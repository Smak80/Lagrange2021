package ru.smak.math.polynom

import java.lang.StringBuilder
import kotlin.math.abs
import ru.smak.math.*
import kotlin.math.max

class Polynom(coeff: Collection<Double>) {

    /**
     * Набор коэффициентов, доступных внутри класса
     */
    private val _coeff: MutableList<Double>

    /**
     * Неизменяемый список коэффициентов для получения извне
     */
    val coeff: List<Double>
        get() = _coeff.toList()

    /**
     * Степень полинома
     */
    val degree: Int
        get() = coeff.size - 1

    init{
        _coeff = coeff.toMutableList()
        removeZeros()
    }

    constructor(coeff: Array<Double>) : this(coeff.toList())
    constructor(coeff: DoubleArray): this(coeff.toList())

    /**
     * Создание полинома нулевой степени с коэффициентом = 0
     */
    constructor() : this(mutableListOf(0.0))

    private fun removeZeros(){
        var found = false
        val nc = coeff.reversed().filter{
            if (found || it neq 0.0) {found = true; true} else false
        }
        _coeff.clear()
        _coeff.addAll(nc.reversed())
        if (_coeff.size == 0) _coeff.add(0.0)
    }

    operator fun plus(other: Polynom) =
        Polynom(DoubleArray(max(degree, other.degree)+1){
            (if (it <= degree) _coeff[it] else 0.0) +
            if (it <= other.degree) other._coeff[it] else 0.0
        })

    operator fun plus(value: Double)  =
        Polynom(
            DoubleArray(degree+1){ _coeff[it] + if (it == 0) value else 0.0}
        )

    operator fun plusAssign(other: Polynom){
        _coeff.indices.forEach {
            _coeff[it] += if (it <= other.degree) other._coeff[it] else 0.0
        }
        for (i in degree+1..other.degree){
            _coeff.add(other._coeff[i])
        }
        removeZeros()
    }

    operator fun plusAssign(value: Double){
        _coeff[0] += value
    }

    operator fun minus(other: Polynom) = this + other * -1.0

    operator fun minus(value: Double) = this + value * -1.0

    operator fun minusAssign(other: Polynom){
        this.plusAssign(other)
    }

    operator fun minusAssign(value: Double){
        this.plusAssign(-value)
    }

    operator fun times(value: Double) =
        Polynom(DoubleArray(degree + 1){_coeff[it] * value})

    operator fun timesAssign(value: Double){
        _coeff.indices.forEach { _coeff[it] *= value }
        removeZeros()
    }

    operator fun div(value: Double){
        _coeff.indices.forEach { _coeff[it] /= value }
    }

    override fun toString() =
        coeff.indices.reversed().joinToString(""){ ind ->
            val monStr = StringBuilder()
            val acoeff = abs(coeff[ind])
            if (coeff[ind] neq 0.0){
                if (ind < coeff.size - 1 && coeff[ind] > 0){
                    monStr.append("+")
                } else if (coeff[ind] < 0) monStr.append("-")
                if (acoeff neq 1.0)
                    if (abs(acoeff - acoeff.toInt().toDouble()) eq 0.0)
                        monStr.append(acoeff.toInt())
                    else monStr.append(acoeff)
                if (ind > 0) {
                    monStr.append("x")
                    if (ind > 1) monStr.append("^$ind")
                }
            } else {
                if (coeff.size == 1) monStr.append("0")
            }
            monStr
        }
}