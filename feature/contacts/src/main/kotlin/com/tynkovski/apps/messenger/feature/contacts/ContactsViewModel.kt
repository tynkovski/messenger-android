package com.tynkovski.apps.messenger.feature.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.domain.GetContactsUsecase
import com.tynkovski.apps.messenger.core.model.collector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val getContactsUsecase: GetContactsUsecase,
) : ViewModel() {
    fun getContacts() {
        viewModelScope.launch {
            getContactsUsecase().collector(
                onLoading = {

                },
                onSuccess = {

                },
                onError = {

                }
            )
        }
    }
}