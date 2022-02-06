package com.everis.listadecontatos.feature.contato

import android.os.Bundle
import android.view.View
import com.everis.listadecontatos.R
import com.everis.listadecontatos.application.ContatoApplication
import com.everis.listadecontatos.bases.BaseActivity
import com.everis.listadecontatos.databinding.ActivityContatoBinding
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO

class ContatoActivity : BaseActivity() {

    private var idContato: Int = -1

    private lateinit var binding: ActivityContatoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContatoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupToolBar(binding.toolBar, "Contato",true)
        setupContato()
        binding.btnSalvarConato.setOnClickListener { onClickSalvarContato() }
    }

    private fun setupContato(){
        idContato = intent.getIntExtra("index",-1)
        if (idContato == -1){
            binding.btnExcluirContato.visibility = View.GONE
            return
        }
        binding.progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            val lista = ContatoApplication.instance.helperDB?.buscarContatos("$idContato",true) ?: return@Runnable
            val contato = lista.getOrNull(0) ?: return@Runnable
            runOnUiThread {
                binding.etNome.setText(contato.nome)
                binding.etTelefone.setText(contato.telefone)
                binding.progress.visibility = View.GONE
            }
        }).start()
    }

    private fun onClickSalvarContato(){
        val nome = binding.etNome.text.toString()
        val telefone = binding.etTelefone.text.toString()
        val contato = ContatosVO(
            idContato,
            nome,
            telefone
        )
        binding.progress.visibility = View.VISIBLE
        Thread {
            Thread.sleep(1500)
            if (idContato == -1) {
                ContatoApplication.instance.helperDB?.salvarContato(contato)
            } else {
                ContatoApplication.instance.helperDB?.updateContato(contato)
            }
            runOnUiThread {
                binding.progress.visibility = View.GONE
                finish()
            }
        }.start()
    }

    fun onClickExcluirContato() {
        if(idContato > -1){
            binding.progress.visibility = View.VISIBLE
            Thread {
                Thread.sleep(1500)
                ContatoApplication.instance.helperDB?.deletarCoontato(idContato)
                runOnUiThread {
                    binding.progress.visibility = View.GONE
                    finish()
                }
            }.start()
        }
    }
}
