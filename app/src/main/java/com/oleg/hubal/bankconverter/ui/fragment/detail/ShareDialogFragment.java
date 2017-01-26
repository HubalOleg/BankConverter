package com.oleg.hubal.bankconverter.ui.fragment.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.oleg.hubal.bankconverter.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 25.01.2017.
 */

public class ShareDialogFragment extends DialogFragment {

    private static final String BUNDLE_IMAGE_URI = "IMAGE_URI";

    @BindView(R.id.iv_share_image)
    ImageView mShareImageView;
    @BindView(R.id.btn_share)
    Button mShareButton;

    static ShareDialogFragment newInstance(Uri imageUri) {
        ShareDialogFragment fragment = new ShareDialogFragment();

        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_IMAGE_URI, imageUri);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_dialog, container, false);
        ButterKnife.bind(ShareDialogFragment.this, view);

        final Uri imageUri = getArguments().getParcelable(BUNDLE_IMAGE_URI);
        Picasso.with(getContext()).load(imageUri).into(mShareImageView);

        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType(getString(R.string.INTENT_TYPE));
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(intent, getString(R.string.intent_share_image)));
            }
        });

        return view;
    }
}
