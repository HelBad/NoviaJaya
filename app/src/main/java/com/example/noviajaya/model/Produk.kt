package com.example.noviajaya.model

class Produk {
    lateinit var nama_produk:String
    lateinit var harga_produk:String
    lateinit var deskripsi:String
    lateinit var gambar:String

    constructor() {}
    constructor(nama_produk:String, harga_produk:String, deskripsi:String, gambar:String) {
        this.nama_produk = nama_produk
        this.harga_produk = harga_produk
        this.deskripsi = deskripsi
        this.gambar = gambar
    }
}