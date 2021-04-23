package com.example.noviajaya.model

class Akun {
    lateinit var id:String
    lateinit var nama:String
    lateinit var email:String
    lateinit var username:String
    lateinit var password:String
    lateinit var jenis_kelamin:String
    lateinit var tanggal_lahir:String
    lateinit var alamat:String
    lateinit var telp:String
    lateinit var level:String
    lateinit var foto:String

    constructor() {}
    constructor(id:String, nama:String, email:String, username:String, password:String, jenis_kelamin:String,
                tanggal_lahir:String, alamat:String, telp:String, level:String, foto:String) {
        this.id = id
        this.nama = nama
        this.email = email
        this.username = username
        this.password = password
        this.jenis_kelamin = jenis_kelamin
        this.tanggal_lahir = tanggal_lahir
        this.alamat = alamat
        this.telp = telp
        this.level = level
        this.foto = foto
    }
}