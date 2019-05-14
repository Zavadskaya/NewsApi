package com.example.user.news.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import java.util.*

class FragmentDialog : DialogFragment() {

    companion object {
        @JvmStatic
        private var TAG: String = "FragmentDialog"
    }

    var done: Button? = null
    var cancel: Button? = null

    lateinit var spinerCountry: Spinner

    interface OnInputListener {
        fun sendInput(input: String)
    }

    lateinit var mOutput: OnInputListener


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val countries = Locale.getISOCountries()
        val _view: View = activity!!.layoutInflater.inflate(com.example.user.news.R.layout.custom_layout, null)


        spinerCountry = _view.findViewById(com.example.user.news.R.id.dialogCountry)

        val adapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_item,
            countries
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinerCountry.adapter = adapter

        loadView()

        this.done = _view.findViewById<Button>(com.example.user.news.R.id.dialogDoneBtn)
        this.cancel = _view.findViewById<Button>(com.example.user.news.R.id.dialogCancelBtn)

        val alert = AlertDialog.Builder(activity)
        alert.setView(_view)
        alert.setTitle("Filter panel")

        this.done!!.setOnClickListener {

            val a: String = spinerCountry.selectedItem.toString()

            mOutput.sendInput(a /*(getActivity() as NewsFragment).getPosition()*/)

            saveData(spinerCountry.selectedItemPosition)
            dialog.dismiss()

        }
        this.cancel!!.setOnClickListener {
            Log.d(TAG, "onClick:closing dialog")
            dialog.dismiss()

        }


        return alert.create()

    }

    private fun saveData(position: Int) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefEditor = prefs.edit()
        prefEditor.putInt("saved", position)
        prefEditor.apply()
        Toast.makeText(context, "Data saved", Toast.LENGTH_LONG).show()


    }

    private fun loadView() {
        val getItm = PreferenceManager.getDefaultSharedPreferences(context).getInt("saved", 0)
        spinerCountry.setSelection(getItm)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mOutput = (context as OnInputListener?)!!
        } catch (e: ClassCastException) {
            Log.e(TAG, "onAttach:ClassCastException" + e.message)
        }
    }
}