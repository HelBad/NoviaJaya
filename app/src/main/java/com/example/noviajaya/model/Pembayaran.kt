package com.example.noviajaya.model

class Pembayaran {
    lateinit var id_pembayaran:String
    lateinit var id_pesanan:String
    lateinit var bukti:String

    constructor() {}
    constructor(id_pembayaran:String, id_pesanan:String, bukti:String) {
        this.id_pembayaran = id_pembayaran
        this.id_pesanan = id_pesanan
        this.bukti = bukti
    }
}