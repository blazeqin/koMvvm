package com.lion.komvvm.ui.activity

import android.os.Bundle
import android.webkit.WebSettings
import androidx.databinding.ViewDataBinding
import com.lion.komvvm.R
import com.lion.mvvmlib.base.BaseActivity
import com.lion.mvvmlib.base.BaseVM
import com.lion.mvvmlib.util.EXTRA_URL
import kotlinx.android.synthetic.main.activity_webview.*

class WebviewActivity: BaseActivity<BaseVM, ViewDataBinding>() {
    override fun layoutId(): Int = R.layout.activity_webview

    override fun initView(savedInstanceState: Bundle?) {
        initWebview()
    }

    private fun initWebview() {
        //声明WebSettings子类
        val webSettings = webview.settings
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
//        webSettings.javaScriptEnabled = true
        //设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.displayZoomControls = false //隐藏原生的缩放控件
        //其他细节操作
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.allowFileAccess = true //设置可以访问文件
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings.loadsImagesAutomatically = true //支持自动加载图片
        webSettings.defaultTextEncodingName = "utf-8"//设置编码格式

        webSettings.domStorageEnabled = true // 开启 DOM storage API 功能
        webSettings.databaseEnabled = true   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true)//开启 Application Caches 功能
    }

    override fun initData() {
        intent.getStringExtra(EXTRA_URL)?.let {
            webview.loadUrl(it)
        }
    }
}