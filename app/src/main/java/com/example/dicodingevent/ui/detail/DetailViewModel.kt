package com.example.dicodingevent.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.response.DetailResponse
import com.example.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _events = MutableLiveData<DetailResponse?>()
    val events: LiveData<DetailResponse?> get() = _events

    private val _isLoad = MutableLiveData<Boolean>()
    val isLoad: LiveData<Boolean> get() = _isLoad

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    companion object{
        private const val TAG = "DetailViewModel"
    }

    fun fetchDetail(eventID: Int){
        _isLoad.value = true
        val client = ApiConfig.getApiService().getDetailEvent(eventID)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>){
                _isLoad.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        Log.d(TAG, "Event Data: ${responseBody.event}")
                        _events.value = responseBody
                    } else{
                        Log.e(TAG, "Response body is null")
                        _errorMessage.value = "Response body is null"
                    }
                } else{
                    Log.e(TAG, "API Error: ${response.message()}")
                    _errorMessage.value = "API Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable){
            _isLoad.value = false
            Log.e(TAG, "API Call Failed: ${t.message}")
            _errorMessage.value = "API Call Failed: ${t.message}"
        }
        })
    }
}
