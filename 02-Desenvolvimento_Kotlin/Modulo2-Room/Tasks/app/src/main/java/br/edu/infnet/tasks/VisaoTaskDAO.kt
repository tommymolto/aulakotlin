package br.edu.infnet.tasks

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface VisaoTaskDAO {
    @Query(
        "select task_table.taskId, task_table.task_name, task_table.task_done , categoria_table.categoria_name\n" +
                "from task_table \n" +
                "left join categoria_table on task_table.categoriaId = categoria_table.categoriaId"
    )
    fun getTasksComCategorias(): LiveData<List<TaskComCategoria>>
}

data class TaskComCategoria(
    val taskId: Int,
    val taskName: String,
    val taskDone: Boolean,
    val categoriaName: String?)


/*

Ou
data class TaskComCategoria(
    @Embedded val categoria: Categoria,
    @Relation(
         parentColumn = "categoriaId",
         entityColumn = "categoriaId"
    )
    val task: Task
)
 */