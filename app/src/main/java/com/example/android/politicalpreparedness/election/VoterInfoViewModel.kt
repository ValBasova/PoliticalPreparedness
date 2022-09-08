package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.network.models.Election
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


    init {
        viewModelScope.launch {
            _election.value = repository.getSavedElectionById(electionId)
            _isFollowed.value = _election.value!!.followed
        }
    }

    fun onFollow() {
        _isFollowed.value = !(_isFollowed.value)!!
        _election.value?.followed = isFollowed.value == true
        viewModelScope.launch {
            repository.updateFollow(_election.value!!)
        }
    }


    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs


}