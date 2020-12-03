package com.triad.school.letscook.fragments

fun interface CreateItemListener<T> {
    fun onItemCreated(item: T)
}