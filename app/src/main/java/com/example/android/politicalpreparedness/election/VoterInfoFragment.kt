package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.repository.ElectionsRepository

class VoterInfoFragment : Fragment() {

    private lateinit var database: ElectionDatabase
    private lateinit var repository: ElectionsRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this

        database = ElectionDatabase.getInstance(requireContext())
        repository = ElectionsRepository(database)
        val arguments = VoterInfoFragmentArgs.fromBundle(arguments!!)
        val viewModelFactory = VoterInfoViewModelFactory(arguments.argElectionId, repository)
        val viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(VoterInfoViewModel::class.java)

        binding.viewModel = viewModel


        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */

        viewModel.votingLocationUrl.observe(viewLifecycleOwner, Observer { url ->
            if (url != null && url.trim().isNotEmpty()) {
                openWebsite(url)
                viewModel.onClickVotingLocationUrlComplete()
            }
        })

        viewModel.ballotInfoUrl.observe(viewLifecycleOwner, Observer { url ->
            if (url != null && url.trim().isNotEmpty()) {
                openWebsite(url)
                viewModel.onClickBallotInfoUrlComplete()
            }
        })

        viewModel.isFollowed.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.followButton.text = getString(R.string.unfollow)
            } else {
                binding.followButton.text = getString(R.string.follow)
            }
        })

        return binding.root
    }

    private fun openWebsite(url: String?) {
        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}