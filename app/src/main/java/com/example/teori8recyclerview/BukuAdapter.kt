package com.example.teori8recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BukuAdapter(
    private val daftar: MutableList<Buku>,
    private val onKlik: (Buku) -> Unit,
    private val onTahan: (Int) -> Unit
) : RecyclerView.Adapter<BukuAdapter.BukuViewHolder>() {

    // ViewHolder: menyimpan referensi View satu baris
    class BukuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvJudul: TextView = itemView.findViewById(R.id.tvJudul)
        val tvPenulis: TextView = itemView.findViewById(R.id.tvPenulis)
    }

    // 1. Membuat View baru (hanya dipanggil bila perlu)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_buku, parent, false)
        return BukuViewHolder(view)
    }

    // 2. Mengisi View dengan data posisi tertentu (dipanggil saat menggulir)
    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        val buku = daftar[position]
        holder.tvJudul.text = buku.judul
        holder.tvPenulis.text = "${buku.penulis} · ${buku.tahun}"

        holder.itemView.setOnClickListener { onKlik(buku) }
        holder.itemView.setOnLongClickListener {
            val posisi = holder.bindingAdapterPosition
            // jaga-jaga: posisi bisa tidak valid saat daftar sedang berubah
            if (posisi != RecyclerView.NO_POSITION) onTahan(posisi)
            true
        }
    }

    // 3. Jumlah data
    override fun getItemCount(): Int = daftar.size

    // Menambah item di akhir daftar, lalu memberi tahu RecyclerView
    fun tambah(buku: Buku) {
        daftar.add(buku)
        notifyItemInserted(daftar.size - 1)
    }

    // Menghapus item, lalu memberi tahu RecyclerView
    fun hapus(posisi: Int) {
        daftar.removeAt(posisi)
        notifyItemRemoved(posisi)
    }
}