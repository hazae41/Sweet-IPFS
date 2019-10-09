package fr.rhaz.ipfs.sweet

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import im.delight.android.webview.AdvancedWebView
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity(), AdvancedWebView.Listener {

    override fun onBackPressed() = webview.goBack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        startService<DaemonService>()

        webview!!.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            settings.setSupportZoom(true)
            settings.defaultTextEncodingName = "utf-8"

            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()

            addJavascriptInterface(object {
                @JavascriptInterface
                fun logs() = json(DaemonService.logs).toString()

                @JavascriptInterface
                fun execute(msg: String) {
                    DaemonService.logs.add("> $msg")
                    exec(msg).read { DaemonService.logs.add(it) }
                }
            }, "android")

            loadUrl("https://sweetipfswebui.netlify.com/")
        }
    }


    override fun onPageStarted(url: String?, favicon: Bitmap?) {}

    override fun onPageFinished(url: String?) {}

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {}

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?
    ) {
    }

    override fun onExternalPageRequest(url: String?) {}
}
