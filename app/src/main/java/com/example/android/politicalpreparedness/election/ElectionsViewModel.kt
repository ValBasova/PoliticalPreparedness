package com.example.android.politicalpreparedness.election

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.ArrayList


class ElectionsViewModel(private val electionsRepository: ElectionsRepository) : ViewModel() {

    //        val upcomingElections = electionsRepository.getElectionList()
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    private val _navigateToVoterInfo = MutableLiveData<Election>()

    val navigateToVoterInfo: LiveData<Election>
        get() = _navigateToVoterInfo

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    init {
        viewModelScope.launch {
            refreshUpcomingElections()
        }
    }

    suspend fun refreshUpcomingElections() {
        try {
//            electionsRepository.fetchUpcomingElections()
            val response = CivicsApi.retrofitService.getElections()
            _upcomingElections.value = response.elections

        } catch (e: Exception) {
            Log.e("Election API error", e.message.toString())
        }
    }

    fun displayVoterInfoFragment(election: Election) {
        _navigateToVoterInfo.value = election
    }

    fun displayVoterInfoFragmentComplete() {
        _navigateToVoterInfo.value = null
    }
}