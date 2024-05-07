package com.tynkovski.apps.messenger.core.network.impl

import com.tynkovski.apps.messenger.core.network.MessagesDataSource
import kotlinx.serialization.json.Json
import okhttp3.Call
import javax.inject.Inject

class MessagesDataSourceImpl @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : MessagesDataSource {

}