package com.tynkovski.apps.messenger.navigation

import com.tynkovski.apps.messenger.feature.auth.R as authR

enum class AuthDestination(val titleTextId: Int) {
    SignIn(titleTextId = authR.string.feature_auth_title_sign_in),
    SignUp(titleTextId = authR.string.feature_auth_title_sign_up)
}