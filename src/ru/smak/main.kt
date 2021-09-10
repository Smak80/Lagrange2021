package ru.smak

import ru.smak.math.polynom.Polynom

fun main() {
    val c = mutableListOf(-2.0, 1.0, 0.03, 1e-90, 0.0, 2.0)
    val a = arrayOf(0.0, 4.0, 5.0, 0.0, 0.0, 1.0, 0.0)
    val p1 = Polynom(a)
    val p2 = Polynom(c)
    println(p1)
    println(p1.degree)
    println(p2)
    println(p2.degree)
    val p3 = p1 + p2
    p3 += p1
    val p4 = p1 - p2
    var k = 2.0/3.0
    println(Math.rint(k*1e8)/1e8)
}