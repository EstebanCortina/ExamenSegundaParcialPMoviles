package com.example.examensegundaparcial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class CustomListAdapter(context: Context, resource: Int, items: List<Producto>) :
    ArrayAdapter<Producto>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.list_item_custom, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val id = view.findViewById<TextView>(R.id.etId)
        val nombreTextView = view.findViewById<TextView>(R.id.nombreTextView)
        val descripcionTextView = view.findViewById<TextView>(R.id.descripcionTextView)
        val cantidad = view.findViewById<TextView>(R.id.etListCantidad)
        val precioCosto = view.findViewById<TextView>(R.id.etListPrecioCosto)
        val precioVenta = view.findViewById<TextView>(R.id.etListPrecioVenta)

        val producto = getItem(position)

        id.text = "Id: ${producto?.id.toString()}"
        nombreTextView.text = "Nombre: ${producto?.nombre}"
        descripcionTextView.text = "Descripcion: ${producto?.descripcion}"
        cantidad.text = "Cantidad: ${producto?.cantidad.toString()}"
        precioCosto.text = "Precio Costo: $${producto?.precioCosto.toString()}"
        precioVenta.text = "Precio Venta: $${producto?.precioVenta.toString()}"
        val imageUrl = producto?.url

        Glide.with(context)
            .load(imageUrl)
            .into(imageView)

        return view
    }
}
