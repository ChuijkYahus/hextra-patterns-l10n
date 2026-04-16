package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.casting.iota.DoubleIota



object HextraUtils {
    fun greaterEq(a: Double, b: Double): Boolean {
        return a >= b || DoubleIota.tolerates(a, b)
    }

    fun lessEq(a: Double, b: Double): Boolean {
        return a <= b || DoubleIota.tolerates(a, b)
    }
}
