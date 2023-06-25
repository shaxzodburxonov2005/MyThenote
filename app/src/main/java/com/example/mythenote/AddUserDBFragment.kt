package com.example.mythenote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mythenote.databinding.FragmentAddUserDBBinding
import com.example.mythenote.model.Users
import com.example.mythenote.viewModelPc.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AddUserDBFragment : Fragment() {
    lateinit var binding: FragmentAddUserDBBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_user_d_b, container, false)
        binding = FragmentAddUserDBBinding.bind(view)

        dataFun(binding.birthdayTv.toString())
        binding.button.setOnClickListener {
            if (binding.lastName.length() == 0 && binding.firstName.length() == 0 || binding.addressTv.length() == 0 || binding.birthdayTv.length() == 0) {
                binding.lastName.error = "Ism kiritish shart"
                binding.firstName.error = "Familya kiritish shart"
                binding.birthdayTv.error = "Tugulgan sanani kiriting"
                binding.addressTv.error = "Manzil kiritish shart"
                binding.numberTv.error = "Raqam kiritish shart"

            } else {
                val firstName = binding.firstName.text.toString()
                val lastName = binding.lastName.text.toString()
                val addressDB = binding.addressTv.text.toString()
                val phoneNumber = binding.numberTv.text.toString()


                val user = Users(firstName, lastName, addressDB, phoneNumber, dataFun(binding.birthdayTv.toString()))
                viewModel.insertPost(user)
                binding.firstName.text.clear()
                binding.lastName.text.clear()
                binding.addressTv.text.clear()
                binding.numberTv.text.clear()
                binding.birthdayTv.text.clear()
            }


        }
        binding.backpop.setOnClickListener {
            findNavController().popBackStack()
        }








        return view
    }


    fun dataFun(dateDb:String):String{
        binding.birthdayTv.addTextChangedListener(object : TextWatcher {
            var current = ""
            val ddmmyyyy = "DDMMYYYY"
            val cal: Calendar = Calendar.getInstance()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != current) {
                    var clean = s.toString().replace("[^\\d.]".toRegex(), "")
                    val cleanC = current.replace("[^\\d.]".toRegex(), "")
                    val cl = clean.length
                    var sel = cl
                    var i = 2
                    while (i <= cl && i < 6) {
                        sel++
                        i += 2
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean == cleanC) sel--
                    if (clean.length < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length)
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        var day = clean.substring(0, 2).toInt()
                        var mon = clean.substring(2, 4).toInt()
                        var year = clean.substring(4, 8).toInt()
                        if (mon > 12) mon = 12
                        cal.set(Calendar.MONTH, mon - 1)
                        year = if (year < 1900) 1900 else if (year > 2100) 2100 else year
                        cal.set(Calendar.YEAR, year)
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012
                        day = if (day > cal.getActualMaximum(Calendar.DATE)) cal.getActualMaximum(
                            Calendar.DATE
                        ) else day
                        clean = String.format("%02d%02d%02d", day, mon, year)
                    }
                    clean = String.format(
                        "%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8)
                    )
                    sel = if (sel < 0) 0 else sel
                    current = clean
                    binding.birthdayTv.setText(current)
                    binding.birthdayTv.setSelection(if (sel < current.length) sel else current.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        return dateDb
    }


}
