package com.example.mythenote.categoryfr

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mythenote.viewModelPc.MainViewModel
import com.example.mythenote.R
import com.example.mythenote.adapterRv.AdapterRv
import com.example.mythenote.adapterRv.AdapterSc
import com.example.mythenote.databinding.FragmentSecondBinding
import com.example.mythenote.databinding.ItemDialogBinding
import com.example.mythenote.databinding.ItemInfoBinding
import com.example.mythenote.model.InformationDB
import com.example.mythenote.model.Users
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecondFragment : Fragment() {
    lateinit var binding:FragmentSecondBinding
    lateinit var adapterSc: AdapterSc
    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_second, container, false)
        binding= FragmentSecondBinding.bind(view)

        val  a=arguments?.getSerializable("data") as Users

        binding.secondFirstname.text=a.firstName
        binding.seconDName.text=a.lastName
        binding.secondNumber.text=a.phoneNumber
//        binding.dataSecond.text=a.birthDay


        viewModel.findByIdWith(a.id.toString())
        binding.nextAdd.setOnClickListener {
            val bundle=Bundle()
            bundle.putSerializable("new",a)
            findNavController().navigate(R.id.blankFragment,bundle)

//            val customDialog= AlertDialog.Builder(requireContext()).create()
//                val dialogView=layoutInflater.inflate(
//                    R.layout.item_dialog,binding.root,false
//                )
//                customDialog.setView(dialogView)
//            val bind= ItemDialogBinding.bind(dialogView)
//
//
//                bind.saveData.setOnClickListener {
//                    val historyInfo=bind.medicalHistory.text.toString()
//                    val one=bind.onTheLastDayOfTreatment.text.toString()
//                    val dayDate=bind.dateoftreatment.text.toString()
//                    val daysCome=bind.daystocome.text.toString()
//                    val infor=InformationDB(null,historyInfo,one,dayDate,daysCome,a.id!!)
//                    viewModel.insertInfo(infor)
//                    infor.id=viewModel.findByIdWith(historyInfo)
//
//
////                    infor.id=viewModel.findByIdWith(historyInfo)
//                }
//                customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                customDialog.show()
        }


        adapterSc=AdapterSc(requireContext(),object :AdapterSc.ItemOnClick{
            override fun nextData(article: InformationDB, position: Int) {
                val customDialog= AlertDialog.Builder(requireContext()).create()
                val dialogView=layoutInflater.inflate(
                    R.layout.item_info,binding.root,false
                )
                customDialog.setView(dialogView)
                val bind= ItemInfoBinding.bind(dialogView)
                bind.infoDialog1.text=article.historyDb
                bind.dialog3.text=article.patientData
                bind.dialog5.text=article.dayPatient

                customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                customDialog.show()
            }


        })



        binding.rvSecond.adapter=adapterSc

        viewModel.getByIdInfo(a.id!!)


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getByIdInfo(a.id!!).collect() {
                    adapterSc.submitList(it)
                    Log.d("RRR", "onCreateView: $it")

                }
            }

        }



        return view
    }


}