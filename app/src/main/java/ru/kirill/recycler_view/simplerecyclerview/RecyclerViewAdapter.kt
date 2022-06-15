package ru.kirill.recycler_view.simplerecyclerview

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.kirill.recycler_view.databinding.RecyclerItemFirstBinding
import ru.kirill.recycler_view.databinding.RecyclerItemHeaderBinding
import ru.kirill.recycler_view.databinding.RecyclerItemSecondBinding
import ru.kirill.recycler_view.recyclerviewWithDiffutils.ItemTouchHelperAdapter


const val TYPE_MARS = 1
const val TYPE_EARTH = 2
const val TYPE_HEADER = 3

class RecyclerViewAdapter(
    private var list: MutableList<DataRecyclerView>,
    private var onListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    private var marsIsOpen: Boolean = false
    private var earthIsOpen: Boolean = false

    fun setList(newList: List<DataRecyclerView>) {
        this.list = newList.toMutableList()
    }

    fun setAddToList(newList: List<DataRecyclerView>, position: Int, type: Int) {
        this.list = newList.toMutableList()
        notifyItemChanged(position)
    }

    fun setRemoveToList(newList: List<DataRecyclerView>, position: Int) {
        this.list = newList.toMutableList()
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        list.removeAt(fromPosition).apply{
            list.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }


    override fun onItemDismiss(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            TYPE_MARS -> {
                val view =
                    RecyclerItemFirstBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(view.root)
            }
            TYPE_EARTH -> {
                val view =
                    RecyclerItemSecondBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(view.root)
            }
            TYPE_HEADER -> {
                val view =
                    RecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(view.root)
            }
            else -> {
                val view =
                    RecyclerItemFirstBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(view.root)

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EARTH -> {
                (holder as EarthViewHolder).myBind(list[position])
            }
            TYPE_MARS -> {
                (holder as MarsViewHolder).myBind(list[position])
            }
            TYPE_HEADER -> {
                (holder as HeaderViewHolder).myBind(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context = view.context
        fun myBind(data: DataRecyclerView) {
            (RecyclerItemFirstBinding.bind(itemView)).apply {
                titleFirst.text = data.title
                descriptionFirst.text = data.description
                container.setOnLongClickListener {
                    alert(context, layoutPosition, TYPE_MARS)
                    true
                }
                container.setOnClickListener {
                    if (!marsIsOpen) {
                        textMars.visibility = View.VISIBLE
                        endFirst.visibility = View.VISIBLE
                    } else {
                        textMars.visibility = View.GONE
                        endFirst.visibility = View.GONE
                    }
                    marsIsOpen = !marsIsOpen
                }

                buttonUpFirst.setOnClickListener {
                    if (layoutPosition > 1) { // Сan 't be higher than header
                        list.removeAt(layoutPosition).apply {
                            list.add(layoutPosition - 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition - 1)
                    } else {
                        Toast.makeText(context, "start of the list", Toast.LENGTH_SHORT).show()
                    }

                }

                buttonDownFirst.setOnClickListener {
                    if (layoutPosition < list.lastIndex) {
                        list.removeAt(layoutPosition).apply {
                            list.add(layoutPosition + 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition + 1)
                    } else {
                        Toast.makeText(context, "end of the list", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context = view.context
        fun myBind(data: DataRecyclerView) {
            (RecyclerItemSecondBinding.bind(itemView)).apply {
                titleSecond.text = data.title
                descriptionSecond.text = data.description
                container.setOnLongClickListener {
                    alert(context, layoutPosition, TYPE_EARTH)
                    true
                }
                container.setOnClickListener {
                    if (!earthIsOpen) {
                        textEarth.visibility = View.VISIBLE
                        endSecond.visibility = View.VISIBLE
                    } else {
                        textEarth.visibility = View.GONE
                        endSecond.visibility = View.GONE
                    }
                    earthIsOpen = !earthIsOpen
                }

                buttonUpSecond.setOnClickListener {
                    if (layoutPosition > 1) { // Сan 't be higher than header
                        list.removeAt(layoutPosition).apply {
                            list.add(layoutPosition - 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition - 1)
                    } else {
                        Toast.makeText(context, "start of the list", Toast.LENGTH_SHORT).show()
                    }

                }

                buttonDownSecond.setOnClickListener {
                    if (layoutPosition < list.lastIndex) {
                        list.removeAt(layoutPosition).apply {
                            list.add(layoutPosition + 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition + 1)
                    } else {
                        Toast.makeText(context, "end of the list", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun myBind(data: DataRecyclerView) {
            (RecyclerItemHeaderBinding.bind(itemView)).apply {
                header.text = data.title
            }
        }
    }


    private fun alert(context: Context, layoutPosition: Int, type: Int) {
        AlertDialog.Builder(context)
            .setMessage("What do you want to do?")
            .setPositiveButton("Add") { dialog: DialogInterface?, which: Int ->
                onListItemClickListener.onAddBtnClick(layoutPosition, type)
            }
            .setNegativeButton("Remove") { dialog: DialogInterface?, which: Int ->
                onListItemClickListener.onRemoveBtnClick(layoutPosition)
            }
            .setNeutralButton("Cancel"){dialog: DialogInterface?, which: Int ->
                dialog?.cancel()
            }
            .show()
    }

}

