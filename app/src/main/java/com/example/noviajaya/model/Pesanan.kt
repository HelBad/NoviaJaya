package com.example.noviajaya.model

class Pesanan {
    lateinit var id_pesanan:String
    lateinit var id_user:String
    lateinit var nama_produk:String
    lateinit var waktu:String
    lateinit var tanggal:String
    lateinit var lokasi:String
    lateinit var jumlah:String
    lateinit var total:String
    lateinit var status:String

    constructor() {}
    constructor(id_pesanan:String, id_user:String, nama_produk:String, waktu:String, tanggal:String,
                lokasi:String, jumlah:String, total:String, status:String) {
        this.id_pesanan = id_pesanan
        this.id_user = id_user
        this.nama_produk = nama_produk
        this.waktu = waktu
        this.tanggal = tanggal
        this.lokasi = lokasi
        this.jumlah = jumlah
        this.total = total
        this.status = status
    }
}