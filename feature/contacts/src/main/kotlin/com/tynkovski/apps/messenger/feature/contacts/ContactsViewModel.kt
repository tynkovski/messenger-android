package com.tynkovski.apps.messenger.feature.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.data.repository.ContactsRepository
import com.tynkovski.apps.messenger.core.model.collector
import com.tynkovski.apps.messenger.core.model.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {


    private val mContacts: MutableStateFlow<ContactsUiState> = MutableStateFlow(
        value = ContactsUiState.Loading
    )

    val contacts = mContacts.asStateFlow()

    fun getContacts() {
        viewModelScope.launch {
            contactsRepository.getContacts().collector(
                onLoading = ::onLoading,
                onSuccess = ::onSuccess,
                onError = ::onError
            )
        }
    }

    fun addContacts(userId: Long) {
        viewModelScope.launch {
            contactsRepository.addContact(userId).collector(
                onLoading = ::onLoading,
                onSuccess = ::onSuccess,
                onError = ::onError
            )
        }
    }

    fun removeContact(userId: Long) {
        viewModelScope.launch {
            contactsRepository.removeContact(userId).collector(
                onLoading = ::onLoading,
                onSuccess = ::onSuccess,
                onError = ::onError
            )
        }
    }

    private suspend fun onSuccess(users: List<User>) {
        if (users.isEmpty()) {
            mContacts.emit(ContactsUiState.Empty)
        } else {
            mContacts.emit(ContactsUiState.Success(users.map(ContactUi::fromUser)))
        }
    }

    private suspend fun onError(exception: Throwable) {
        mContacts.emit(ContactsUiState.Error(exception))
    }

    private suspend fun onLoading() {
        mContacts.emit(ContactsUiState.Loading)
    }

    init {
        getContacts()
    }
}