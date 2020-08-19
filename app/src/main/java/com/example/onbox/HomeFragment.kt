package com.example.onbox

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission


class HomeFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnScan : Button = view.findViewById(R.id.ButtonScan)
        btnScan.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ButtonScan -> {
//                Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()

                val permissionlistener: PermissionListener = object : PermissionListener {
                    override fun onPermissionGranted() {
//                        Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT)
//                            .show()
                        showScanFragment()
                    }

                    override fun onPermissionDenied(deniedPermissions: List<String>) {
                        Toast.makeText(
                            context,
                            "Permission Denied\n$deniedPermissions",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_scanQRFragment_to_homeFragment)
                    }
                }

                TedPermission.with(context)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.CAMERA)
                    .check()
            }
        }
    }

    private fun showScanFragment(){
        findNavController().navigate(R.id.action_homeFragment_to_scanQRFragment)
    }

}