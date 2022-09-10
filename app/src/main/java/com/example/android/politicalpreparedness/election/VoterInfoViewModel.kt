package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.State
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch

enum class MainApiStatus { LOADING, ERROR, DONE }

class VoterInfoViewModel(
    private val electionId: Int = 0,
    private val repository: ElectionsRepository
) : ViewModel() {

    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    private val _isFollowed = MutableLiveData<Boolean>()
    val isFollowed: LiveData<Boolean>
        get() = _isFollowed

    private val _state = MutableLiveData<State>()

    private var _votingLocationUrl = MutableLiveData<String>()
    val votingLocationUrl: LiveData<String>
        get() = _votingLocationUrl

    private var _ballotInfoUrl = MutableLiveData<String>()
    val ballotInfoUrl: LiveData<String>
        get() = _ballotInfoUrl

    private val _status = MutableLiveData<MainApiStatus>()
    val status: LiveData<MainApiStatus>
        get() = _status

    var errorMessage: String = ""

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

    suspend fun fetchVoterInfoByElectionId() {
        _status.value = MainApiStatus.LOADING
        try {
            _state.value = repository.getVoterInfoById("Boston", electionId).state?.get(0)
            _status.value = MainApiStatus.DONE
        } catch (e: Exception) {
            errorMessage = e.message.toString()
            _status.value = MainApiStatus.ERROR
            Log.e("Get VoteInfo API error", e.message.toString())
        }
    }

    fun onClickVotingLocationUrl() {
        _votingLocationUrl.value = _state.value?.electionAdministrationBody?.votingLocationFinderUrl
    }

    fun onClickVotingLocationUrlComplete() {
        _votingLocationUrl.value = ""
    }

    fun onClickBallotInfoUrl() {
        _ballotInfoUrl.value = _state.value?.electionAdministrationBody?.ballotInfoUrl
    }

    fun onClickBallotInfoUrlComplete() {
        _ballotInfoUrl.value = ""
    }
}