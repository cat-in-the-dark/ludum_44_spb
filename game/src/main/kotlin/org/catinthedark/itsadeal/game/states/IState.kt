package org.catinthedark.itsadeal.game.states

interface IState {
    fun onActivate()
    fun onUpdate()
    fun onExit()
}
