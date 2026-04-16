package com.meepoffaith.hextrapats.init

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.common.lib.hex.HexArithmetics
import com.meepoffaith.hextrapats.HextraPats
import com.meepoffaith.hextrapats.casting.arithmetic.BoolArithmetic
import net.minecraft.registry.Registry

object Arithmetics {
    fun init(){
        registerArithmetic("bool", BoolArithmetic())
    }

    private fun registerArithmetic(name: String, a: Arithmetic){
        Registry.register(HexArithmetics.REGISTRY, HextraPats.modLoc(name), a)
    }
}
