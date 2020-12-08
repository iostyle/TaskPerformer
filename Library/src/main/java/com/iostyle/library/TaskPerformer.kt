package com.iostyle.library

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

object TaskPerformer {

    private var taskPerformerList: CopyOnWriteArrayList<TaskResumeCallback>? = null
    private var taskPerformerMap: ConcurrentHashMap<String, TaskResumeCallback>? = null

    interface TaskResumeCallback {
        fun onResume(): Boolean
    }

    fun addPerform(callback: TaskResumeCallback) {
        taskPerformerList ?: run {
            taskPerformerList = CopyOnWriteArrayList()
        }
        taskPerformerList?.add(callback)
    }

    fun putPerform(key: String, callback: TaskResumeCallback) {
        taskPerformerMap ?: run {
            taskPerformerMap = ConcurrentHashMap()
        }
        taskPerformerMap?.put(key, callback)
    }

    fun perform() {
        taskPerformerList?.forEach {
            if (it.onResume()) taskPerformerList?.remove(it)
        }
        taskPerformerMap?.forEach {
            if (it.value.onResume()) taskPerformerMap?.remove(it.key)
        }
    }

    fun clear() {
        taskPerformerList?.clear()
        taskPerformerList = null
        taskPerformerMap?.clear()
        taskPerformerMap = null
    }
}