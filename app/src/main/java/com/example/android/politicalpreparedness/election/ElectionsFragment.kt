package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.repository.ElectionsRepository

class ElectionsFragment : Fragment() {

    private lateinit var database: ElectionDatabase
    private lateinit var repository: ElectionsRepository
    private val viewModel: ElectionsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(
            this,
            ElectionsViewModelFactory(repository)
        ).get(ElectionsViewModel::class.java)

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

        val upcomingAdapter = ElectionListAdapter((ElectionListAdapter.ElectionListener {
            viewModel.displayVoterInfoFragment(it)
        }))

        val followedAdapter = ElectionListAdapter((ElectionListAdapter.ElectionListener {
            viewModel.displayVoterInfoFragment(it)
        }))

        binding.upcomingElectionsRecycler.adapter = upcomingAdapter
        binding.upcomingElectionsRecycler.setHasFixedSize(true)
        binding.savedElectionsRecycler.adapter = followedAdapter
        binding.savedElectionsRecycler.setHasFixedSize(true)

        viewModel.upcomingElections.observe(viewLifecycleOwner, Observer {
            it.let {
                upcomingAdapter.submitList(it)
            }
        })

        viewModel.savedElections.observe(viewLifecycleOwner, Observer {
            it.let {
                followedAdapter.submitList(it)
            }
        })

        viewModel.navigateToVoterInfo.observe(viewLifecycleOwner, Observer { election ->
            election?.let {
                findNavController().navigate(
                    ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(
                        it.id,
                        it.division
                    )
                )
                viewModel.displayVoterInfoFragmentComplete()
            }
        })

        return binding.root
    }

}