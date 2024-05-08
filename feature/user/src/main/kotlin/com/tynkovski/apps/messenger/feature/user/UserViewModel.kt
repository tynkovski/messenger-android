package com.tynkovski.apps.messenger.feature.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tynkovski.apps.messenger.feature.user.navigation.UserArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val userArgs: UserArgs = UserArgs(savedStateHandle)
    val userId = userArgs.userId
}