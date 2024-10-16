package com.dicoding.myfinalsub.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.myfinalsub.data.response.EventDetail
import com.dicoding.myfinalsub.data.response.FinishedResponse
import com.dicoding.myfinalsub.data.response.ListEventsItem
import com.dicoding.myfinalsub.data.retofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _eventDetail = MutableLiveData<ListEventsItem?>()
    val eventDetail: LiveData<ListEventsItem?> get() = _eventDetail

    fun getEventDetail(eventId: Int) {
        ApiConfig.getApiService().getDetailed(eventId).enqueue(object : Callback<EventDetail> {
            override fun onResponse(call: Call<EventDetail>, response: Response<EventDetail>) {
                if (response.isSuccessful) {
                    _eventDetail.value = response.body()?.event
                    Log.d("DetailViewModel", "Event Detail fetched successfully: ${response.body()}")
                } else {
                    Log.e("DetailViewModel", "Error fetching event detail: ${response.errorBody()?.string()}")
                    _eventDetail.value = null
                }
            }

            override fun onFailure(call: Call<EventDetail>, t: Throwable) {
                Log.e("DetailViewModel", "Network failure: ${t.message}")
                _eventDetail.postValue(null)
            }
        })
    }
}
