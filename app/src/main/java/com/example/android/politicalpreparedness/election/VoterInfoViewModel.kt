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

    init {
        viewModelScope.launch {
            _election.value = repository.getSavedElectionById(electionId)
        }
    }

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs


    val isFollowed = Transformations.map(_election) {
        it?.followed
    }

    fun onFollow() {
        _election.value?.followed = !(_election.value?.followed)!!
        viewModelScope.launch {
            repository.updateFollow(_election.value!!)
        }
    }

    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}