package com.meepoffaith.hextrapats.init

import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hextrapats.HextraPats
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota
import com.meepoffaith.hextrapats.casting.iota.VecSetIota
import net.minecraft.registry.Registry

object IotaTypes {
    val NUM_SET = registerIota("num_set", DoubleSetIota.TYPE)
    val VEC_SET = registerIota("vec_set", VecSetIota.TYPE)
    val ENTITY_SET = registerIota("entity_set", EntitySetIota.TYPE)

    fun init(){}

    private fun registerIota(name: String, type: IotaType<*>) :  IotaType<*>{
        return Registry.register(IXplatAbstractions.INSTANCE.iotaTypeRegistry, HextraPats.modLoc(name), type)
    }
}
