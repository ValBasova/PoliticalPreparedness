package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.repository.ElectionsRepository

class VoterInfoFragment : Fragment() {

    private lateinit var database: ElectionDatabase
    private lateinit var repository: ElectionsRepository

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this

        database = ElectionDatabase.getInstance(requireContext())
        repository = ElectionsRepository(database)
        val arguments = VoterInfoFragmentArgs.fromBundle(arguments!!)
        val viewModelFactory = VoterInfoViewModelFactory(arguments.argElectionId, repository)
        val viewModel =  ViewModelProvider(
            this, viewModelFactory
        ).get(VoterInfoViewModel::class.java)

        binding.viewModel = viewModel

        val electionId = VoterInfoFragmentArgs.fromBundle(requireArguments()).argElectionId

        return binding.root

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

    }

    //TODO: Create method to load URL intents

}