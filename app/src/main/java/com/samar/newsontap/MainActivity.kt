package com.samar.newsontap

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.samar.newsontap.appUI.HomeScreen
import com.samar.newsontap.ui.theme.NewsOnTapTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity(), ServiceConnection {
    private val serviceFlow: MutableStateFlow<ServiceNotify?> = MutableStateFlow(null)
    // Getter to access the service flow
    fun getServiceFlow(): StateFlow<ServiceNotify?> = serviceFlow
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NewsOnTapTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
//                    GlobalScope.launch {
//                        val responseData = fetchNewsData()
//                      Log.d("Response", responseData.toString())
//                    }
                    HomeScreen()
                }
                }
            }
        }
    override fun onStart() {
        super.onStart()
        Intent(this, ServiceNotify::class.java).also { intent ->
            bindService(intent, this, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as ServiceNotify.ServiceBinder
//        service.asMutableStateFlow()?.tryEmit(binder.service)service
        val myService = (binder as? ServiceNotify.ServiceBinder)?.service
        // Emit the service instance to the flow
        myService?.let { service ->
            serviceFlow.tryEmit(service)
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        serviceFlow.tryEmit(null)
    }
}