package com.dicoding.myfinalsub.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.myfinalsub.data.response.FinishedResponse
import com.dicoding.myfinalsub.data.response.ListEventsItem
import com.dicoding.myfinalsub.data.retofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getEvents() {
        if (_events.value.isNullOrEmpty()) {
            _isLoading.value = true
            // Panggilan API untuk mengambil event yang akan datang
            ApiConfig.getApiService().getFinished(active = 1) // Mengambil event yang akan datang
                .enqueue(object : Callback<FinishedResponse> {
                    override fun onResponse(
                        call: Call<FinishedResponse>,
                        response: Response<FinishedResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            // Ambil listEvents dari FinishedResponse
                            _events.value = response.body()?.listEvents?.filterNotNull() ?: emptyList()
                        } else {
                            _events.value = emptyList()
                            _errorMessage.value =
                                "Error: ${response.code()} - ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<FinishedResponse>, t: Throwable) {
                        _isLoading.value = false
                        _events.value = emptyList()
                        _errorMessage.value = "Failed to fetch data: ${t.message}"
                        t.printStackTrace()
                    }
                })
        }
    }
}
