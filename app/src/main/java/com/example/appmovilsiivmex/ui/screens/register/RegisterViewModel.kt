package com.example.appmovilsiivmex.ui.screens.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
//import com.example.appmovilsiivmex.domain.usecase.LoginUseCase
//import com.example.appmovilsiivmex.domain.usecase.ValidateEmailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    //private val loginUseCase: LoginUseCase,
    //private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()


    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailError = null
            )
        }
        validateForm()
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                passwordError = null
            )
        }
        validateForm()
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(showPassword = !it.showPassword) }
    }



    private fun validateForm() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        //val isEmailValid = validateEmailUseCase(email)
        val isEmailValid = true
        val isPasswordValid = password.length >= 8


        /*
        _uiState.update {
            it.copy(isLoginEnabled = isEmailValid && isPasswordValid)
        }

         */
    }


    fun onLoginClick() {


        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true) }

            try {



                /*
                val result = loginUseCase(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )

                result.fold(
                    onSuccess = { user ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                loginSuccess = true
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                loginError = error.message ?: "Error desconocido"
                            )
                        }
                    }
                )

                 */


            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }

}