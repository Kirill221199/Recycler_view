package ru.kirill.recycler_view.recyclerviewWithDiffutils.DiffUtils

import androidx.recyclerview.widget.DiffUtil
import com.google.firebase.database.core.view.Change
import ru.kirill.recycler_view.recyclerviewWithDiffutils.DataRecyclerViewWithDiffUtils


class DiffUtilCallback(
    private val oldList: List<DataRecyclerViewWithDiffUtils>,
    private val newList: List<DataRecyclerViewWithDiffUtils>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].title == newList[newItemPosition].title)
                && ((oldList[oldItemPosition].description == newList[newItemPosition].description))
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return Change(oldItem,newItem)
    }
}