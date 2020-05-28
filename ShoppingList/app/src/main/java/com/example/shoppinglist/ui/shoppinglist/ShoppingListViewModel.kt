package com.example.shoppinglist.ui.shoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.shoppinglist.data.db.entities.ShoppingItem
import com.example.shoppinglist.data.repositories.ShoppingListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingListViewModel(private val repository: ShoppingListRepository) : ViewModel() {

    fun upsert(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsert(item)
    }

    fun delete(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllShoppingItems() = liveData {
        val some = repository.getAllShoppingItems()
        emitSource(some)
    }
}