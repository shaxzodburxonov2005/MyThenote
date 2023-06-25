package com.example.mythenote

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mythenote.databinding.FragmentBlankBinding
import com.example.mythenote.databinding.ItemDialogBinding
import com.example.mythenote.databinding.ItemGallaryBinding
import com.example.mythenote.model.InformationDB
import com.example.mythenote.model.InformationWithTeeth
import com.example.mythenote.model.Teeth
import com.example.mythenote.model.Users
import com.example.mythenote.viewModelPc.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class BlankFragment : Fragment() {
    lateinit var binding: FragmentBlankBinding
    private val viewModel: MainViewModel by viewModels()
    var imgUri: String? = null
    private val galleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                getImageContent.launch("image/*")
            }

        }
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult
            binding.gallery.setImageURI(uri)
            val stream = requireContext().contentResolver.openInputStream(uri!!)
            val file = File(requireContext().filesDir, "image.jpg")
            val outputStream = FileOutputStream(file)
            stream?.copyTo(outputStream)
            stream?.close()
            imgUri = file.absolutePath

        }

    private fun savedInstance() {
        findNavController().navigate(R.id.cameraFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        binding = FragmentBlankBinding.bind(view)

        val a = arguments?.getSerializable("new") as Users

//        val customDialog= AlertDialog.Builder(requireContext()).create()
//        val dialogView=layoutInflater.inflate(
//            R.layout.item_dialog,binding.root,false
//        )
//        customDialog.setView(dialogView)
//        val bind= ItemDialogBinding.bind(dialogView)
//

        val allTeeth: List<InformationWithTeeth> = viewModel.getAllTeeth()
        // adapterga wuni jonatasiz
        // bosilganda keyingi oynaga icidigi listi jonatasz
    //    allTeeth[0].teeths -> bu qaysi tiwla davolangaligi royxati
     //   aynan bosilga userni listini jonatib keyingi oynada wu listga qarab checkboxisi togirlab  ciqasz

    //    chundizmi? // tushunganimcha qilay aga
        Log.d("AAA", "onCreateView: $allTeeth")

        // check boxla qata ?
        binding.saveData.setOnClickListener {
            val historyInfo = binding.medicalHistory.text.toString()
            val one = binding.onTheLastDayOfTreatment.text.toString()
            val dayDate = binding.dateoftreatment.text.toString()
            val daysCome = binding.daystocome.text.toString()


            val infor =
                InformationDB(null, historyInfo, one, dayDate, daysCome, a.id!!, imgUri)

            val id = viewModel.insertInfo(infor)


            val list = ArrayList<Teeth>()
            for (i in 0 until 32) {
                list.add(Teeth(userId = id.toInt()))
            }
            viewModel.insertTeeth(list)
            infor.id = viewModel.findByIdWith(historyInfo)


//                    infor.id=viewModel.findByIdWith(historyInfo)
        }
//        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        customDialog.show()
        gallaryfun()
        return view
    }

    private fun gallaryfun() {
        binding.gallery.setOnClickListener {
            val customDialog = AlertDialog.Builder(requireContext()).create()
            val dialogView = layoutInflater.inflate(
                R.layout.item_gallary, binding.root, false
            )
            customDialog.setView(dialogView)
            val bind = ItemGallaryBinding.bind(dialogView)
            bind.gallery.setOnClickListener {
                galleryPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            bind.camera.setOnClickListener {
                if (allPermissonGranted()) {
                    savedInstance()
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        REQUIRED_PERMISSIONS,
                        REQUEST_CODE_PERMISSIONS
                    )
                }
                customDialog.show()
            }

        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "key"
        )
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                binding.imgRe.visibility = View.VISIBLE
                binding.imgRe.setImageURI(Uri.parse(result))
                imgUri = result

            }
    }

    private fun allPermissonGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val Tag = "CameraXAPP"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = mutableListOf(
            android.Manifest.permission.CAMERA
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissonGranted()) {
                savedInstance()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permissions not granted by the user",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}