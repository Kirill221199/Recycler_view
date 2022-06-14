package ru.kirill.recycler_view.recyclerviewWithDiffutils

interface OnListItemClickListener {

    fun onItemClickListener(data: DataRecyclerViewWithDiffUtils)
    fun onAddBtnClick(position: Int, type: Int)
    fun onRemoveBtnClick(position: Int)

}