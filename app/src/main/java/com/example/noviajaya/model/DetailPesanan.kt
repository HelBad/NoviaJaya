package com.example.noviajaya.model

class DetailPesanan {
    lateinit var id_pesanan:String
    lateinit var id_produk:String
    lateinit var jumlah:String
    lateinit var harga:String
    lateinit var total:String

    constructor() {}
    constructor(id_pesanan:String, id_produk:String, jumlah:String, harga:String, total:String) {
        this.id_pesanan = id_pesanan
        this.id_produk = id_produk
        this.jumlah = jumlah
        this.harga = harga
        this.total = total
    }
}