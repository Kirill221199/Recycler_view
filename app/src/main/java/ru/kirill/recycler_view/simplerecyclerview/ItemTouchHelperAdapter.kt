package ru.kirill.recycler_view.simplerecyclerview

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)

}