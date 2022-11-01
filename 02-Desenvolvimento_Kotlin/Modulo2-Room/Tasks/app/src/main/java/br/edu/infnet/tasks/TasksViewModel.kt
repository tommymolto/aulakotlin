package br.edu.infnet.tasks
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
class TasksViewModel(val dao: TaskDao) : ViewModel() {
    var newTaskName = ""
    private var _listaItems = mutableListOf<Task>()
    private var tasks = dao.getAll()
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
}