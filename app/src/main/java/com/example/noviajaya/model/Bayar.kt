package com.example.noviajaya.model

class Bayar {
    lateinit var id_bayar:String
    lateinit var id_pesanan:String
    lateinit var bukti:String
    lateinit var status_bayar:String

    constructor() {}
    constructor(id_bayar:String, id_pesanan:String, bukti:String, status_bayar:String) {
        this.id_bayar = id_bayar
        this.id_pesanan = id_pesanan
        this.bukti = bukti
        this.status_bayar = status_bayar
    }
}