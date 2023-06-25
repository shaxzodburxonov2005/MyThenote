package com.example.mythenote

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mythenote.databinding.FragmentAnimatsionBinding
import com.example.mythenote.sharedPr.MySharedPreference


class AnimatsionFragment : Fragment() {
    lateinit var binding:FragmentAnimatsionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       Handler(Looper.getMainLooper()).postDelayed({
           findNavController().navigate(R.id.action_animatsionFragment_to_firstFragment2)
       },4000)

        val view= inflater.inflate(R.layout.fragment_animatsion, container, false)
        binding= FragmentAnimatsionBinding.bind(view)



        return view
    }


}