package com.aip.alisher.covid19stats.screens.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aip.alisher.covid19stats.common.models.CountryCovidInfo
import com.aip.alisher.covid19stats.business.repository.Repository
import com.aip.alisher.covid19stats.common.utils.NetworkHelper
import com.aip.alisher.covid19stats.common.utils.Source
import com.aip.alisher.covid19stats.presentation.viewmodel.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by halilozel1903 on 11.12.2021
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _countryData = MutableLiveData<Source<List<CountryCovidInfo>>>()
    val countryData: LiveData<Source<List<CountryCovidInfo>>> get() = _countryData

    init {
        getData()
    }

    override fun getData() {
        viewModelScope.launch {
            _countryData.postValue(Source.loading(null))

            if (networkHelper.isInternetAvailable()) {
                repository.getCountryList().let { response ->
                    if (response.isSuccessful) {
                        _countryData.postValue(Source.success(response.body()))
                    } else {
                        _countryData.postValue(Source.error(response.errorBody().toString(), null))
                    }
                }
            } else {
                _countryData.postValue(Source.error("Internet connection not found", null))
            }
        }
    }
}