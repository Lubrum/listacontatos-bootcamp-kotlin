package com.everis.listadecontatos.feature.listacontatos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everis.listadecontatos.databinding.ItemContatoBinding
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO

class ContatoAdapter(
    private val context: Context,
    private val lista: List<ContatosVO>,
    private val onClick: ((Int) -> Unit)
) : RecyclerView.Adapter<ContatoViewHolder>() {

    private lateinit var binding: ItemContatoBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        binding = ItemContatoBinding.inflate(LayoutInflater.from(context), parent,false)
        return ContatoViewHolder(binding)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val contato = lista[position]
        with(holder.itemView){
            binding.tvNome.text = contato.nome
            binding.tvTelefone.text = contato.telefone
            binding.llItem.setOnClickListener { onClick(contato.id) }
        }
    }

}

class ContatoViewHolder(itemView: ItemContatoBinding) : RecyclerView.ViewHolder(itemView.root)