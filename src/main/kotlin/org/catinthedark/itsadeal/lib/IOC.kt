package org.catinthedark.itsadeal.lib

import java.util.concurrent.ConcurrentHashMap

object IOC {
    private val container: ConcurrentHashMap<String, Any?> = ConcurrentHashMap()

    fun put(name: String, obj: Any) {
        container[name] = obj
    }

    fun get(name: String): Any? {
        return container[name]
    }
}

inline fun <reified T> IOC.at(name: String): T? {
    val obj = get(name)
    if (obj is T?) {
        return obj
    } else {
        throw ClassCastException("Object for $name is not a ${T::class.java.name}")
    }
}

inline fun <reified T> IOC.atOrFail(name: String): T {
    val obj = get(name) ?: throw NullPointerException("$name is null")
    if (obj is T) {
        return obj
    } else {
        throw ClassCastException("Object for $name is not a ${T::class.java.name}")
    }
}


inline fun <reified T> IOC.atOr(name: String, default: T): T {
    val obj = get(name)
    if (obj is T?) {
        return obj ?: default
    } else {
        throw ClassCastException("Object for $name is not a ${T::class.java.name}")
    }
}
