package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.HexAPI
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota
import net.minecraft.entity.Entity


object HextraUtils {
    /** Simulates the accumulation process of Numerical Reflection  */
    fun numericalReflection(s: String): Double{
        var accumulator = 0.0
        for (ch in s.toCharArray()) {
            when (ch) {
                'w' -> accumulator++
                'q' -> accumulator += 5.0
                'e' -> accumulator += 10.0
                'a' -> accumulator *= 2.0
                'd' -> accumulator /= 2.0
                's' -> {}
                else -> throw IllegalStateException()
            }
        }
        return accumulator
    }

    fun greaterEq(a: Double, b: Double): Boolean {
        return a >= b || DoubleIota.tolerates(a, b)
    }

    fun lessEq(a: Double, b: Double): Boolean {
        return a <= b || DoubleIota.tolerates(a, b)
    }

    fun specialHandlerLang(handler: SpecialHandler.Factory<*>): String {
        val key = IXplatAbstractions.INSTANCE.specialHandlerRegistry.getKey(handler).get()
        return HexAPI.instance().getSpecialHandlerI18nKey(key)
    }

    fun Set<Entity>.asActionResult(): List<Iota> = listOf(EntitySetIota(this))
}
