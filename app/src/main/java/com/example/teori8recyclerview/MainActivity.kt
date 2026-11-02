package com.example.teori8recyclerview

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: BukuAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnTampilan: Button
    private var modeGrid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerBuku)
        btnTampilan = findViewById(R.id.btnTampilan)

        // 1. LayoutManager: menentukan bentuk daftar (awalnya vertikal)
        aturTampilan()
        // 2. ItemDecoration: pemisah antar baris (jarak transparan 8dp)
        val pemisah = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(this, R.drawable.pemisah)?.let { pemisah.setDrawable(it) }
        recyclerView.addItemDecoration(pemisah)

        // 3. Adapter: menyediakan data dan callback
        adapter = BukuAdapter(
            daftar = siapkanData(),
            onKlik = { buku ->
                Toast.makeText(this, "Dipilih: ${buku.judul}", Toast.LENGTH_SHORT).show()
            },
            onTahan = { posisi -> adapter.hapus(posisi) }
        )
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.btnTambah).setOnClickListener {
            val nomor = adapter.itemCount + 1
            adapter.tambah(Buku("Buku Baru $nomor", "Penulis Baru", 2026))
            recyclerView.scrollToPosition(adapter.itemCount - 1)   // tampilkan baris baru
        }

        // Latihan Soal 4: ganti bentuk daftar <-> grid
        btnTampilan.setOnClickListener {
            modeGrid = !modeGrid
            aturTampilan()
        }
    }

    // Bentuk daftar hanya ditentukan di sini; Adapter dan data tidak berubah
    private fun aturTampilan() {
        recyclerView.layoutManager =
            if (modeGrid) GridLayoutManager(this, 2) else LinearLayoutManager(this)
        btnTampilan.text = if (modeGrid) "Tampilan: Grid" else "Tampilan: Daftar"
    }

    private fun siapkanData(): MutableList<Buku> = mutableListOf(
        Buku("Pemrograman Android", "Budi Santoso", 2022),
        Buku("Dasar Kotlin", "Siti Aminah", 2023),
        Buku("Jetpack Compose", "Andi Pratama", 2024),
        Buku("Desain UI/UX", "Maya Lestari", 2020),
        Buku("Basis Data", "Rudi Hartono", 2019)
    )
}