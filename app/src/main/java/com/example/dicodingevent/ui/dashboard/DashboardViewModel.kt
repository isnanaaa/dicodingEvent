package com.example.dicodingevent.ui.dashboard

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.data.response.UpcomingResponse
import com.example.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class DashboardViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> get() = _events

    private val _isLoad = MutableLiveData<Boolean>()
    val isLoad: LiveData<Boolean> get() = _isLoad

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchEvents(){
        _isLoad.value = true
        val client = ApiConfig.getApiService().getEvents()
        client.enqueue(object : Callback<UpcomingResponse>{
            override fun onResponse(call: Call<UpcomingResponse>, response: Response<UpcomingResponse>){
                _isLoad.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        Log.d(TAG, "Event Data: ${responseBody.listEvents}")
                        _events.value = responseBody.listEvents
                    } else{
                        Log.d(TAG, "Response body is null")
                    }
                } else{
                    Log.e(TAG, "API Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UpcomingResponse>, t: Throwable){
                _isLoad.value = false
                Log.e(TAG, "API Call Failed: ${t.message}")
            }
        })
    }
}
