package ru.kirill.recycler_view.simplerecyclerview

interface OnListItemClickListener {

    fun onItemClickListener(data: Data)
    fun onAddBtnClick(position: Int, type: Int)
    fun onRemoveBtnClick(position: Int)

}