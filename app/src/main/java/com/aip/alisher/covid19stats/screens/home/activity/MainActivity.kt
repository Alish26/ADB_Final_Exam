package com.aip.alisher.covid19stats.screens.home.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aip.alisher.covid19stats.screens.detail.viewmodel.DetailViewModel
import com.aip.alisher.covid19stats.screens.home.viewmodel.HomeViewModel
import com.aip.alisher.covid19stats.common.models.CountryCovidInfo
import com.aip.alisher.covid19stats.common.utils.Consts.Companion.CASES
import com.aip.alisher.covid19stats.common.utils.Consts.Companion.COUNTRY
import com.aip.alisher.covid19stats.common.utils.Consts.Companion.DEATHS
import com.aip.alisher.covid19stats.common.utils.Consts.Companion.FLAG
import com.aip.alisher.covid19stats.common.utils.Consts.Companion.RECOVERED
import com.aip.alisher.covid19stats.common.utils.Consts.Companion.TESTS
import com.aip.alisher.covid19stats.common.utils.Consts.Companion.TODAY_CASE
import com.aip.alisher.covid19stats.common.utils.Consts.Companion.TODAY_DEATH
import com.aip.alisher.covid19stats.databinding.ActivityMainBinding
import com.aip.alisher.covid19stats.screens.detail.activity.DetailActivity
import com.aip.alisher.covid19stats.screens.home.adapter.CountryAdapter
import com.aip.alisher.covid19stats.common.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val usersDetailViewModel: DetailViewModel by viewModels()
    private var countryAdapter: CountryAdapter? = null
    private var countryCovidInfoList: List<CountryCovidInfo>? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.rvCountry.layoutManager = LinearLayoutManager(this)
        countryAdapter = CountryAdapter()
        binding.rvCountry.adapter = countryAdapter
        countryCovidInfoList = ArrayList()

        homeViewModel.countryData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { countries ->
                        countryAdapter?.setCountryList(applicationContext, countries)
                    }
                }
                else -> {}
            }
        }

        countryAdapter?.setOnItemClickListener(object : CountryAdapter.OnItemClickListener {
            override fun onItemClick(item: CountryCovidInfo) {
                usersDetailViewModel._countryLiveData.postValue(item.country)

                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(COUNTRY, item.country)
                intent.putExtra(DEATHS, item.deaths)
                intent.putExtra(RECOVERED, item.recovered)
                startActivity(intent)
            }
        })
    }
}