package com.example.mythenote.viewModelPc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mythenote.model.InformationDB
import com.example.mythenote.model.Teeth
import com.example.mythenote.model.Users
import com.example.mythenote.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    fun insertPost(users: Users) {
        viewModelScope.launch {
            mainRepository.insertPost(users)
        }
    }

    fun getAllTeeth() = mainRepository.getAllTeeth()

    fun getAllUsers() = mainRepository.getAllPost()

    fun updateTeeth(teeth: Teeth) = mainRepository.updateTeeth(teeth)
    fun findById(id: String): String {
        return mainRepository.findById(id)
    }

    fun insertTeeth(list: List<Teeth>) {
        viewModelScope.launch {
            mainRepository.insertTeeth(list)
        }
    }

    fun insertInfo(informationDB: InformationDB): Long {
        return mainRepository.insertInfo(informationDB)
    }

    fun getAllInfo() = mainRepository.getAllInfo()

    fun getAllWithUser() = mainRepository.getUserWithInfo()

    fun findByIdWith(id: String): Int {
        return mainRepository.getIdByName(id)
    }

    fun getByIdInfo(id: Int) = mainRepository.getIdByInfo(id)

    fun getSearch(name: String) = mainRepository.getSearch(name)

    fun deleted(users: Users){
        viewModelScope.launch {
            mainRepository.userDeleted(users)
        }
    }

}