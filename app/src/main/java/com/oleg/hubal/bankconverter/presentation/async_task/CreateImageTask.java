package com.oleg.hubal.bankconverter.presentation.async_task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;

import com.oleg.hubal.bankconverter.model.data.Currency;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.presentation.events.CreateImageEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by User on 25.01.2017.
 */

public class CreateImageTask extends AsyncTask<Void, Void, Uri> {

    public static final int TITLE_HEIGHT = 200;
    public static final int CURRENCY_HEIGHT = 100;
    public static final int WIDTH = 800;
    public static final int TITLE_LEFT_MARGIN = 10;
    public static final int CURRENCY_LEFT_MARGIN = 20;
    public static final int BIG_TEXT_SIZE = 60;
    public static final int SMALL_TEXT_SIZE = 40;
    public static final int VERTICAL_MARGIN = 10;

    private final Organization mOrganization;
    private final String mCacheDir;
    private Canvas mCanvas;

    public CreateImageTask(Organization organization, String cacheDir) {
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
        EventBus.getDefault().post(new CreateImageEvent(imageUri));
    }
}
