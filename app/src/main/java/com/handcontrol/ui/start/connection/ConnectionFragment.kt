package com.handcontrol.ui.start.connection

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.handcontrol.R
import com.handcontrol.api.Api
import com.handcontrol.api.HandlingType


class ConnectionFragment : Fragment() {
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val okButton: Button = view.findViewById(R.id.okButton) as Button

        val radioGroup: RadioGroup = view.findViewById(R.id.radios) as RadioGroup
        val bluetoothButton: RadioButton = view.findViewById(R.id.radioButton_bluetooth)
        if (bluetoothAdapter == null) {
            bluetoothButton.isEnabled = false
            bluetoothButton.visibility = View.INVISIBLE
        }
        radioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.radioButton_grpc -> {
                    okButton.setOnClickListener {
                        Api.setHandlingType(HandlingType.GRPC)
                        it.findNavController().navigate(R.id.action_connectionFragment_to_loginFragment)
                    }
                }
                R.id.radioButton_bluetooth -> {
                    okButton.setOnClickListener {
                        if (bluetoothAdapter!!.isEnabled) {
                            navigateBluetooth()
                        } else {
                            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
                        }
                    }
                }
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK)
                navigateBluetooth()
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    private fun navigateBluetooth() {
        context?.let {
            val pairedDevices = bluetoothAdapter!!.bondedDevices.toList()
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.choose_device_title))
                .setItems(pairedDevices.map { item -> item.name }.toTypedArray()) { _, i ->
                    Api.setHandlingType(HandlingType.BLUETOOTH)
                    Api.setBluetoothAddress(pairedDevices[i].address)
                    activity?.finish()
                    findNavController().navigate(R.id.action_global_navigation)
                }
                .show()
        }
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 1
    }
}