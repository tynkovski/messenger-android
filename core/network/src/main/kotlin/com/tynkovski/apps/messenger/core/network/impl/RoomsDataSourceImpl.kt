package com.tynkovski.apps.messenger.core.network.impl

import com.tynkovski.apps.messenger.core.network.RoomsDataSource
import kotlinx.serialization.json.Json
import okhttp3.Call
import javax.inject.Inject

class RoomsDataSourceImpl @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : RoomsDataSource {

}