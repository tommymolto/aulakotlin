package br.edu.infnet.android.firebaseinfnet

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.android.firebaseinfnet.Model.Perfil
typealias onItemChange = (perfil: Perfil)->Unit
class PerfilAdapter(
    var perfis: MutableList<Perfil>,
    private val onItemUpdate: onItemChange,
    private val onItemDelete: onItemChange,
) : RecyclerView.Adapter<PerfilAdapter.PerfilViewHolder>() {
    inner class PerfilViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
 {
    val primeironome: TextView = itemView.findViewById(R.id.txtPerfilPrimeiroNome)
    val ultimonome: TextView = itemView.findViewById(R.id.txtPerfilUltimoNome)
    val ano: TextView = itemView.findViewById(R.id.txtPerfilAno)
    val btEdit: Button = itemView.findViewById(R.id.btnEdit)
    val btDelete: Button = itemView.findViewById(R.id.btnDeletar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.perfil_item,parent,false)
        Log.d("onCreateViewHolder()","RecyclerAdapter.onCreateViewHolder() called")

        return PerfilViewHolder(itemView)
    }
    fun update(perfilList:MutableList<Perfil>){
        perfis = perfilList
        Log.d("Info", "atualiza")
        this.notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: PerfilViewHolder, position: Int) {
        holder.primeironome.text = perfis[position].primeiroNome
        holder.ultimonome.text = perfis[position].ultimoNome
        holder.ano.text = perfis[position].anoNascimento?.toString()
        holder.btEdit.setOnClickListener {
            onItemUpdate(Perfil(
                perfis[position].userId,
                perfis[position].primeiroNome,
                perfis[position].ultimoNome,
                perfis[position].anoNascimento

            ))
        }
        holder.btDelete.setOnClickListener {
            onItemDelete(Perfil(
                perfis[position].userId,
                perfis[position].primeiroNome,
                perfis[position].ultimoNome,
                perfis[position].anoNascimento

            ))
        }

    }

    override fun getItemCount(): Int {
        return perfis.size
    }

}