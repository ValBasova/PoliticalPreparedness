package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class DetailFragment : Fragment() {

    companion object {
        private val REQUEST_LOCATION_PERMISSION = 1
    }

    private lateinit var viewModel: RepresentativeViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRepresentativeBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(RepresentativeViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //TODO: Define and assign Representative adapter

        //TODO: Populate Representative adapter


        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        binding.buttonLocation.setOnClickListener {
            if (checkLocationPermissions()) {
                getLocation()
            }
        }

        binding.buttonSearch.setOnClickListener {

            val line1 = viewModel.address.value!!.line1
            val line2 = viewModel.address.value!!.line2
            val state = viewModel.address.value!!.state
            val city = viewModel.address.value!!.city
            val zip = viewModel.address.value!!.zip

            val address= Address(line1, line2, city, state, zip)

            if (viewModel.validateEnteredData(address)) {
                viewModel.address.value = address
                viewModel.fetchRepresentatives()
            } else {
                Toast.makeText(requireContext(), viewModel.errorMessage.value, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return binding.root
    }

    private fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            true
        } else {
            requestPermissions(
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.permission_denied_explanation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) === PackageManager.PERMISSION_GRANTED
    }

    private fun getLocation() {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    viewModel.address.value = geoCodeLocation(location)
                    Log.d("MY LOCATION", viewModel.address.value!!.city)

                    viewModel.fetchRepresentatives()
                }
            }

    }
    //The geoCodeLocation method is a helper function to change the lat/long location
    // to a human readable street address

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
            .map { address ->
                Address(
                    address.thoroughfare,
                    address.subThoroughfare,
                    address.locality,
                    address.adminArea,
                    address.postalCode
                )
            }
            .first()
    }

//    private fun hideKeyboard() {
//        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
//    }

}