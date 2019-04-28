package org.catinthedark.itsadeal.game

object IOC {
    private val container: MutableMap<String, Any?> = hashMapOf()

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
        return obj as T
    } else {
        throw ClassCastException("Object for $name is not a ${T::class.java.name}")
    }
}
