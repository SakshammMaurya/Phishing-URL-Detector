package com.example.phishingwebsitepredictor.ViewModel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phishingwebsitepredictor.Api.RetrofitInstance
import com.example.phishingwebsitepredictor.model.UrlRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.security.KeyStore.TrustedCertificateEntry

class PredictionViewModel : ViewModel() {

    private val api = RetrofitInstance.api

    private val _prediction = MutableStateFlow("")
    val prediction: StateFlow<String> = _prediction.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getPrediction(url: String) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val response = api.predictUrl(UrlRequest(url))
                if (response.isSuccessful) {
                    _prediction.value = (response.body()?.prediction ?: "Unknown").toString()
                } else {
                    _error.value = "Server error: ${response.code()}"
                }
            } catch (e: IOException) {
                _error.value = "Network error: ${e.message}"
            } catch (e: HttpException) {
                _error.value = "HTTP error: ${e.message}"
            } catch (e: Exception) {
                _error.value = "Unexpected error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}