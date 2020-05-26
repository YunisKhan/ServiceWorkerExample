package com.example.servicethreadexample.service

interface Task<T> {
    fun onExecuteTask(): T
    fun onTaskComplete(result: T)
}