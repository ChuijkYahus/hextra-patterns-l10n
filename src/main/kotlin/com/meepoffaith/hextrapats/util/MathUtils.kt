package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.casting.iota.DoubleIota
import net.minecraft.util.math.Vec3d
import kotlin.math.acos
import kotlin.math.min
import kotlin.math.roundToInt


object MathUtils {
    const val TAU = Math.PI * 2.0

    /** Modulo that works properly for negative numbers. Taken from Anuken/Arc.  */
    fun mod(a: Double, b: Double): Double{
        return ((a % b) + b) % b
    }

    fun angleDist(a: Double, b: Double): Double{
        var a = a
        var b = b
        a = mod(a, TAU)
        b = mod(b, TAU)

        val distBack = if ((a - b) < 0) a - b + TAU else a - b
        val distFwd = if ((b - a) < 0) b - a + TAU else b - a

        return min(distBack, distFwd)
    }

    fun vecAngleDist(a: Vec3d, b: Vec3d): Double{
        val dot = a.dotProduct(b)
        val len2 = a.length() * b.length()

        return acos(dot / len2)
    }

    fun roundToTolerance(num: Double): Double{
        return (num / DoubleIota.TOLERANCE).roundToInt() * DoubleIota.TOLERANCE
    }

    fun roundToTolerance(v: Vec3d): Vec3d{
        return Vec3d(
            roundToTolerance(v.getX()),
            roundToTolerance(v.getY()),
            roundToTolerance(v.getZ())
        )
    }
}
