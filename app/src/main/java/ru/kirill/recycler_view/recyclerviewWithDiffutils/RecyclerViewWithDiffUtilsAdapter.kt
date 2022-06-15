package ru.kirill.recycler_view.recyclerviewWithDiffutils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kirill.recycler_view.recyclerviewWithDiffutils.DiffUtils.DiffUtilCallback
import ru.kirill.recycler_view.R
import ru.kirill.recycler_view.databinding.RecyclerItemFourthBinding
import ru.kirill.recycler_view.databinding.RecyclerItemHeaderBinding
import ru.kirill.recycler_view.databinding.RecyclerItemThirdBinding
import ru.kirill.recycler_view.recyclerviewWithDiffutils.DiffUtils.Change
import ru.kirill.recycler_view.recyclerviewWithDiffutils.DiffUtils.createCombinedPayload
import ru.kirill.recycler_view.recyclerviewWithDiffutils.ItemTouchHelper.ItemTouchHelperAdapter
import ru.kirill.recycler_view.recyclerviewWithDiffutils.ItemTouchHelper.ItemTouchHelperViewHolder


const val TYPE_JUPITER = 1
const val TYPE_PLUTO = 2
const val TYPE_HEADER = 3

class RecyclerViewWithDiffUtilsAdapter(
    private var list: MutableList<DataRecyclerViewWithDiffUtils>,
    private var onListItemClickListener: RecyclerViewWithDiffUtilFragment
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    private var marsIsOpen: Boolean = false
    private var earthIsOpen: Boolean = false

    fun setList(newList: List<DataRecyclerViewWithDiffUtils>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallback(list,newList))
        result.dispatchUpdatesTo(this)
        this.list = newList.toMutableList()
    }

    fun setAddToList(newList: List<DataRecyclerViewWithDiffUtils>, position: Int, type: Int) {
        this.list = newList.toMutableList()
        notifyItemChanged(position)
    }

    fun setRemoveToList(newList: List<DataRecyclerViewWithDiffUtils>, position: Int) {
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

            TYPE_JUPITER -> {
                val view =
                    RecyclerItemThirdBinding.inflate(LayoutInflater.from(parent.context))
                JupiterViewHolder(view.root)
            }
            TYPE_PLUTO -> {
                val view =
                    RecyclerItemFourthBinding.inflate(LayoutInflater.from(parent.context))
                PlutoViewHolder(view.root)
            }
            TYPE_HEADER -> {
                val view =
                    RecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(view.root)
            }
            else -> {
                val view =
                    RecyclerItemThirdBinding.inflate(LayoutInflater.from(parent.context))
                JupiterViewHolder(view.root)

            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        }else{
            when (getItemViewType(position)){

                TYPE_JUPITER -> {
                    val res = createCombinedPayload(payloads as List<Change<DataRecyclerViewWithDiffUtils>>)
                    if(res.oldData.description != res.newData.description)
                        (holder as JupiterViewHolder).itemView.findViewById<TextView>(R.id.description_third).text =res.newData.description
                }

                TYPE_PLUTO -> {
                    val res = createCombinedPayload(payloads as List<Change<DataRecyclerViewWithDiffUtils>>)
                    if(res.oldData.title != res.newData.title)
                        (holder as PlutoViewHolder).itemView.findViewById<TextView>(R.id.title_fourth).text =res.newData.title
                }

                TYPE_HEADER -> {
                    (holder as HeaderViewHolder).myBind(list[position])
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_JUPITER -> {
                (holder as JupiterViewHolder).myBind(list[position])
            }
            TYPE_PLUTO -> {
                (holder as PlutoViewHolder).myBind(list[position])
            }
            TYPE_HEADER -> {
                (holder as HeaderViewHolder).myBind(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class JupiterViewHolder(view: View) : RecyclerView.ViewHolder(view),
        ItemTouchHelperViewHolder {
        fun myBind(data: DataRecyclerViewWithDiffUtils) {
            (RecyclerItemThirdBinding.bind(itemView)).apply {
                titleThird.text = data.title
                descriptionThird.text = data.description
                container.setOnClickListener {
                    if(!marsIsOpen){
                        textJupiter.visibility = View.VISIBLE
                        endThird.visibility = View.VISIBLE
                    }else{
                        textJupiter.visibility = View.GONE
                        endThird.visibility = View.GONE
                    }
                    marsIsOpen = !marsIsOpen
                }
            }
        }

        @SuppressLint("ResourceAsColor")
        override fun onItemSelected() {
            itemView.setBackgroundColor(R.color.black)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class PlutoViewHolder(view: View) : RecyclerView.ViewHolder(view),
        ItemTouchHelperViewHolder {
        fun myBind(data: DataRecyclerViewWithDiffUtils) {
            (RecyclerItemFourthBinding.bind(itemView)).apply {
                titleFourth.text = data.title
                descriptionFourth.text = data.description
                container.setOnClickListener {
                    if (!earthIsOpen){
                        textPluto.visibility = View.VISIBLE
                        endFourth.visibility = View.VISIBLE
                    }else{
                        textPluto.visibility = View.GONE
                        endFourth.visibility = View.GONE
                    }
                    earthIsOpen = !earthIsOpen
                }
            }
        }

        @SuppressLint("ResourceAsColor")
        override fun onItemSelected() {
            itemView.setBackgroundColor(R.color.black)
        }

            override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun myBind(data: DataRecyclerViewWithDiffUtils) {
            (RecyclerItemHeaderBinding.bind(itemView)).apply {
                header.text = data.title
            }
        }
    }


}

