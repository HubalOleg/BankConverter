package com.oleg.hubal.bankconverter.presentation.presenter.detail;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.webkit.URLUtil;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.oleg.hubal.bankconverter.global.constants.ErrorConstants;
import com.oleg.hubal.bankconverter.model.data.Currency;
import com.oleg.hubal.bankconverter.model.data.CurrencyAbbr;
import com.oleg.hubal.bankconverter.model.data.CurrencyAbbr_Table;
import com.oleg.hubal.bankconverter.model.data.CurrencyUI;
import com.oleg.hubal.bankconverter.model.data.Currency_Table;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.model.data.Organization_Table;
import com.oleg.hubal.bankconverter.presentation.view.detail.DetailView;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.oleg.hubal.bankconverter.model.data.Currency_Table.organizationId;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private static final String COMA = ", ";
    private static final String CITY = "город ";

    private Organization mOrganization;
    private Uri mUri;

    public void onLoadOrganization(String organizationId) {
        mOrganization = SQLite.select()
                .from(Organization.class)
                .where(Organization_Table.id.is(organizationId))
                .querySingle();

        getViewState().showOrganizationData(mOrganization);
    }

    public void onLoadCurrency() {
        parseCurrencyData();
    }

    private void parseCurrencyData() {
        List<Currency> currentCurrencyList = mOrganization.getCurrency();

        List<CurrencyUI> currencyUIList = getCurrencyUIList(currentCurrencyList);

        getViewState().showCurrencyData(currencyUIList);
    }

    private List<CurrencyUI> getCurrencyUIList(List<Currency> currencyList) {
        List<CurrencyUI> currencyUIList = new ArrayList<>();

        for (Currency currency : currencyList) {
            CurrencyUI currencyUI = new CurrencyUI();
            String currencyName = loadCurrencyName(currency.getNameAbbreviation());
            float ask = Float.parseFloat(currency.getAsk());
            float bid = Float.parseFloat(currency.getBid());

            currencyUI.setCurrencyName(currencyName);
            currencyUI.setCurrentAsk(currency.getAsk());
            currencyUI.setCurrentBid(currency.getBid());
            currencyUI.setAskIncreased(false);
            currencyUI.setBidIncreased(false);

            Currency previousCurrency = loadPreviousCurrency(currency);

            if (previousCurrency != null) {
                float previousAsk = Float.parseFloat(previousCurrency.getAsk());
                float previousBid = Float.parseFloat(previousCurrency.getBid());

                currencyUI.setAskIncreased(ask > previousAsk);
                currencyUI.setBidIncreased(bid > previousBid);
            }

            currencyUIList.add(currencyUI);
        }

        return currencyUIList;
    }

    private String loadCurrencyName(String abbreviation) {
        CurrencyAbbr currencyAbbr = SQLite.select()
                .from(CurrencyAbbr.class)
                .where(CurrencyAbbr_Table.abbreviation.is(abbreviation))
                .querySingle();

        return (currencyAbbr == null) ? "" : currencyAbbr.getName();
    }

    private Currency loadPreviousCurrency(Currency currency) {
        return SQLite.select()
                .from(Currency.class)
                .where(organizationId.is(currency.getOrganizationId()))
                .and(Currency_Table.nameAbbreviation.is(currency.getNameAbbreviation()))
                .and(Currency_Table.isCurrent.is(false))
                .querySingle();
    }

    public void onFloatingMapClick() {
        String location = mOrganization.getAddress()+ COMA + CITY + mOrganization.getCityName() + COMA + mOrganization.getRegionName();
        getViewState().showMap(location);
    }

    public void onFloatingSiteClick() {
        String url = mOrganization.getLink();
        if (URLUtil.isValidUrl(url)) {
            getViewState().showSite(url);
        }
    }

    public void onFloatingPhoneClick() {
        if (mOrganization.getPhone() == null) {
            getViewState().showError(ErrorConstants.PHONE_NOT_EXIST);
        } else {
            getViewState().makeCall(mOrganization.getPhone());
        }
    }

    public void onShareClick(String cacheDir) {
        new CreateImageTask(mOrganization, cacheDir).execute();
    }

    class CreateImageTask extends AsyncTask<Void, Void, Uri> {

        public static final int TITLE_HEIGHT = 200;
        public static final int CURRENCY_HEIGHT = 100;
        public static final int WIDTH = 800;
        public static final int TITLE_LEFT_MARGIN = 30;
        public static final int CURRENCY_LEFT_MARGIN = 50;
        public static final int BIG_TEXT_SIZE = 60;
        public static final int SMALL_TEXT_SIZE = 40;
        public static final int VERTICAL_MARGIN = 10;

        private final Organization mOrganization;
        private final String mCacheDir;
        private Canvas mCanvas;

        CreateImageTask(Organization organization, String cacheDir) {
            mOrganization = organization;
            mCacheDir = cacheDir;
        }

        @Override
        protected Uri doInBackground(Void... voids) {
            Bitmap bitmap = createBitmap();
            File file = compressBitmapToFile(bitmap);
            return Uri.fromFile(file);
        }

        private Bitmap createBitmap() {
            int currencySize = mOrganization.getCurrency().size();
            int width = WIDTH;
            int height = TITLE_HEIGHT + CURRENCY_HEIGHT * currencySize;
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            bitmap.eraseColor(Color.WHITE);
            mCanvas = new Canvas(bitmap);

            drawTitle();
            drawCurrencyList();

            if (bitmap.getHeight() > 4000) {
                bitmap = Bitmap.createScaledBitmap(bitmap, WIDTH, 4000, false);
            }

            return bitmap;
        }

        private void drawTitle() {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(BIG_TEXT_SIZE);
            mCanvas.drawText(mOrganization.getTitle(), TITLE_LEFT_MARGIN, BIG_TEXT_SIZE + VERTICAL_MARGIN, paint);
            paint.setTextSize(SMALL_TEXT_SIZE);
            mCanvas.drawText(mOrganization.getRegionName(), TITLE_LEFT_MARGIN,
                    BIG_TEXT_SIZE + SMALL_TEXT_SIZE + VERTICAL_MARGIN * 2,
                    paint);
            mCanvas.drawText(mOrganization.getCityName(), TITLE_LEFT_MARGIN,
                    BIG_TEXT_SIZE + SMALL_TEXT_SIZE * 2 + VERTICAL_MARGIN * 3,
                    paint);
        }

        private void drawCurrencyList() {
            Paint paint = new Paint();
            paint.setTextSize(BIG_TEXT_SIZE);

            int currencyVerticalMargin = CURRENCY_HEIGHT - BIG_TEXT_SIZE;
            int topMargin = TITLE_HEIGHT + BIG_TEXT_SIZE;

            List<Currency> currencyList = mOrganization.getCurrency();

            for (int i = 0; i < currencyList.size(); i++) {
                Currency currency = currencyList.get(i);

                String abbreviation = currency.getNameAbbreviation();
                String currencyAsk = currency.getAsk().substring(0, 5);
                String currencyBid = currency.getBid().substring(0, 5);
                String currencyText = currencyAsk + "/" + currencyBid;

                paint.setColor(Color.RED);
                mCanvas.drawText(abbreviation, CURRENCY_LEFT_MARGIN, topMargin, paint);
                paint.setColor(Color.BLACK);
                mCanvas.drawText(currencyText, WIDTH / 2, topMargin, paint);

                topMargin += BIG_TEXT_SIZE + currencyVerticalMargin;
            }
        }

        private File compressBitmapToFile(Bitmap bitmap) {
            File file = new File(mCacheDir, mOrganization.getId() + ".png");
            try {
                FileOutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
                file.setReadable(true, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }

        @Override
        protected void onPostExecute(Uri imageUri) {
            super.onPostExecute(imageUri);
            mUri = imageUri;
            getViewState().showShareDialog(imageUri);
        }
    }

}
