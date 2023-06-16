package com.capstone.perigigiapps.ui.screen.deteksi

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.auth0.android.jwt.JWT
import com.capstone.perigigiapps.databinding.FragmentSectionBinding
import com.capstone.perigigiapps.di.Injection
import com.capstone.perigigiapps.network.NetworkResult
import com.capstone.perigigiapps.utils.createCustomTempFile
import com.capstone.perigigiapps.utils.reduceFileImage
import com.capstone.perigigiapps.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class SectionFragment : Fragment() {

    private var _binding: FragmentSectionBinding? = null
    private val binding get() = _binding!!
    private var getFile: File? = null
    private lateinit var deteksiViewModel: DeteksiViewModel
    private lateinit var historyAdapter: HistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var position: Int? = 0
        val userRepository = Injection.provideUserRepository()
        val factory = DeteksiViewModel.DeteksiViewModelFactory(userRepository)
        deteksiViewModel = ViewModelProvider(this, factory)[DeteksiViewModel::class.java]
        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }
        if (position == 1) {
            binding.apply {
                previewImageView.isVisible = true
                cameraButton.isVisible = true
                uploadButton.isVisible = true
                galleryButton.isVisible = true
                divider.isVisible = true
                rvHistory.isVisible = false
            }
        } else {
            binding.apply {
                previewImageView.isVisible = false
                cameraButton.isVisible = false
                uploadButton.isVisible = false
                galleryButton.isVisible = false
                divider.isVisible = false
                history()
            }
        }

        binding.cameraButton.setOnClickListener { startTakePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener { uploadImage() }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun history() {
        val sharedPreferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "").orEmpty()
        val jwt = JWT(token)
        val id = jwt.getClaim("id").asInt()
        deteksiViewModel.getHistory(id, token = token)
            .observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is NetworkResult.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is NetworkResult.Success -> {
                            binding.progressBar.isVisible = false
                            val response = result.data.data
                            historyAdapter = HistoryAdapter(response!!)
                            binding.rvHistory.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.rvHistory.adapter = historyAdapter
                        }

                        is NetworkResult.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
    }

    private fun uploadImage() {
        val sharedPreferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "").orEmpty()

        val jwt = JWT(token)
        val id = jwt.getClaim("id").asInt()

        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/*".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )
            deteksiViewModel.postPredict(imageMultipart, id, token = token)
                .observe(viewLifecycleOwner) { result ->
                    if (result != null) {
                        when (result) {
                            is NetworkResult.Loading -> {
                                binding.progressBar.isVisible = true
                            }

                            is NetworkResult.Success -> {
                                binding.progressBar.isVisible = false
                                if (result.data.data?.scanningResult == "Gigi Sehat") {
                                    val sweetAlertDialog = SweetAlertDialog(
                                        requireContext(),
                                        SweetAlertDialog.SUCCESS_TYPE
                                    )
                                    sweetAlertDialog.titleText = "Gigi Sehat!"
                                    sweetAlertDialog.contentText =
                                        "Gigi anda tampak sehat, pertahankan kesehatan gigi anda"
                                    sweetAlertDialog.show()
                                    sweetAlertDialog.setConfirmClickListener { sweetAlertDialog ->
                                        // Handle Confirm button click
                                        sweetAlertDialog.dismissWithAnimation()
                                        // Perform any additional actions here
                                    }
                                } else if (result.data.data?.scanningResult == "Gigi Tidak Sehat") {
                                    val sweetAlertDialog = SweetAlertDialog(
                                        requireContext(),
                                        SweetAlertDialog.ERROR_TYPE
                                    )
                                    sweetAlertDialog.titleText = "Gigi Tidak Sehat!"
                                    sweetAlertDialog.contentText =
                                        "Gigi anda tampak tidak sehat, anda disarankan untuk melakukan pemeriksaan ke dokter gigi"
                                    sweetAlertDialog.show()
                                    sweetAlertDialog.setConfirmClickListener { sweetAlertDialog ->
                                        // Handle Confirm button click
                                        sweetAlertDialog.dismissWithAnimation()
                                        // Perform any additional actions here
                                    }
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        result.data.data?.scanningResult,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        is NetworkResult.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        } else {
            binding.uploadButton.isVisible = false
        }

    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)
        createCustomTempFile(requireContext().applicationContext).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.capstone.perigigiapps",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            myFile.let { file ->
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
                getFile = file
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireActivity())
                getFile = myFile
                binding.previewImageView.setImageURI(uri)
            }
        }
    }

    companion object {
        const val ARG_POSITION = " "
    }

}