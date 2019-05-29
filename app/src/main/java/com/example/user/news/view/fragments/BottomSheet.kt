package com.example.user.news.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomSheetDialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.user.news.R
import kotlinx.android.synthetic.main.bottonsheet.*
import java.util.*


class BottomSheet : BottomSheetDialogFragment() {
    lateinit var country: Spinner
    lateinit var category: Spinner


    interface OnInputListener {
        fun sendInput(input: String, output: String)
    }

    lateinit var mOutput: OnInputListener
    private var fragmentView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.bottonsheet, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countries = Locale.getISOCountries()
        val adapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_item,
            countries
        )

        category = fragmentView!!.findViewById(R.id.dCategory)

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        country = fragmentView!!.findViewById(R.id.dCountry)
        country.adapter = adapter
        initView()
    }

    private fun initView() {
        loadView()
        dialogDoneBtn.setOnClickListener {
            val str: String = category.selectedItem.toString()
            val str_e: String = country.selectedItem.toString()
            mOutput.sendInput(str, str_e)
            saveData(country.selectedItemPosition, category.selectedItemPosition)
            Toast.makeText(context, "Save + $str + $str_e", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
    }

    private fun saveData(position: Int, position_2: Int) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefEditor = prefs.edit()
        prefEditor.putInt("saved", position)
        prefEditor.putInt("save", position_2)
        prefEditor.apply()
        Toast.makeText(context, "Data saved", Toast.LENGTH_LONG).show()


    }

    private fun loadView() {
        val getItm = PreferenceManager.getDefaultSharedPreferences(context).getInt("saved", 0)
        country.setSelection(getItm)
        val getItms = PreferenceManager.getDefaultSharedPreferences(context).getInt("save", 0)
        category.setSelection(getItms)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mOutput = (context as OnInputListener?)!!
        } catch (e: ClassCastException) {
            Log.e("Error", "onAttach:ClassCastException" + e.message)
        }
    }
}