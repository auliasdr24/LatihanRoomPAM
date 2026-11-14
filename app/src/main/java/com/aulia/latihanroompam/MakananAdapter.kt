package com.aulia.latihanroompam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MakananAdapter(
    private val onDeleteClick: (Makanan) -> Unit
) : RecyclerView.Adapter<MakananAdapter.MakananViewHolder>() {

    private var makananList = listOf<Makanan>()

    // ViewHolder: pegang view tiap item
    class MakananViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNama)
        val tvHarga: TextView = view.findViewById(R.id.tvHarga)
        val tvDeskripsi: TextView = view.findViewById(R.id.tvDeskripsi)
        val tvKategori: TextView = view.findViewById(R.id.tvKategori)
        val btnHapus: Button = view.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananViewHolder {
        // Inflate layout item_makanan.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_makanan, parent, false)
        return MakananViewHolder(view)
    }

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        val makanan = makananList[position]
        holder.tvNama.text = makanan.nama
        holder.tvHarga.text = "Rp ${"%,d".format(makanan.harga).replace(",", ".")}"
        holder.tvDeskripsi.text = makanan.deskripsi
        holder.tvKategori.text = "Kategori: ${makanan.kategori}"

        // Klik tombol hapus → panggil callback
        holder.btnHapus.setOnClickListener { onDeleteClick(makanan) }
    }

    override fun getItemCount(): Int = makananList.size

    // Update data list (dipanggil dari observer LiveData)
    fun submitList(list: List<Makanan>) {
        makananList = list
        notifyDataSetChanged()
    }
}