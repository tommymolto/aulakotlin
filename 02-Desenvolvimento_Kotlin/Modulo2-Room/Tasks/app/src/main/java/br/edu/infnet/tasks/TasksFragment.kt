package br.edu.infnet.tasks
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.tasks.databinding.FragmentTasksBinding
class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var tm: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao
        val viewModelFactory = TasksViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(TasksViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        recyclerView= binding.rvTasks
        configureRecyclerView()
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun configureRecyclerView(){
        //Log.d("configureRecyclerView()","${viewModel.exposeItems().size}")
         tm = TaskAdapter(binding.viewModel?.listaTasks(),
             onListItemDelete,
             onListItemUpdate
         )
        recyclerView.layoutManager  = LinearLayoutManager(activity)
        recyclerView.adapter        = tm
        recyclerView.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )
        binding.viewModel?.liveTasks()?.observe( requireActivity(), taskUpdateObserver);

    }
    private var taskUpdateObserver: Observer<List<Task>> =  Observer<List<Task>>() {
        userArrayList ->
            tm.updateTaskList(userArrayList);
    }

    private var onListItemDelete: ( position:Long) -> Unit = {
        binding.viewModel?.deleteTask(it)
    }
    private var onListItemUpdate: ( task: Task) -> Unit = {
            t: Task ->
        binding.viewModel?.updateTask(t)
    }
   /* private fun onListItemClick(position:Long): Unit{
        binding.viewModel.deleteTask(position)
    }*/
}