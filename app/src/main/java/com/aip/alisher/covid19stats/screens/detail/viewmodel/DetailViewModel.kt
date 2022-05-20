package com.aip.alisher.covid19stats.screens.detail.viewmodel

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
class DetailViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val countryData = MutableLiveData<Source<CountryCovidInfo>>()

    val _countryLiveData = MutableLiveData<String>()
    val countryLiveData: LiveData<String> get() = _countryLiveData

    val id = countryLiveData.value


    override fun getData() {
        viewModelScope.launch {

            countryData.postValue(
                Source.loading(
                    null
                )
            )

            if (networkHelper.isInternetAvailable()) {

                if (id != null) {

                    repository.getCountryInfo(id).let {

                        if (it.isSuccessful) {
                            countryData.postValue(
                                Source.success(
                                    it.body()
                                )
                            )

                        } else {

                            countryData.postValue(
                                Source.error(
                                    it.errorBody().toString(),
                                    null
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}