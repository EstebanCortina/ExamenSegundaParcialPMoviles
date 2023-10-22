package com.example.examensegundaparcial
import com.example.examensegundaparcial.CustomListAdapter

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import java.lang.Error

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val cantidad: Int,
    val precioCosto: Double,
    val precioVenta: Double,
    val url: String


){
    override fun toString(): String {
        return "Id: $id, Nombre: $nombre, Descripción: $descripcion, Cantidad: $cantidad, Precio Costo: $$precioCosto, Precio Venta: $$precioVenta"
    }
}

class MainActivity : AppCompatActivity() {
    val items = mutableListOf<Producto>()

    lateinit var et1: EditText
    lateinit var etNombre: EditText
    lateinit var et2: EditText
    lateinit var etCantidad: EditText
    lateinit var etPrecioCosto: EditText
    lateinit var et3: EditText
    lateinit var etUrl: EditText

    lateinit var boton1: Button
    lateinit var boton2: Button
    lateinit var boton5: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et1 = findViewById(R.id.et1)
        etNombre = findViewById(R.id.etNombre)
        et2 = findViewById(R.id.et2)
        etCantidad = findViewById(R.id.etCantidad)
        etPrecioCosto = findViewById(R.id.etPrecioCosto)
        et3 = findViewById(R.id.et3)
        etUrl = findViewById(R.id.etUrl)

        boton1 = findViewById(R.id.boton1)
        boton2 = findViewById(R.id.boton2)
        boton5 = findViewById(R.id.boton5)


        getAllItems()

        //Crear Fruta
        boton1.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("nombre", etNombre.getText().toString())
            registro.put("descripcion", et2.getText().toString())
            registro.put("cantidad", etCantidad.getText().toString().toInt())
            registro.put("precioCosto", etPrecioCosto.getText().toString().toDouble())
            registro.put("precioVenta", et3.getText().toString().toDouble())
            registro.put("url", etUrl.getText().toString())

            val check = bd.insert("frutas", null, registro)
            if (check != -1L) {
                // La inserción fue exitosa, result contiene el ID de la fila insertada
                showToast("Nueva fruta creada")
                getAllItems()
            } else {
                // Ocurrió un error durante la inserción
                showToast("Error al crear la fruta")
            }
            bd.close()
            etNombre.setText("")
            et2.setText("")
            et3.setText("")
            etPrecioCosto.setText("")
            etCantidad.setText("")
            etUrl.setText("")
        }

        //Buscar por codigo
        boton2.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select * from frutas where id=${et1.text.toString()}", null)
            items.clear()
            if (fila.moveToFirst()) {
                try {
                    val producto = Producto(
                        fila.getString(0).toInt(),
                        fila.getString(1),
                        fila.getString(2),
                        fila.getString(3).toInt(),
                        fila.getString(4).toDouble(),
                        fila.getString(5).toDouble(),
                        fila.getString(6)
                    )
                    items.add(producto)
                } catch (elm: Error) {
                    print(elm)
                }
            } else
                Toast.makeText(this, "No existe un artículo con dicho código", Toast.LENGTH_SHORT)
                    .show()
            updateListView()
            bd.close()
        }


        //Editar registro
        boton5.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("nombre", etNombre.getText().toString())
            registro.put("descripcion", et2.getText().toString())
            registro.put("cantidad", etCantidad.getText().toString().toInt())
            registro.put("precioCosto", etPrecioCosto.getText().toString().toDouble())
            registro.put("precioVenta", et3.getText().toString().toDouble())
            registro.put("url", etUrl.getText().toString())
            val cant = bd.update("frutas", registro, "id=${et1.text.toString()}", null)
            getAllItems()
            bd.close()
            if (cant == 1)
                Toast.makeText(this, "Fruta editada", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(
                    this,
                    "No existe una fruta con el código ingresado",
                    Toast.LENGTH_SHORT
                ).show()
            et1.isEnabled = true
            clearEditTexts()
        }
    }

    //Asigna la info a los Edit Text para editar la fruta
    private  fun setData(id: String) {
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("select * from frutas where id=${id}", null)
        if (fila.moveToFirst()) {
            try {
                et1.setText(fila.getString(0).toString())
                etNombre.setText(fila.getString(1).toString())
                et2.setText(fila.getString(2).toString())
                etCantidad.setText(fila.getString(3).toString())
                etPrecioCosto.setText(fila.getString(4).toString())
                et3.setText(fila.getString(5).toString())
                etUrl.setText(fila.getString(6).toString())
            } catch (elm: Error) {
                print(elm)
            }
        } else
            Toast.makeText(this, "No existe un artículo con dicho código", Toast.LENGTH_SHORT)
                .show()
        bd.close()
    }

    //Borra el registro por id
    private fun delete(id: String){
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("frutas", "id=${id}", null)
        bd.close()

        if (cant == 1)
            Toast.makeText(this, "Se borró la fruta", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        getAllItems()

    }

    //Lista todos los elementos
    private fun getAllItems(){
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("select * from frutas", null)
        print(fila)

        items.clear()
        updateListView()
        while (fila.moveToNext()) {
            try {
                val producto = Producto(
                    fila.getString(0).toInt(),
                    fila.getString(1),
                    fila.getString(2),
                    fila.getString(3).toInt(),
                    fila.getString(4).toDouble(),
                    fila.getString(5).toDouble(),
                    fila.getString(6))
                items.add(producto)
               updateListView()
            }catch (elm: Error){
                print(elm)
            }
        }
        bd.close()
    }

    //Actualiza el list View para ver las frutas
    private fun updateListView(){
        val listView2 = findViewById<ListView>(R.id.listView)
        val newAdapter = CustomListAdapter(this, R.layout.list_item_custom, items)
        listView2.adapter = newAdapter
        newAdapter.notifyDataSetChanged()
        registerForContextMenu(listView2)
    }
    private fun clearEditTexts(){
        et1.setText("")
        etNombre.setText("")
        et2.setText("")
        etCantidad.setText("")
        etPrecioCosto.setText("")
        et3.setText("")
        etUrl.setText("")
    }

    //Esto nada mas infla el menu contextual
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }

    //Estas son las logicas de cada opcion cuando se le da long click
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedItemPosition = info.position
        val selectedProducto = items[selectedItemPosition]
        et1.isEnabled = false
        return when (item.itemId) {
            R.id.Editar -> {

                setData(selectedProducto.id.toString())
                true
            }
            R.id.Eliminar -> {
                delete(selectedProducto.id.toString())
                et1.isEnabled = true
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    //Lo que le pongas lo va a mostrar en un toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}




