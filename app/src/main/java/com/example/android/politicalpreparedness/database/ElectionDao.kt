package com.example.android.politicalpreparedness.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(asteroids: ArrayList<Election>)
//
//    @Query("SELECT * FROM election_table")
//    fun getElections(): LiveData<List<Election>>

    //TODO: Add select single election query

    //TODO: Add delete query

    //TODO: Add clear query

}