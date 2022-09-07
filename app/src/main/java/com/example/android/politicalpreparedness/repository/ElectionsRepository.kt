package com.example.android.politicalpreparedness.repository

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election

class ElectionsRepository (private val dataSource: ElectionDatabase) {

    fun getElectionList(): LiveData<List<Election>> {
        return  dataSource.electionDao.getElections()
    }



    suspend fun fetchUpcomingElections() {
        val response = CivicsApi.retrofitService.getElections()
        dataSource.electionDao.insertAll(response.elections as ArrayList<Election>)
    }
}