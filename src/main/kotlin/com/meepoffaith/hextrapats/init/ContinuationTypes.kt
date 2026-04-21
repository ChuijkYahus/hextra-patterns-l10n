package com.meepoffaith.hextrapats.init

import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hextrapats.HextraPats
import com.meepoffaith.hextrapats.casting.eval.vm.FrameIndexForEach
import net.minecraft.registry.Registry

object ContinuationTypes {
    val FOREACH_INDEX = registerContinuation("foreach_index", FrameIndexForEach.TYPE)

    fun init(){}

    private fun registerContinuation(name: String, continuation: ContinuationFrame.Type<*>) :  ContinuationFrame.Type<*>{
        return Registry.register(IXplatAbstractions.INSTANCE.continuationTypeRegistry, HextraPats.modLoc(name), continuation)
    }
}
