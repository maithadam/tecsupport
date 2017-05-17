package com.nanobite.nanobite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.app.ProgressDialog;
import android.webkit.WebSettings;
import android.app.AlertDialog;
import android.webkit.WebViewClient;
import android.util.Log;
import android.widget.Toast;
import android.content.DialogInterface;

/**
 * Created by mac on 5/3/17.
 */

public class NanoFragment extends Fragment {

    View myview;
    private static final String TAG = "Main";
    private ProgressDialog progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myview=inflater.inflate(R.layout.fragment_nanosite,container,false);
        WebView webview=(WebView)myview.findViewById(R.id.nano_webview);

            WebSettings settings = webview.getSettings();
            settings.setJavaScriptEnabled(true);
            webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

            final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

            progressBar = ProgressDialog.show(getActivity(), "nanobitetec.com", "Loading...");

            webview.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.i(TAG, "Processing webview url click...");
                    view.loadUrl(url);
                    return true;
                }

                public void onPageFinished(WebView view, String url) {
                    Log.i(TAG, "Finished loading URL: " + url);
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
                }

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Log.e(TAG, "Error: " + description);
                    Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage(description);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    alertDialog.show();
                }
            });

            webview.loadUrl("http://www.nanobitetec.com");


        return myview;
    }
}
