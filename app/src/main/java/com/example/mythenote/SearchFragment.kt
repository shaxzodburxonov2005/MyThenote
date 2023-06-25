package com.example.mythenote

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mythenote.adapterRv.AdapterRv
import com.example.mythenote.databinding.FragmentSearchBinding
import com.example.mythenote.model.Users
import com.example.mythenote.viewModelPc.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: AdapterRv
    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)
        showSoftKeyboard(requireContext(), binding.editTvSearch)

        adapter = AdapterRv(requireContext(), object : AdapterRv.Next {
            override fun nextData(article: Users, position: Int) {
                val bundle=Bundle()
                bundle.putSerializable("data",article)
                findNavController().navigate(R.id.secondFragment,bundle)
            }

            override fun save(article: Users, position: Int) {

            }

        })

        binding.editTvSearch.addTextChangedListener {
            if (it?.length == 0) {
                Toast.makeText(requireContext(), "Enter information to search ", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.getSearch(it.toString()).collect() {edit->
                            adapter.submitList(edit)
                            Log.d("RRR", "onCreateView: $it")

                        }
                    }

                }
            }
        }
        viewModel.getAllUsers()

            binding.rvSearch.adapter = adapter

            return view
        }
    private fun showSoftKeyboard(context: Context, editText: EditText) {
        try {
            editText.requestFocus()
            editText.postDelayed(
                {
                    val keyboard =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    keyboard.showSoftInput(editText, 0)
                }, 200
            )
        } catch (npe: NullPointerException) {
            npe.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}


