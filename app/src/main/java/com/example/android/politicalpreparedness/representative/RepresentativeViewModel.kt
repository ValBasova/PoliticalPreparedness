package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.repository.RepresentativeRepository
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel : ViewModel() {
    private var repository: RepresentativeRepository

    private val _representatives = MutableLiveData<MutableList<Representative>>()
    val representatives: LiveData<MutableList<Representative>>
        get() = _representatives

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    var name = MutableLiveData<String>()
    val address = MutableLiveData<Address>()

    init {
        repository = RepresentativeRepository()
        address.value = Address("","","","","")
    }

    fun fetchRepresentatives() {
        viewModelScope.launch {
            try{
              val representativeResponse= address.value?.let {
                  repository.getRepresentativesByAddress(
                      it.toFormattedString())
              }
                if (representativeResponse != null) {
                    name.value = representativeResponse.offices.get(0).name
                }

            } catch (e: Exception) {
                name.value = e.message.toString()
            }
        }
    }
    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

    /**
     * Validate the entered data and show error to the user if there's any empty fields
     */
    fun validateEnteredData(enteredAddress: Address): Boolean {
        if (enteredAddress.line1.isNullOrEmpty()) {
            _errorMessage.value = "Please fill in Address Line 1"
            return false
        }
        if (enteredAddress.line2.isNullOrEmpty()) {
            _errorMessage.value = "Please fill in Address Line 2"
            return false
        }

        if (enteredAddress.city.isNullOrEmpty()) {
            _errorMessage.value = "Please fill in City"
            return false
        }

        if (enteredAddress.zip.isNullOrEmpty()) {
            _errorMessage.value = "Please fill in Zip"
            return false
        }
        return true
    }

}
