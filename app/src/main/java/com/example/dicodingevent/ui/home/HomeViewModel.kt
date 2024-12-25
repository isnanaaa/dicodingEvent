package com.example.dicodingevent.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.data.response.UpcomingResponse
import com.example.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _upcoming = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> get() = _upcoming

    private val _finished = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> get() = _finished

    private val _isLoad = MutableLiveData<Boolean>()
    val isLoad: LiveData<Boolean> get() = _isLoad

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchUpcomingEvents(){
        _isLoad.value = true
        val client = ApiConfig.getApiService().getListEventsItem()
        client.enqueue(object : Callback<UpcomingResponse>{

            override fun onResponse(
                call: Call<UpcomingResponse>,
                response: Response<UpcomingResponse>
            ) {
                _isLoad.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _upcoming.value = responseBody.listEvents
                    }
                } else{
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<UpcomingResponse>, t: Throwable) {
                _isLoad.value = false
                _errorMessage.value = t.message
            }
        })
    }

    fun fetchFinishedEvents(){
        _isLoad.value = true
        val client = ApiConfig.getApiService().getFinishEvents()
        client.enqueue(object : Callback<UpcomingResponse>{

            override fun onResponse(
                call: Call<UpcomingResponse>,
                response: Response<UpcomingResponse>
            ) {
                _isLoad.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _finished.value = responseBody.listEvents
                    }
                } else{
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<UpcomingResponse>, t: Throwable) {
                _isLoad.value = false
                _errorMessage.value = t.message
            }
        })
    }

}