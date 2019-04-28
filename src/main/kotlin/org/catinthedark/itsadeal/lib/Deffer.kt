package org.catinthedark.itsadeal.lib

import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

class Deffer {
    private val funcs: ConcurrentHashMap<Long, Func> = ConcurrentHashMap()
    private var index: Long = 0L

    private val log = LoggerFactory.getLogger(Deffer::class.java)

    fun register(time: Float, func: () -> Unit): Long {
        funcs[index++] = Func(func, time)
        return index
    }

    fun unregister(index: Long) {
        funcs.remove(index)
    }

    fun update(time: Float) {
        funcs.forEach {
            it.value.time -= time

            if (it.value.time <= 0) {
                it.value.func()
                log.info("remove func from deffer at ${Thread.currentThread().name}")
                funcs.remove(it.key)
            }
        }
    }

    data class Func(
        val func: () -> Unit,
        var time: Float
    )
}
