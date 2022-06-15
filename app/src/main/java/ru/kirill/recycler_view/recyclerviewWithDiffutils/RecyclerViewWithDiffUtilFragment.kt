package ru.kirill.recycler_view.recyclerviewWithDiffutils

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import ru.kirill.recycler_view.R
import ru.kirill.recycler_view.databinding.FragmentRecyclerViewWithDiffUtilBinding
import ru.kirill.recycler_view.recyclerviewWithDiffutils.ItemTouchHelper.ItemTouchHelperCallback
import ru.kirill.recycler_view.simplerecyclerview.DataRecyclerView
import ru.kirill.recycler_view.simplerecyclerview.OnListItemClickListener

class RecyclerViewWithDiffUtilFragment : Fragment(), OnListItemClickListener {

    private var _binding: FragmentRecyclerViewWithDiffUtilBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: RecyclerViewWithDiffUtilsAdapter
    private var isNewList = false

    private fun changeAdapterData() {
        adapter.setList(createItemList(isNewList).map {it})
        isNewList = !isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<DataRecyclerViewWithDiffUtils> {
        return when (instanceNumber) {
            false -> listOf(
                DataRecyclerViewWithDiffUtils(0,"Header", "", TYPE_HEADER),
                DataRecyclerViewWithDiffUtils(1, "Pluto 1", "", TYPE_PLUTO),
                DataRecyclerViewWithDiffUtils(2, "", "Jupiter Description 1", TYPE_JUPITER),
                DataRecyclerViewWithDiffUtils(3, "Pluto 1", "", TYPE_PLUTO),
                DataRecyclerViewWithDiffUtils(4, "Pluto 1", "", TYPE_PLUTO),
                DataRecyclerViewWithDiffUtils(5, "", "Jupiter Description 1", TYPE_JUPITER),
                DataRecyclerViewWithDiffUtils(6, "", "Jupiter Description 1", TYPE_JUPITER),
                DataRecyclerViewWithDiffUtils(7, "Pluto 1", "", TYPE_PLUTO),
                DataRecyclerViewWithDiffUtils(8, "", "Jupiter Description 1", TYPE_JUPITER)
            )
            true -> listOf(
                DataRecyclerViewWithDiffUtils(0,"Header", "", TYPE_HEADER),
                DataRecyclerViewWithDiffUtils(1, "Pluto 2", "", TYPE_PLUTO),
                DataRecyclerViewWithDiffUtils(2, "", "Jupiter Description 2", TYPE_JUPITER),
                DataRecyclerViewWithDiffUtils(3, "Pluto 2", "", TYPE_PLUTO),
                DataRecyclerViewWithDiffUtils(4, "Pluto 2", "", TYPE_PLUTO),
                DataRecyclerViewWithDiffUtils(5, "", "Jupiter Description 2", TYPE_JUPITER),
                DataRecyclerViewWithDiffUtils(6, "", "Jupiter Description 2", TYPE_JUPITER),
                DataRecyclerViewWithDiffUtils(7, "Pluto 2", "", TYPE_PLUTO),
                DataRecyclerViewWithDiffUtils(8, "", "Jupiter Description 2", TYPE_JUPITER)
            )
        }
    }

    val list = arrayListOf(
        DataRecyclerViewWithDiffUtils(0,"Header", "", TYPE_HEADER),
        DataRecyclerViewWithDiffUtils(1, "Pluto", "Pluto Description", TYPE_PLUTO),
        DataRecyclerViewWithDiffUtils(2, "Jupiter", "Jupiter Description", TYPE_JUPITER),
        DataRecyclerViewWithDiffUtils(3, "Pluto", "Pluto Description", TYPE_PLUTO),
        DataRecyclerViewWithDiffUtils(4, "Pluto", "Pluto Description", TYPE_PLUTO),
        DataRecyclerViewWithDiffUtils(5, "Jupiter", "Jupiter Description", TYPE_JUPITER),
        DataRecyclerViewWithDiffUtils(6, "Jupiter", "Jupiter Description", TYPE_JUPITER),
        DataRecyclerViewWithDiffUtils(7, "Pluto", "Pluto Description", TYPE_PLUTO),
        DataRecyclerViewWithDiffUtils(8, "Jupiter", "Jupiter Description", TYPE_JUPITER)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu_diff, menu);
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_item -> {
                alert()
            }
            R.id.action_update_item -> {
                changeAdapterData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerViewWithDiffUtilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        adapter = RecyclerViewWithDiffUtilsAdapter(list, this)
        adapter.setList(list)
        binding.recyclerviewWithDiffUtils.adapter = adapter

        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerviewWithDiffUtils)
    }

    override fun onItemClickListener(data: DataRecyclerView) {
        TODO("Not yet implemented")
    }

    override fun onAddBtnClick(position: Int, type: Int) {
        when (type) {
            1 -> {
                list.add(
                    position,
                    DataRecyclerViewWithDiffUtils(list.lastIndex+1,"Jupiter", "Jupiter Description", TYPE_JUPITER)
                )
                adapter.setAddToList(list, position, type)
            }
            2 -> {
                list.add(
                    position,
                    DataRecyclerViewWithDiffUtils(list.lastIndex+1,"Pluto", "Pluto Description", TYPE_PLUTO)
                )
                adapter.setAddToList(list, position, type)
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
            RecyclerViewWithDiffUtilFragment().apply {
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
            .setPositiveButton("Jupiter") { dialog: DialogInterface?, which: Int ->
                onAddBtnClick(list.size, ru.kirill.recycler_view.simplerecyclerview.TYPE_MARS)
            }
            .setNegativeButton("Pluto") { dialog: DialogInterface?, which: Int ->
                onAddBtnClick(list.size, ru.kirill.recycler_view.simplerecyclerview.TYPE_EARTH)
            }
            .setNeutralButton("Cancel") { dialog: DialogInterface?, which: Int ->
                dialog?.cancel()
            }
            .show()
    }
}