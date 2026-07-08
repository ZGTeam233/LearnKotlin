package com.github.zgteam233.webappdemo

import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.github.zgteam233.webappdemo.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.IOException
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.widget.Toast
import androidx.activity.OnBackPressedCallback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // 重试与状态管理
    private var retryCount = 0
    private val maxRetries = 2 // 主 URL 最大重试次数
    private var isUsingFallback = false // 标记当前是否已切换到备用 URL

    private var currentUrl: String = ""
    private var fallbackUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. 初始化 ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. 设置 Toolbar
        setSupportActionBar(binding.toolbar)

        // 3. 加载配置
        val config = loadConfig()
        currentUrl = config.optString("url", "https://www.baidu.com")
        fallbackUrl = config.optString("fallbackUrl", "https://www.github.com")

        val enableJs = config.optBoolean("enableJavaScript", true)
        val enableZoom = config.optBoolean("enableZoom", false)
        val debugForceFallback = config.optBoolean("debugForceFallback", false)

        // 4. 配置 WebView
        setupWebView(enableJs, enableZoom)

        // 5. 加载页面
        val initialUrl = if (debugForceFallback) fallbackUrl else currentUrl
        loadUrl(initialUrl)

        // 6. 设置刷新按钮
        binding.refreshFab.setOnClickListener {
            reloadPage()
        }

        // 7. 拦截返回键
        setupBackPressedDispatcher()
    }

    private fun loadConfig(): JSONObject {
        return try {
            val jsonString = assets.open("config.json").bufferedReader().use { it.readText() }
            JSONObject(jsonString)
        } catch (e: IOException) {
            Log.e("Config", "Failed to load config", e)
            JSONObject()
        } catch (e: Exception) {
            Log.e("Config", "JSON parse error", e)
            JSONObject()
        }
    }

    private fun setupWebView(enableJs: Boolean, enableZoom: Boolean) {
        val webView = binding.webView
        val settings = webView.settings

        // 基础设置
        settings.javaScriptEnabled = enableJs
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.setSupportZoom(enableZoom)
        settings.builtInZoomControls = enableZoom
        settings.displayZoomControls = false
        settings.cacheMode = android.webkit.WebSettings.LOAD_DEFAULT

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url?.toString() ?: return false
                view?.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d("WebView", "Page finished: $url")
                retryCount = 0
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                if (request?.isForMainFrame == true) {
                    Log.e("WebView", "Network error: ${error?.description}")
                    handleLoadError(view)
                }
            }

            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: android.webkit.WebResourceResponse?) {
                if (request?.isForMainFrame == true) {
                    Log.e("WebView", "HTTP error: ${errorResponse?.statusCode}")
                    handleLoadError(view)
                }
            }
        }

        webView.webChromeClient = WebChromeClient()
    }
    private fun loadUrl(url: String) {
        if (url.isEmpty()) return
        binding.webView.loadUrl(url)
    }

    private fun handleLoadError(webView: WebView?) {
        if (isUsingFallback) {
            "备用页面也无法加载，请检查网络".showToast(Toast.LENGTH_LONG)
            return
        }

        if (retryCount < maxRetries) {
            retryCount++
            Log.w("WebView", "Retrying main URL ($retryCount/$maxRetries)")
            webView?.loadUrl(currentUrl)
        } else {
            isUsingFallback = true
            retryCount = 0
            Log.w("WebView", "Switching to fallback: $fallbackUrl")
            "主服务器连接失败，尝试备用线路...".showToast()
            webView?.loadUrl(fallbackUrl)
        }
    }

    private fun reloadPage() {
        retryCount = 0
        isUsingFallback = false
        val urlToReload = if (binding.webView.url?.contains(fallbackUrl) == true) fallbackUrl else currentUrl
        binding.webView.loadUrl(urlToReload)
        "正在刷新...".showToast()
    }
    private fun setupBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

}