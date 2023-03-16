package com.jpmc.nycschools.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.jpmc.nycschools.Util
import com.jpmc.nycschools.model.data.StatsInfo
import com.jpmc.nycschools.model.repository.ApiService
import com.jpmc.nycschools.model.repository.RetrofitUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    // Mutable LiveData to hold Stats Information
    private var stats: MutableLiveData<StatsInfo> = MutableLiveData()

    lateinit var job: Job
    private val context = getApplication<Application>().applicationContext


    fun registerForStats(): LiveData<StatsInfo> {
        return stats
    }

    fun fetchStats(dbn: String) {
        if (Util.isNetworkConnected(context)) {
            job = viewModelScope.launch {
                // retrofit call to fetch Stats
                val schoolApi = RetrofitUtil.getInstance().create(ApiService::class.java)

                val response = schoolApi.getStats()
                if (response.isSuccessful) {
                    // filtering out stats based on dbn
                    val info = response.body()?.firstOrNull {
                        it.dbn == dbn
                    }
                    // sending result to observer i.e Activity
                    stats.postValue(info)
                } else {
                    stats.postValue(null)
                }
            }
        }else{
            stats.postValue(null)
        }
        }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) {
            job.cancel()
        }
    }
    }
