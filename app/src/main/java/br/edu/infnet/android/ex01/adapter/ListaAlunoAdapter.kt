package br.edu.infnet.android.ex01.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.android.ex01.R
import br.edu.infnet.android.ex01.model.Aluno

class ListaAlunoAdapter(var listaAlunos: List<Aluno>): RecyclerView.Adapter<ListaAlunoAdapter.ListaAlunoViewholder>() {
    class ListaAlunoViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtNomeAluno: TextView =
            itemView.findViewById(R.id.txtNomeAlunoCard)
        val txtNotaUmAlunoCard: TextView =
            itemView.findViewById(R.id.txtNotaUmAlunoCard)
        val txtNotaDoisAlunoCard: TextView =
            itemView.findViewById(R.id.txtNotaDoisAlunoCard)
        val ivImagemAlunoCard: ImageView =
            itemView.findViewById(R.id.ivImagemAlunoCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaAlunoViewholder {
        val card = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.aluno_card, parent, false)
        return ListaAlunoViewholder(card)
    }

    override fun onBindViewHolder(holder: ListaAlunoViewholder, position: Int) {
        //holder.ivImagemAlunoCard.setI
        if(listaAlunos[position].nome.isNotEmpty()){
            //implementa a imagem no ivImagemAlunoCard
        }
        holder.txtNomeAluno.text = listaAlunos[position].nome
        holder.txtNotaUmAlunoCard.text = listaAlunos[position].notaUm.toString()
        holder.txtNotaDoisAlunoCard.text = listaAlunos[position].notaDois.toString()
    }

    override fun getItemCount(): Int {
        return listaAlunos.size
    }
}