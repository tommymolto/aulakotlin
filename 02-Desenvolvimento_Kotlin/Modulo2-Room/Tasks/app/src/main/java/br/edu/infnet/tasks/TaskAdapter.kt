package br.edu.infnet.tasks

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
typealias onItemDelete = (position:Long)->Unit
typealias onItemUpdate = (task: Task)->Unit

class TaskAdapter(
    private var _itemsList: List<Task>?,
    private val onItemDelete: onItemDelete,
    private val onItemUpdate: onItemUpdate,
): RecyclerView.Adapter<TaskAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView){
        val nomeTextView: TextView = itemView.findViewById(R.id.txtTarefa)
        val btnDelete: Button  = itemView.findViewById(R.id.btnDelete)
        val btnStatus: Button = itemView.findViewById(R.id.btnStatus)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_task, parent, false)

        //val nomeTextNumber: TextView = itemView.findViewById(R.id.itemQuant)
        Log.d("onCreateViewHolder()", "RecyclerAdapter.onCreateViewHolder() called")

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskAdapter.ItemViewHolder, position: Int) {
        holder.nomeTextView.text = _itemsList?.get(position)?.taskName
        if(_itemsList?.get(position)?.taskDone == true){
            holder.nomeTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        holder.btnDelete.setOnClickListener {
            _ -> onItemDelete(_itemsList?.get(position)?.taskId!!)
        }
        holder.btnStatus.setOnClickListener {
            _ -> onItemUpdate(_itemsList?.get(position)!!)
        }
        //holder.nomeTextNumber.text = _itemsList[position].quant

        Log.d("onBindViewHolder()", "called")
        Log.d("onBindViewHolder()", "${holder.nomeTextView.text}")
    }

    override fun getItemCount(): Int {
        if (_itemsList?.isNotEmpty() == true) {
            Log.d("INFO", "Tem")
            return _itemsList?.size!!
        }
        return 0

    }
    fun updateTaskList(taskList: List<Task>) {
        _itemsList = taskList
        notifyDataSetChanged()
    }
}
