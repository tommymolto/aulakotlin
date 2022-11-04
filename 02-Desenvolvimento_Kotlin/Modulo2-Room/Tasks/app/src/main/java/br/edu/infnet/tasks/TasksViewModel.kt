package br.edu.infnet.tasks
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
class TasksViewModel(val dao: TaskDao, val categoriaDao: CategoriaDAO, val visaoTaskDAO: VisaoTaskDAO) : ViewModel() {
    var newTaskName = ""
    var newTaskCategory = 1
    private var _listaItems = mutableListOf<Task>()
    private var tasks = dao.getAll()
    private var categorias = categoriaDao.getAll()
    private var _listaCategorias =  mutableListOf<Categoria>()

    private var tarefas = visaoTaskDAO.getTasksComCategorias()
    private var _listaTarefas =  mutableListOf<TaskComCategoria>()
    fun liveTarefas(): LiveData<List<TaskComCategoria>>{
        return tarefas
    }
    fun listaTarefas() : List<TaskComCategoria> {
        tarefas = visaoTaskDAO.getTasksComCategorias()
        if (tarefas?.value?.isNotEmpty() == true){
            _listaTarefas = tarefas.value as MutableList<TaskComCategoria>
        }
        //_listaItems = (tasks.value as List<Task>).toMutableList()
        return _listaTarefas

    }
    val tasksString = Transformations.map(tasks) {
            tasks -> formatTasks(tasks)
    }
    fun liveTasks(): LiveData<List<Task>> {
        return tasks
    }
    fun listaTasks() : List<Task> {
        tasks = dao.getAll()
        if (tasks?.value?.isNotEmpty() == true){
            _listaItems = tasks.value as MutableList<Task>
        }
        //_listaItems = (tasks.value as List<Task>).toMutableList()
        return _listaItems

    }
    fun addTask() {
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            task.categoriaId=newTaskCategory
            dao.insert(task)
        }
    }
    fun deleteTask(id: Long){
        viewModelScope.launch {
            val task = Task()
            task.taskId = id
            dao.delete(task)
            listaTasks()
        }

    }
    fun updateTask(t: Task){
        viewModelScope.launch {

            t.taskDone = !t.taskDone
            dao.update(t)
            listaTasks()
        }
    }
    fun formatTasks(tasks: List<Task>): String {
        return tasks.fold("") {
                str, item -> str + '\n' + formatTask(item)
        }
    }
    fun formatTask(task: Task): String {
        var str = "ID: ${task.taskId}"
        str += '\n' + "Name: ${task.taskName}"
        str += '\n' + "Complete: ${task.taskDone}" + '\n'
        return str
    }


    fun listaCategorias() : List<Categoria> {
        categorias = categoriaDao.getCategorias()
        if (categorias.value?.isNotEmpty() == true){
            Log.d("TEM cat", categorias.toString())
            _listaCategorias = categorias.value as MutableList<Categoria>
        }
        //_listaItems = (tasks.value as List<Task>).toMutableList()
        return _listaCategorias

    }
    fun liveCategorias(): LiveData<List<Categoria>> {
        return categorias
    }
}