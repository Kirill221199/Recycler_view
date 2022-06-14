package ru.kirill.recycler_view.simplerecyclerview

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import ru.kirill.recycler_view.R
import ru.kirill.recycler_view.databinding.FragmentRecyclerViewBinding


class RecyclerViewFragment : Fragment(), OnListItemClickListener {

    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: RecyclerViewAdapter

    val list = arrayListOf(
        DataRecyclerView("Header","", TYPE_HEADER),
        DataRecyclerView("Earth", "Earth Description", TYPE_EARTH),
        DataRecyclerView("Mars", "Mars Description", TYPE_MARS),
        DataRecyclerView("Earth", "Earth Description", TYPE_EARTH),
        DataRecyclerView("Earth", "Earth Description", TYPE_EARTH),
        DataRecyclerView("Mars", "Mars Description", TYPE_MARS),
        DataRecyclerView("Mars", "Mars Description", TYPE_MARS),
        DataRecyclerView("Earth", "Earth Description", TYPE_EARTH),
        DataRecyclerView("Mars", "Mars Description", TYPE_MARS)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_item -> {
                alert()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter(){
        adapter  = RecyclerViewAdapter(list,this)
        adapter.setList(list)
        binding.recyclerview.adapter = adapter
    }

    override fun onItemClickListener(data: DataRecyclerView) {
        TODO("Not yet implemented")
    }

    override fun onAddBtnClick(position: Int, type: Int) {
        when (type){
            1 ->{
                list.add(position, DataRecyclerView("Mars", "Mars Description", TYPE_MARS))
                adapter.setAddToList(list, position,type)
            }
            2 -> {
                list.add(position, DataRecyclerView("Earth", "Earth Description", TYPE_EARTH))
                adapter.setAddToList(list, position,type)
            }
        }
    }

    override fun onRemoveBtnClick(position: Int) {
        list.removeAt(position)
        adapter.setRemoveToList(list, position)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecyclerViewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun alert() {
        AlertDialog.Builder(context)
            .setMessage("What do you want add?")
            .setPositiveButton("Mars") { dialog: DialogInterface?, which: Int ->
                onAddBtnClick(list.size, TYPE_MARS)
            }
            .setNegativeButton("Earth") { dialog: DialogInterface?, which: Int ->
                onAddBtnClick(list.size, TYPE_EARTH)
            }
            .setNeutralButton("Cancel"){dialog: DialogInterface?, which: Int ->
                dialog?.cancel()
            }
            .show()
    }

}