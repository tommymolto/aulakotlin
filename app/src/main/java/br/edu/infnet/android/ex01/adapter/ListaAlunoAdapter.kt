package br.edu.infnet.android.ex01.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.android.ex01.R
import br.edu.infnet.android.ex01.model.Aluno

class ListaAlunoAdapter:
    RecyclerView.Adapter<ListaAlunoAdapter.ListaAlunoViewholder>() {
    val listaAlunos: List<Aluno> = listOf(
        Aluno("x",5.0,6.0),
        Aluno("x1",7.0,6.0),
     Aluno("x2",9.0,6.0)
    )
    class ListaAlunoViewholder(itemView: View):
        RecyclerView.ViewHolder(itemView)
        {
            // Referência para a TextView que será
            // configurada com nome
            val txtNomeAluno: TextView =
                itemView.findViewById(R.id.txtNomeAlunoCard)
        val txtNotaUmAlunoCard: TextView =
            itemView.findViewById(R.id.txtNotaUmAlunoCard)
        val txtNotaDoisAlunoCard: TextView =
            itemView.findViewById(R.id.txtNotaDoisAlunoCard)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaAlunoViewholder {
        val card = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.aluno_card, parent, false)
        return ListaAlunoViewholder(card)
    }

    override fun onBindViewHolder(holder: ListaAlunoViewholder, position: Int) {
        holder.txtNomeAluno.text = listaAlunos[position].nome
        holder.txtNotaUmAlunoCard.text = listaAlunos[position].notaUm.toString()
        holder.txtNotaDoisAlunoCard.text = listaAlunos[position].notaDois.toString()
    }

    override fun getItemCount(): Int {
        return listaAlunos.size
    }
}