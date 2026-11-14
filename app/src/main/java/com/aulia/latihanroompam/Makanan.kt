package com.aulia.latihanroompam

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "makanan_table")
data class Makanan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val harga: Int,
    val deskripsi: String,
    val kategori: String
)