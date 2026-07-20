package com.example.taskkilltest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskkilltest.databinding.ActivityMainBinding
import rikka.shizuku.Shizuku
import android.content.pm.PackageManager
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE_PERMISSION = 100
    }

    private lateinit var binding: ActivityMainBinding

    // 监听 Shizuku 服务启动
    private val binderReceivedListener = Shizuku.OnBinderReceivedListener {
        checkPermissionAndSetup()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "内存清理助手"

        initUI()
        checkShizukuStatus()
    }

    private fun initUI() {
        binding.btnClean.setOnClickListener {
            executeShellCommand()
        }
    }

    private fun checkShizukuStatus() {
        if (Shizuku.pingBinder()) {
            binding.tvStatus.text = "状态：Shizuku 服务已连接"
            Shizuku.addBinderReceivedListenerSticky(binderReceivedListener)
        } else {
            binding.tvStatus.text = "状态：Shizuku 未运行，请先在 Shizuku App 中激活"
            binding.btnClean.isEnabled = false
        }
    }

    private fun checkPermissionAndSetup() {
        if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
            binding.btnClean.isEnabled = true
            binding.tvStatus.text = "状态：已授权，可以开始获取进程"
        } else if (Shizuku.shouldShowRequestPermissionRationale()) { // 修复点 1：移除参数 this
            binding.tvStatus.text = "状态：需要 Shizuku 权限，请手动允许"
            binding.btnClean.isEnabled = true // 允许用户再次点击触发请求
        } else {
            Shizuku.requestPermission(REQUEST_CODE_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                checkPermissionAndSetup()
            } else {
                binding.tvStatus.text = "状态：权限被拒绝"
            }
        }
    }

    private fun executeShellCommand() {
        binding.tvResult.text = "正在执行 ps -A ...\n(请稍候)"
        binding.btnClean.isEnabled = false

        thread {
            try {
                // 修复点 2：使用反射调用 newProcess，完美绕过 Kotlin 编译器的可见性误报
                val cmd = arrayOf("sh", "-c", "ps -A")
                val method = Shizuku::class.java.getMethod(
                    "newProcess",
                    Array<String>::class.java,
                    Array<String>::class.java,
                    String::class.java
                )

                // 执行命令，返回的 ShizukuRemoteProcess 继承自 java.lang.Process
                val process = method.invoke(null, cmd, null, null) as Process

                val reader = process.inputStream.bufferedReader()
                val result = StringBuilder()
                var line: String?
                var count = 0

                // 读取输出，限制前 30 行防止 UI 渲染卡顿
                while (reader.readLine().also { line = it } != null && count < 30) {
                    result.append(line).append("\n")
                    count++
                }

                process.waitFor()
                reader.close()

                runOnUiThread {
                    if (result.isEmpty()) {
                        binding.tvResult.text = "执行成功，但无输出 (可能命令不兼容当前系统)"
                    } else {
                        binding.tvResult.text = "✅ 成功获取进程列表 (前30行):\n\n$result"
                    }
                    binding.btnClean.isEnabled = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    binding.tvResult.text = "❌ 执行出错:\n${e.message}\n\n请检查:\n1. Shizuku 是否已激活\n2. 是否已授予本应用权限"
                    binding.btnClean.isEnabled = true
                }
            }
        }
    }
}