package ru.kirill.recycler_view.simplerecyclerview

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

class RecyclerViewAdapter(private var list: List<Data>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

}

class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun myBind(data: Data) {
        (RecyclerItemFirstBinding.bind(itemView)).apply {
            titleFirst.text = data.title
            descriptionFirst.text = data.description
        }
    }
}

class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun myBind(data: Data) {
        (RecyclerItemSecondBinding.bind(itemView)).apply {
            titleSecond.text = data.title
            descriptionSecond.text = data.description
        }
    }
}

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun myBind(data: Data) {
        (RecyclerItemHeaderBinding.bind(itemView)).apply {
            header.text = data.title
        }
    }
}
