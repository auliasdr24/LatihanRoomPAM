package com.aulia.latihanroompam

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface MakananDao {
    @Insert
    suspend fun insert(makanan: Makanan)

    @Update
    suspend fun update(makanan: Makanan)

    @Delete
    suspend fun delete(makanan: Makanan)

    @Query("SELECT * FROM makanan_table ORDER BY id DESC")
    fun getAllMakanan(): LiveData<List<Makanan>>

    @Query("SELECT COUNT(*) FROM makanan_table")
    suspend fun getCount(): Int
}