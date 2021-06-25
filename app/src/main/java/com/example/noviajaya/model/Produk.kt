package com.example.noviajaya.model

class Produk {
    lateinit var id_produk:String
    lateinit var nama_produk:String
    lateinit var harga_produk:String
    lateinit var deskripsi:String
    lateinit var gambar:String

    constructor() {}
    constructor(id_produk:String, nama_produk:String, harga_produk:String, deskripsi:String, gambar:String) {
        this.id_produk = id_produk
        this.nama_produk = nama_produk
        this.harga_produk = harga_produk
        this.deskripsi = deskripsi
        this.gambar = gambar
    }
}