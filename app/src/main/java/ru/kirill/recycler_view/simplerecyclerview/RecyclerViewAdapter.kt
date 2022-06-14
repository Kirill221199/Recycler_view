package ru.kirill.recycler_view.simplerecyclerview

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kirill.recycler_view.databinding.RecyclerItemFirstBinding
import ru.kirill.recycler_view.databinding.RecyclerItemHeaderBinding
import ru.kirill.recycler_view.databinding.RecyclerItemSecondBinding


const val TYPE_MARS = 1
const val TYPE_EARTH = 2
const val TYPE_HEADER = 3

class RecyclerViewAdapter(
    private var list: List<Data>,
    private var onListItemClickListener: OnListItemClickListener,
    context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var marsIsOpen: Boolean = false
    private var earthIsOpen: Boolean = false

    fun setList(newList: List<Data>) {
        this.list = newList
    }

    fun setAddToList(newList: List<Data>, position: Int, type: Int) {
        this.list = newList
        notifyItemChanged(position)
    }

    fun setRemoveToList(newList: List<Data>, position: Int) {
        this.list = newList
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
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
        fun myBind(data: Data) {
            (RecyclerItemFirstBinding.bind(itemView)).apply {
                titleFirst.text = data.title
                descriptionFirst.text = data.description
                container.setOnLongClickListener {
                    alert(context, layoutPosition, TYPE_MARS)
                    true
                }
                container.setOnClickListener {
                    if(!marsIsOpen){
                        textMars.visibility = View.VISIBLE
                        endFirst.visibility = View.VISIBLE
                    }else{
                        textMars.visibility = View.GONE
                        endFirst.visibility = View.GONE
                    }
                    marsIsOpen = !marsIsOpen
                }
            }
        }
    }

    inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context = view.context
        fun myBind(data: Data) {
            (RecyclerItemSecondBinding.bind(itemView)).apply {
                titleSecond.text = data.title
                descriptionSecond.text = data.description
                container.setOnLongClickListener {
                    alert(context, layoutPosition, TYPE_EARTH)
                    true
                }
                container.setOnClickListener {
                    if (!earthIsOpen){
                        textEarth.visibility = View.VISIBLE
                        endSecond.visibility = View.VISIBLE
                    }else{
                        textEarth.visibility = View.GONE
                        endSecond.visibility = View.GONE
                    }
                    earthIsOpen = !earthIsOpen
                }
            }
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun myBind(data: Data) {
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
            .show()
    }

}

