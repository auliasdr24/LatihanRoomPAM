package com.aulia.latihanroompam

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MakananViewModel(private val dao: MakananDao) : ViewModel() {

    // LiveData yang ngambil data dari DAO
    val semuaMakanan: LiveData<List<Makanan>> = dao.getAllMakanan()

    // Fungsi Insert (jalan di background thread)
    fun insert(makanan: Makanan) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(makanan)
        }
    }

    // Fungsi Update
    fun update(makanan: Makanan) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(makanan)
        }
    }

    // Fungsi Delete
    fun delete(makanan: Makanan) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(makanan)
        }
    }
}

// Factory buat bikin ViewModel dengan parameter DAO
class MakananViewModelFactory(private val dao: MakananDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MakananViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MakananViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}