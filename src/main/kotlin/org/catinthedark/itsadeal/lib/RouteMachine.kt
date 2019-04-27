package org.catinthedark.itsadeal.lib

import org.slf4j.LoggerFactory

class RouteMachine {
    private val log = LoggerFactory.getLogger(RouteMachine::class.java)
    private val routes: MutableMap<YieldUnit<*, *>, (Any) -> YieldUnit<Any, *>> = hashMapOf()
    private lateinit var current: YieldUnit<*, *>

    fun <T> start(unit: YieldUnit<T, Any>, data: T) {
        current = unit
        unit.onActivate(data)
    }

    /**
     * Stops current unit
     */
    fun exit() {
        current.onExit()
    }

    fun run(delta: Float) {
        val data = current.run(delta)
        if (data != null) {
            doRoute(data)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> addRoute(from: YieldUnit<*, T>, routeFn: (T) -> YieldUnit<T, *>) {
        routes.put(from as YieldUnit<*, *>, routeFn as (Any) -> YieldUnit<Any, *>)
    }

    private fun doRoute(data: Any) {
        val from = current
        log.info("Begin transition from $from")
        from.onExit()
        val routeFn = routes[from] ?: throw Exception("Could not find route function from $from")
        val to = routeFn(data)
        to.onActivate(data)
        log.info("End transition to $to")
        current = to
    }
}
