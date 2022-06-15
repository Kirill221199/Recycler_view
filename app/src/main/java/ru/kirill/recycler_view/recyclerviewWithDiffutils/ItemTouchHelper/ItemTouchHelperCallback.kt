package ru.kirill.recycler_view.recyclerviewWithDiffutils.ItemTouchHelper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.kirill.recycler_view.recyclerviewWithDiffutils.RecyclerViewWithDiffUtilsAdapter

class ItemTouchHelperCallback(private val adapter: RecyclerViewWithDiffUtilsAdapter):
    ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        val dragsFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragsFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target. adapterPosition)
        return true

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder is RecyclerViewWithDiffUtilsAdapter.JupiterViewHolder) {
            viewHolder.onItemSelected()
        }else if(viewHolder is RecyclerViewWithDiffUtilsAdapter.PlutoViewHolder){
            viewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is RecyclerViewWithDiffUtilsAdapter.JupiterViewHolder) {
            viewHolder.onItemClear()
        }else if(viewHolder is RecyclerViewWithDiffUtilsAdapter.PlutoViewHolder){
            viewHolder.onItemClear()
        }
        super.clearView(recyclerView, viewHolder)
    }
}