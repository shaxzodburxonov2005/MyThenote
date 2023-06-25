package com.example.mythenote.categoryfr

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
import com.example.mythenote.databinding.FragmentFirstBinding
import com.example.mythenote.model.Users
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    lateinit var binding:FragmentFirstBinding
    lateinit var adapterRv: AdapterRv

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_first, container, false)
        binding= FragmentFirstBinding.bind(view)

        binding.animationView.setOnClickListener {
            findNavController().navigate(R.id.addUserDBFragment)
        }
        binding.search.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

        adapterView()

//        list=viewModel.getInfo() as ArrayList<InformationDB>
        binding.rv.adapter=adapterRv

        viewModelAdapter()

        return view
    }

    private fun viewModelAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAllUsers ().collect {
                    adapterRv.submitList(it)
                    Log.d("RRR", "onCreateView: $it")

                }
            }

        }
    }

    private fun adapterView() {
        adapterRv= AdapterRv(requireContext(),object :AdapterRv.Next{
            override fun nextData(article: Users, position: Int) {

                val bundle=Bundle()
                bundle.putSerializable("data",article)


                findNavController().navigate(R.id.secondFragment,bundle)

            }

            override fun save(article: Users,position: Int) {

                viewModel.deleted(article)

//                val customDialog=AlertDialog.Builder(requireContext()).create()
//                val dialogView=layoutInflater.inflate(
//                    R.layout.item_dialog,binding.root,false
//                )
//                customDialog.setView(dialogView)
//                val bind=ItemDialogBinding.bind(dialogView)
//
//                bind.saveData.setOnClickListener {
//                    val historyInfo=bind.medicalHistory.text.toString()
//                    val one=bind.onTheLastDayOfTreatment.text.toString()
//                    val dayDate=bind.dateoftreatment.text.toString()
//                    val daysCome=bind.daystocome.text.toString().toInt()
//                    val infor=InformationDB(null,historyInfo,one,dayDate,daysCome,article.id!!)
//                    viewModel.insertInfo(infor)
//                    infor.id=viewModel
//
////                    infor.id=viewModel.findByIdWith(historyInfo)
//                }
//                customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                customDialog.show()
            }

        })
    }


}