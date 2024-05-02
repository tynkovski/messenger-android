package com.tynkovski.apps.messenger.core.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val messengerDispatcher: MessengerDispatchers)

enum class MessengerDispatchers {
    Default,
    IO
}
