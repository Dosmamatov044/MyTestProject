package com.example.mytestproject.ui.aboutMe.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytestproject.ui.aboutMe.models.AboutMeModel
import com.example.mytestproject.ui.aboutMe.models.AboutMeRepositories

class AboutMeViewModel : ViewModel() {
    private val repository: AboutMeRepositories = AboutMeRepositories()
    private var _jsonMutableLiveData = MutableLiveData<List<AboutMeModel>>()
    val jsonMutableLiveData: LiveData<List<AboutMeModel>>
        get() = _jsonMutableLiveData

    init {
        repository.getJson()
        _jsonMutableLiveData = repository.jsonMutableLiveData
    }
}