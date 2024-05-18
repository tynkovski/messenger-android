package com.tynkovski.apps.messenger.core.data.util

import com.tynkovski.apps.messenger.core.model.data.AccessToken
import com.tynkovski.apps.messenger.core.model.data.Token
import com.tynkovski.apps.messenger.core.network.model.response.AccessResponse
import com.tynkovski.apps.messenger.core.network.model.response.TokenResponse

object TokenMapper {

    val tokenMapper: (TokenResponse) -> Token = {
        Token(it.accessToken, it.refreshToken)
    }

    val accessMapper: (AccessResponse) -> AccessToken = {
        AccessToken(it.accessToken)
    }

}