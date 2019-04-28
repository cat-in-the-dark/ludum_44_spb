package org.catinthedark.itsadeal.lib

class Deffer {
    private val funcs: MutableMap<Long, Func> = mutableMapOf()
    private var index: Long = 0L

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
                funcs.remove(it.key)
            }
        }
    }

    data class Func(
        val func: () -> Unit,
        var time: Float
    )
}
