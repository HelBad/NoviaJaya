package com.example.noviajaya.model

class Pesanan {
    lateinit var id_pesanan:String
    lateinit var id_user:String
    lateinit var tanggal:String
    lateinit var lokasi:String
    lateinit var total_pembayaran:String
    lateinit var status_pembayaran:String
    lateinit var status_pesanan:String

    constructor() {}
    constructor(id_pesanan:String, id_user:String, tanggal:String, lokasi:String, total_pembayaran:String,
                status_pembayaran:String, status_pesanan:String) {
        this.id_pesanan = id_pesanan
        this.id_user = id_user
        this.tanggal = tanggal
        this.lokasi = lokasi
        this.total_pembayaran = total_pembayaran
        this.status_pembayaran = status_pembayaran
        this.status_pesanan = status_pesanan
    }
}