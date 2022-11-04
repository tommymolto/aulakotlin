package br.edu.infnet.tasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
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
        val visaoTaskDAO = TaskDatabase.getInstance(application).visaoTaskDao
        val categoriaDAO = TaskDatabase.getInstance(application).categoriaDao
        val viewModelFactory = TasksViewModelFactory(dao,categoriaDAO, visaoTaskDAO)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(TasksViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        recyclerView= binding.rvTasks

        var categorias= viewModel.listaCategorias() as ArrayList<Categoria>
        Log.d("WAR", categorias.toString())
        preencheSpinner(categorias)
        configureRecyclerView()
        return view
    }
    fun preencheSpinner(categorias: ArrayList<Categoria>){
        var _categorias = categorias
        var dropdown = binding.spinner



        var spadapter: ArrayAdapter<Categoria> =
            ArrayAdapter<Categoria>(requireContext(), android.R.layout.simple_spinner_dropdown_item,
                ArrayList<Categoria>())

        dropdown.adapter = spadapter

        spadapter.notifyDataSetChanged()
        binding.viewModel?.liveCategorias()?.observe( requireActivity()) { ct ->
            _categorias = ct as ArrayList<Categoria>
            spadapter.addAll(ct);
            spadapter.notifyDataSetChanged()

        };
        //dropdown.setSelection(adapter.getPosition(myItem)) //Optional to set the selected item.
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val country: Categoria = _categorias[position]
                Toast.makeText(
                    context,
                    "Country ID: " + country.categoriaId
                        .toString() + ",  Country Name : " + country.categoriaName,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }




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