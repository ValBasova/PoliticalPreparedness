package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.repository.ElectionsRepository

class ElectionsFragment : Fragment() {

    private lateinit var database: ElectionDatabase
    private lateinit var repository: ElectionsRepository
    private val viewModel: ElectionsViewModel by lazy {
                val activity = requireNotNull(this.activity) {
                    "You can only access the viewModel after onViewCreated()"
                }
                ViewModelProvider(this, ElectionsViewModelFactory(repository)).get(ElectionsViewModel::class.java)

            }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this

        database = ElectionDatabase.getInstance(requireContext())
        repository = ElectionsRepository(database)

        binding.viewModel = viewModel

        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters

        return binding.root
    }

    //TODO: Refresh adapters when fragment loads

}