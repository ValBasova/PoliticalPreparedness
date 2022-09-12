package com.example.android.politicalpreparedness.repository

import com.example.android.politicalpreparedness.network.CivicsApi

class RepresentativeRepository() {

    suspend fun getRepresentativesByAddress(address: String) =
        CivicsApi.retrofitService.getRepresentatives(address)

}