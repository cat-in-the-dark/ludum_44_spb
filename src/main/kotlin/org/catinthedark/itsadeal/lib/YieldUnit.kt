package org.catinthedark.itsadeal.lib

interface YieldUnit<in U, out T> {
    fun onActivate(data: U)
    fun run(delta: Float): T?
    fun onExit()
}
