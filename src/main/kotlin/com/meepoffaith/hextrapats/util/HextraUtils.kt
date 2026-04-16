package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.HexAPI
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import at.petrak.hexcasting.xplat.IXplatAbstractions
import net.minecraft.registry.RegistryKey
import net.minecraft.text.Text


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

    fun specialHandlerLang(handler: SpecialHandler.Factory<*>, vararg args: Any): Text {
        val key = IXplatAbstractions.INSTANCE.specialHandlerRegistry.getKey(handler).get()
        return HexAPI.instance().getSpecialHandlerI18nKey(key).asTranslatedComponent(args).lightPurple
    }
}
