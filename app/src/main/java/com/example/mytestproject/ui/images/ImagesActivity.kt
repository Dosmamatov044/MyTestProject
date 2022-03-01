package com.example.mytestproject.ui.images

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mytestproject.R
import com.example.mytestproject.databinding.ActivityImagesBinding
import com.example.mytestproject.helpers.InputFilterMinMax
import com.example.mytestproject.helpers.PERMISSION_REQUEST_CODE
import com.example.mytestproject.helpers.makeToast
import com.example.mytestproject.ui.images.adapter.ImagesAdapter
import com.example.mytestproject.ui.images.models.ImagesModel
import com.example.mytestproject.ui.images.viewModels.ImagesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ImagesActivity : AppCompatActivity() {
    lateinit var binding: ActivityImagesBinding
    private val viewModels: ImagesViewModel by viewModels()
    lateinit var adapter: ImagesAdapter
    lateinit var list: MutableList<ImagesModel>

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermissions()
        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        updateUi()
    }

    private fun updateUi() {
        viewModels.imageSizeM.observe(this) {
            binding.inputImagesSize.filters =
                arrayOf<InputFilter>(InputFilterMinMax("1", it.size.toString()))
            //  makeToast(it.size.toString())
        }


        binding.saveImagesSize.setOnClickListener {
            if (binding.inputImagesSize.text.isNotEmpty()) {
                val imagesSize = binding.inputImagesSize.text.toString().toInt()
                viewModels.saveLastCount(imagesSize)


                viewModels.updateImagesSize(imagesSize)
                viewModels.imageM.observe(this) {
                    adapter.images = it

                }

            } else {
                makeToast(applicationContext.getString(R.string.enter_the_number))
            }

        }

    }

    private fun initAdapter() {
        adapter = ImagesAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter

        viewModels.readLastCount.observe(this) {

            if (it != 0) {
                viewModels.readLastCount.value?.let { it1 -> viewModels.updateImagesSize(it1) }

                viewModels.imageM.observe(this) {
                    list = it
                    adapter.images = it
                }
            } else {
                makeToast("empty")
            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun getPermissions() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                Log.d("IsPermissions", "Все получен")
            }
            else -> {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.

                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    Log.d("IsPermissions", "Получен")
                } else {
                    makeToast("permissions not denied")
                    Log.d("IsPermissions", "Не получен")
                }
                return
            }


            else -> {
            }
        }
    }

}