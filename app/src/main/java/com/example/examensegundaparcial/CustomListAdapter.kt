package com.example.examensegundaparcial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class CustomListAdapter(context: Context, resource: Int, items: List<Producto>) :
    ArrayAdapter<Producto>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.list_item_custom, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val nombreTextView = view.findViewById<TextView>(R.id.nombreTextView)
        val descripcionTextView = view.findViewById<TextView>(R.id.descripcionTextView)

        val producto = getItem(position)
        nombreTextView.text = producto?.nombre
        descripcionTextView.text = producto?.descripcion

        val imageUrl = producto?.url

        Glide.with(context)
            .load(imageUrl)
            .into(imageView)

        return view
    }
}
