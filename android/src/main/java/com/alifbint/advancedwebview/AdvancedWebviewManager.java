package com.alifbint.advancedwebview;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.URLUtil;

import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.webview.ReactWebViewManager;

public class AdvancedWebviewManager extends ReactWebViewManager {

    private ValueCallback mUploadMessage;
    private final static int FCR = 1;
    public WebView webview = null;
    private AdvancedWebviewPackage aPackage;

    public String getName() {
        return "AdvancedWebView";
    }

    @ReactProp(name = "enabledUploadAndroid")
    public void enabledUploadAndroid(WebView view, boolean enabled) {
        if (enabled) {
            webview = view;
            final AdvancedWebviewModule module = this.aPackage.getModule();
            view.setWebChromeClient(new WebChromeClient() {

                public void onGeolocationPermissionsShowPrompt(String origin,
                        GeolocationPermissions.Callback callback) {
                    callback.invoke(origin, true, false);
                }

                public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                    return true;
                }

                public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                        JsPromptResult result) {
                    return true;
                }

                //For Android 3.0+
                public void openFileChooser(ValueCallback uploadMsg) {
                    module.setUploadMessage(uploadMsg);
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    module.getActivity().startActivityForResult(Intent.createChooser(i, "File Chooser"), FCR);
                }

                // For Android 3.0+, above method not supported in some android 3+ versions, in such case we use this
                public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                    module.setUploadMessage(uploadMsg);
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    module.getActivity().startActivityForResult(Intent.createChooser(i, "File Browser"), FCR);
                }

                //For Android 4.1+
                public void openFileChooser(ValueCallback uploadMsg, String acceptType, String capture) {
                    module.setUploadMessage(uploadMsg);
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    module.getActivity().startActivityForResult(Intent.createChooser(i, "File Chooser"), FCR);
                }

                //For Android 5.0+
                public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                        WebChromeClient.FileChooserParams fileChooserParams) {
                    module.setmUploadCallbackAboveL(filePathCallback);
                    /*if(mUMA != null){
                        mUMA.onReceiveValue(null);
                    }*/
                    if (module.grantPermissions()) {
                        module.uploadImage(filePathCallback);
                    }
                    return true;
                }
            });

        }
    }

    @ReactProp(name = "enabledDownloadAndroid")
    public void enabledDownloadAndroid(WebView view, boolean enabled) {
        if (enabled) {
            webview = view;
            final AdvancedWebviewModule module = this.aPackage.getModule();
            view.setDownloadListener(new DownloadListener() {

                @Override
                public void onDownloadStart(final String url, String userAgent, String contentDisposition,
                        String mimetype, long contentLength) {
                    //module.downUrl = url;
                    if (module.grantStoragePermission()) {
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                        DownloadManager dManager = (DownloadManager) webview.getContext()
                                .getSystemService(Context.DOWNLOAD_SERVICE);
                        dManager.enqueue(request);
                    }
                }
            });
        }
    }

    public void setPackage(AdvancedWebviewPackage aPackage) {
        this.aPackage = aPackage;
    }

    public AdvancedWebviewPackage getPackage() {
        return this.aPackage;
    }
}