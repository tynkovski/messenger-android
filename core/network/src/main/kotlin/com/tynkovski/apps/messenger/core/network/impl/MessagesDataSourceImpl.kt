package com.tynkovski.apps.messenger.core.network.impl

import com.tynkovski.apps.messenger.core.network.MessagesDataSource
import kotlinx.serialization.json.Json
import okhttp3.Call
import javax.inject.Inject

private const val SEND_MESSAGE = "send_message"
private const val EDIT_MESSAGE = "edit_message"
private const val DELETE_MESSAGE = "delete_message"
private const val READ_MESSAGE = "read_message"
private const val UNKNOWN = "unknown"

class MessagesDataSourceImpl @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : MessagesDataSource {

}