package com.jpmc.nycschools.model.repository

import com.jpmc.nycschools.model.data.SchoolInfo
import com.jpmc.nycschools.model.data.StatsInfo
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    // Suspended methods to fetch schools list
    @GET("resource/s3k6-pzi2.json")
    suspend fun getSchoolInfo(): Response<List<SchoolInfo>>

    // method to fetch stats
    @GET("resource/f9bf-2cp4.json")
    suspend fun getStats(): Response<List<StatsInfo>>
}