package com.example.examensegundaparcial

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory

class AdminSQLiteOpenHelper(context: Context, name: String, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        //db.execSQL("DROP TABLE IF EXISTS frutas")
        db.execSQL("CREATE TABLE frutas(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, cantidad INTEGER, precioCosto REAL, precioVenta REAL, url TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}