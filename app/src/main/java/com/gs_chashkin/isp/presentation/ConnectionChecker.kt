package com.gs_chashkin.isp.presentation

import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object ConnectionChecker {
    // TCP/HTTP/DNS (depending on the port, 53=DNS, 80=HTTP, etc.)
    fun isOnline(): Boolean {
        return try {
            Socket().run {
                connect(InetSocketAddress("8.8.8.8", 53), 500)
                close()
            }
            true
        } catch (e: IOException) {
            false
        }
    }
}


//@dagger.assisted.AssistedFactory
//interface LoginViewModelAssistedFactory {
//    fun create(resources: Resources): LoginViewModel
//}
//
//
//fun provideFactory(
//    assistedFactory: LoginViewModelAssistedFactory,
//    resources: Resources
//): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return assistedFactory.create(resources) as T
//    }
//}