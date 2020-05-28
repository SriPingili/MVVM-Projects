package com.example.shoppinglist.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
        @ColumnInfo(name = "item_name")
        val name: String,
        @ColumnInfo(name = "item_quantity")
        var quantity: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}