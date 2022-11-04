package br.edu.infnet.tasks
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
class TasksViewModelFactory(private val dao: TaskDao, private val categoriaDAO: CategoriaDAO, private val visaoTaskDAO: VisaoTaskDAO)
    : ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return TasksViewModel(dao, categoriaDAO, visaoTaskDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}