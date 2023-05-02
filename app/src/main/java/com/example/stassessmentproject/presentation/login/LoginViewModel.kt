package com.example.stassessmentproject.presentation.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stassessmentproject.domain.models.Validation
import com.example.stassessmentproject.utils.APP_LAUNCHED
import com.example.stassessmentproject.utils.USERNAME
import com.example.stassessmentproject.utils.ValidationEvents
import com.example.stassessmentproject.utils.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class LoginViewModel @Inject constructor(
    private val getValidation: Validation,
    @ApplicationContext private val context: Context,
): ViewModel() {

    private var _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvents>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvents) {
        when(event) {
            is LoginEvents.EnteredUsername -> {
                _state.value = _state.value.copy(username = event.username)
            }
            is LoginEvents.ClickedLoginButton -> {
                val usernameIsValid = isUserEntryValid()
                if (usernameIsValid) {
                    viewModelScope.launch {
                        validationEventChannel.send(ValidationEvents.Success)
                        updateLaunchState(context)
                    }
                }
            }
        }
    }

    /**
     * This function allows you to authenticate a user by vetting their entries.
     * It returns true if the entries are valid, and false if they are invalid.
     */
    private fun isUserEntryValid(): Boolean {

        val usernameResult = getValidation.validateUsername(_state.value.username)

        /**
         * This value checks if there is any error in the username field.
         */
        val errorInEntry = !usernameResult.successful

        _state.value = _state.value.copy(
            usernameError = usernameResult.errorMessage
        )

        return !errorInEntry
    }

    private fun updateLaunchState(context: Context) {
        /** Updating the flag that indicates that the app has been launched before */
        viewModelScope.launch {
            context.dataStore.edit {
                it[APP_LAUNCHED] = true
            }
            /**
             * Saving the username of the user to be used in the products screen
             */
            context.dataStore.edit {
                it[USERNAME] = _state.value.username
            }
        }
    }
}