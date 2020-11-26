package com.example.android.exercisetracker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.exercisetracker.database.AppRepository
import com.example.android.exercisetracker.models.Set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository = AppRepository(application)
    val allSets: LiveData<List<Set>>

    init {
        allSets = repository.allSets
    }

    fun insert(set: Set) =
        viewModelScope.launch(Dispatchers.IO) { repository.insert(set) }
}