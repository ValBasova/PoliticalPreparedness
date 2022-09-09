package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.State
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch

class VoterInfoViewModel(
    private val electionId: Int = 0,
    private val repository: ElectionsRepository
) : ViewModel() {

    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    private val _isFollowed = MutableLiveData<Boolean>()
    val isFollowed :  LiveData<Boolean>
        get() = _isFollowed

    private val _state = MutableLiveData<State>()
    val state :  LiveData<State>
        get() = _state

    init {
        viewModelScope.launch {
            _election.value = repository.getSavedElectionById(electionId)
            _isFollowed.value = _election.value!!.followed
            fetchVoterInfoByElectionId()
        }
    }

    fun onFollow() {
        _isFollowed.value = !(_isFollowed.value)!!
        _election.value?.followed = isFollowed.value == true
        viewModelScope.launch {
            repository.updateFollow(_election.value!!)
        }
    }

    //TODO: Add var and methods to support loading URLs
    suspend fun fetchVoterInfoByElectionId() {
        try {
            _state.value  = repository.getVoterInfoById("New York", electionId)
        } catch (e: Exception) {
            Log.e("Get VoteInfo API error", e.message.toString())
        }
    }

}