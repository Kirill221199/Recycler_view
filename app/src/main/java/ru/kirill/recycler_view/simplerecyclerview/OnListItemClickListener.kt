package ru.kirill.recycler_view.simplerecyclerview

interface OnListItemClickListener {

    fun onItemClickListener(data: DataRecyclerView)
    fun onAddBtnClick(position: Int, type: Int)
    fun onRemoveBtnClick(position: Int)

}