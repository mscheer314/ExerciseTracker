package com.example.android.exercisetracker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.exercisetracker.database.AppRepository
import com.example.android.exercisetracker.models.BodyType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BodyTypeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    val allBodyTypes: LiveData<List<BodyType>>

    init {
        repository = AppRepository(application)
        allBodyTypes = repository.allBodyTypes
    }

    fun insert(bodyType: BodyType) =
        viewModelScope.launch(Dispatchers.IO) { repository.insert(bodyType) }

}