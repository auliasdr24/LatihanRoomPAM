package com.aulia.latihanroompam

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var makananViewModel: MakananViewModel
    private lateinit var adapter: MakananAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // === 1. Setup Room Database + ViewModel ===
        val dao = MakananDatabase.getDatabase(application).makananDao()
        makananViewModel = ViewModelProvider(this, MakananViewModelFactory(dao))[MakananViewModel::class.java]

        // === 2. Inisialisasi View ===
        val etNama = findViewById<EditText>(R.id.etNama)
        val etHarga = findViewById<EditText>(R.id.etHarga)
        val etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val etKategori = findViewById<EditText>(R.id.etKategori)
        val btnTambah = findViewById<Button>(R.id.btnTambah)
        val rvMakanan = findViewById<RecyclerView>(R.id.rvMakanan)

        // === 3. Setup RecyclerView + Adapter ===
        adapter = MakananAdapter { makanan ->
            // Saat tombol hapus diklik → hapus dari database
            makananViewModel.delete(makanan)
            Toast.makeText(this, "${makanan.nama} dihapus", Toast.LENGTH_SHORT).show()
        }
        rvMakanan.adapter = adapter
        rvMakanan.layoutManager = LinearLayoutManager(this)

        // === 4. Observe LiveData dari Room ===
        // Data otomatis update kalau ada perubahan di database
        makananViewModel.semuaMakanan.observe(this, Observer { listMakanan ->
            adapter.submitList(listMakanan)

            // Insert dummy data kalau database masih kosong (cuma sekali)
            if (listMakanan.isEmpty()) {
                insertDummyData()
            }
        })

        // === 5. Tombol Tambah Makanan ===
        btnTambah.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val hargaStr = etHarga.text.toString().trim()
            val deskripsi = etDeskripsi.text.toString().trim()
            val kategori = etKategori.text.toString().trim()

            if (nama.isEmpty() || hargaStr.isEmpty() || deskripsi.isEmpty() || kategori.isEmpty()) {
                Toast.makeText(this, "Lengkapi semua data!", Toast.LENGTH_SHORT).show()
            } else {
                val makanan = Makanan(
                    nama = nama,
                    harga = hargaStr.toInt(),
                    deskripsi = deskripsi,
                    kategori = kategori
                )
                makananViewModel.insert(makanan)
                Toast.makeText(this, "$nama ditambahkan", Toast.LENGTH_SHORT).show()

                // Kosongkan form
                etNama.text.clear()
                etHarga.text.clear()
                etDeskripsi.text.clear()
                etKategori.text.clear()
            }
        }
    }

    // Insert 5 menu awal (dari project UTS) kalau database kosong
    private fun insertDummyData() {
        val dummyList = listOf(
            Makanan(nama = "Kopi Susu Gula Aren", harga = 22000, deskripsi = "Kopi susu dengan gula aren asli", kategori = "Kopi"),
            Makanan(nama = "Donat Cokelat", harga = 12000, deskripsi = "Donat lembut dengan topping cokelat", kategori = "Nyemil"),
            Makanan(nama = "Sourdough Loaf", harga = 35000, deskripsi = "Roti sourdough dipanggang segar", kategori = "Roti"),
            Makanan(nama = "Matcha Latte", harga = 27000, deskripsi = "Matcha premium dengan susu segar", kategori = "Non-Kopi"),
            Makanan(nama = "Cappuccino", harga = 25000, deskripsi = "Espresso dengan susu dan foam", kategori = "Kopi")
        )
        dummyList.forEach { makananViewModel.insert(it) }
    }
}