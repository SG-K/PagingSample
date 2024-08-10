package com.example.pagingsample.paging

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}