package com.example.instantweather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instantweather.api.Constant
import com.example.instantweather.api.NetworkResponse
import com.example.instantweather.api.RetrofitInstance
import com.example.instantweather.api.WeatherApi
import com.example.instantweather.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city : String) {

        _weatherResult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try{
            val response = weatherApi.getWeather(Constant.apikey, city)
            if (response.isSuccessful){
                response.body()?.let {
                    _weatherResult.value = NetworkResponse.Success(it)
                }
        }else {
                _weatherResult.value = NetworkResponse.Error("Failed to Load Data")
        }
    }
        catch( e : Exception){
            _weatherResult.value = NetworkResponse.Error("Failed to Load Data")

        }        }

    }
}