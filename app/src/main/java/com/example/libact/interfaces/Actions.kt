package com.example.libact.interfaces

/**
 * A fragment representing a list of Items.
 */
interface Actions<T>{
    fun onLongClick(item: T):Boolean{
        return true
    }
    fun onClick(item: T)
}