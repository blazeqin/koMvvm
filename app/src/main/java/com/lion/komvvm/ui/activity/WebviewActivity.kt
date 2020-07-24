package com.lion.komvvm.ui.activity

import android.content.Intent
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ShareCompat
import androidx.databinding.ViewDataBinding
import com.lion.komvvm.R
import com.lion.mvvmlib.base.BaseActivity
import com.lion.mvvmlib.base.BaseVM
import com.lion.mvvmlib.util.EXTRA_URL
import kotlinx.android.synthetic.main.activity_webview.*

class WebviewActivity : BaseActivity<BaseVM, ViewDataBinding>() {
    private var mUrl: String? = null

    override fun layoutId(): Int = R.layout.activity_webview

    override fun initView(savedInstanceState: Bundle?) {
        initWebview()
    }

    private fun initWebview() {
        //声明WebSettings子类
        with(webview.settings) {
            //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
//        javaScriptEnabled = true
            //设置自适应屏幕，两者合用
            useWideViewPort = true //将图片调整到适合webview的大小
            loadWithOverviewMode = true // 缩放至屏幕的大小
            //缩放操作
            setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
            builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
            displayZoomControls = false //隐藏原生的缩放控件
            //其他细节操作
            cacheMode = WebSettings.LOAD_DEFAULT
            allowFileAccess = true //设置可以访问文件
            javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
            loadsImagesAutomatically = true //支持自动加载图片
            defaultTextEncodingName = "utf-8"//设置编码格式

            domStorageEnabled = true // 开启 DOM storage API 功能
            databaseEnabled = true   //开启 database storage API 功能
            setAppCacheEnabled(true)//开启 Application Caches 功能
        }

        //url jump problem. prevent to use system browser
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url?.toString())
                return true
            }
        }
    }

    override fun initData() {
        intent.getStringExtra(EXTRA_URL)?.let {
            mUrl = it
            webview.loadUrl(it)
        }
        bindListener()
        wv_toolbar.title = mUrl?.let {
            it.substring(it.lastIndexOf("/") + 1)
        } ?: ""
        openOptionsMenu()
    }

    private fun bindListener() {
        with(wv_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_share -> {
                        createShareIntent()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun createShareIntent() {
        val shareText = mUrl ?: ""
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }
}