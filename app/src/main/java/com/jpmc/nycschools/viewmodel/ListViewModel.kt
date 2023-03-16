package com.jpmc.nycschools.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.jpmc.nycschools.Util
import com.jpmc.nycschools.model.data.SchoolInfo
import com.jpmc.nycschools.model.repository.ApiService
import com.jpmc.nycschools.model.repository.RetrofitUtil
import kotlinx.coroutines.Job

import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {


    // Mutable LiveData to hold Stats Information
    private var schools: MutableLiveData<List<SchoolInfo>> = MutableLiveData()
    private var internetInfo: MutableLiveData<Boolean> = MutableLiveData()

    private val context = getApplication<Application>().applicationContext
    lateinit var job: Job


    fun registerForSchoolsInfo(): LiveData<List<SchoolInfo>> {
        return schools
    }



    fun registerForInternalConnectivity(): LiveData<Boolean> {
        return internetInfo
    }


    fun fetchSchools() {

        if (Util.isNetworkConnected(context)) {
            val schoolApi = RetrofitUtil.getInstance().create(ApiService::class.java)
            // launching a new coroutine
            job = viewModelScope.launch {
                val result = schoolApi.getSchoolInfo()
                if (result.isSuccessful) {
                    schools.postValue(result.body())
                } else {
                    schools.postValue(null)
                }
            }
        }else {
            internetInfo.postValue(false)
        }
    }
    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) {
            job.cancel()
        }
    }
}