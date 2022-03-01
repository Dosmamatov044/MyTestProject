package com.example.mytestproject.ui.images.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.mytestproject.ui.images.models.DataStoreRepository
import com.example.mytestproject.ui.images.models.ImagesModel
import com.example.mytestproject.ui.images.models.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
class ImagesViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStoreRepository = DataStoreRepository(application)
    private val repository: ImagesRepository = ImagesRepository(getApplication())
    var imageM: MutableLiveData<MutableList<ImagesModel>> = repository.imageMutableLiveData
    var imageSizeM: MutableLiveData<MutableList<ImagesModel>> = repository.imageSizeMutableLiveData



    fun updateImagesSize(totalSize: Int) {
        repository.getImageFromCursor(totalSize)
    }


    fun saveLastCount(photoLastCount: Int) =viewModelScope.launch(Dispatchers.IO){
        dataStoreRepository.saveImageCount(photoLastCount)
    }

    val readLastCount= dataStoreRepository.readImageCount.asLiveData()


}