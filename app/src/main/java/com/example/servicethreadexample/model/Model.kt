package com.example.servicethreadexample.model

import com.example.servicethreadexample.service.Task

data class TaskResult<T>(
    val task: Task<T>,
    val result: T
)