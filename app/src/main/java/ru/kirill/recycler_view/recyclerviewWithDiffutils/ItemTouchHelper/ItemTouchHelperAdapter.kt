package ru.kirill.recycler_view.recyclerviewWithDiffutils.ItemTouchHelper

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)

}