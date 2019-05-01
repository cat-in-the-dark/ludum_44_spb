package org.catinthedark.itsadeal.game

class Texts {
    val bankrot = "Вам конец!"
    val cantPay = "Вы не можете заплатить за крышу"
    val failTitle = "Вас подставили!"
    val bail = "Вы откупились за %d у.е."
    val cantBail = "Вы не можете откупиться."
    val seeYou = "Увидимся через 15 лет."
    val money = { money: Int -> "Баланс: $money у.е." }
    val win = "Успешный успех!"
    val winDescription = "Вы накопили на самолет\n и улетели на острова"
    val profit = "ПРОФИТ: %d у.е."
    val goal = { achName: String, achCost: Int -> "Цель: $achName за $achCost у.е" }
    val payment = { credit: Int -> "Плата за крышу: $credit у.е." }
    val rejected = "Отказано"
}
