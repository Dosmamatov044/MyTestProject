package com.example.mytestproject.ui.images.models

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.graphics.luminance
import androidx.lifecycle.MutableLiveData

class ImagesRepository(val application: Application) {
    val imageMutableLiveData: MutableLiveData<MutableList<ImagesModel>> = MutableLiveData()
    val imageSizeMutableLiveData: MutableLiveData<MutableList<ImagesModel>> = MutableLiveData()


    init {
        getImageSizeFromCursor()
    }


    fun getImageFromCursor(totalSize: Int): MutableLiveData<MutableList<ImagesModel>> {

        val galleryImageUrls = mutableListOf<ImagesModel>()
        val columns = arrayOf(MediaStore.Images.Media._ID)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        val selArg = arrayOf("0")
        application.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
            "${columns[0]} > ?", selArg, "$orderBy DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)


            while (cursor.moveToNext() && cursor.position < totalSize) {


                val id = cursor.getLong(idColumn)
                galleryImageUrls.add(
                    ImagesModel(
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id
                        ).toString(), id
                    )
                )
                imageMutableLiveData.value = galleryImageUrls


            }


            cursor.close()
        }


        return imageMutableLiveData
    }


    fun getImageSizeFromCursor(): MutableLiveData<MutableList<ImagesModel>> {

        val galleryImageUrls = mutableListOf<ImagesModel>()
        val columns = arrayOf(MediaStore.Images.Media._ID)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        val selArg = arrayOf("0")
        application.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
            "${columns[0]} > ?", selArg, "$orderBy DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                galleryImageUrls.add(
                    ImagesModel(
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id
                        ).toString(), id
                    )
                )
                imageSizeMutableLiveData.value = galleryImageUrls


            }


            cursor.close()
        }


        return imageSizeMutableLiveData
    }


}