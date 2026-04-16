package com.meepoffaith.hextrapats.casting.actions.handlers

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.math.HexPattern
import com.meepoffaith.hextrapats.util.HextraUtils.numericalReflection
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d

class VectorZ(val z: Double) : SpecialHandler{
    override fun act(): Action{
        return InnerAction(z)
    }

    override fun getName(): Text{
        /*
        val key = IXplatAbstractions.INSTANCE.specialHandlerRegistry.getKey(SpecialHandlers.VEC_1).get()
        val num = Action.DOUBLE_FORMATTER.format(x)
        return HexAPI.instance().getSpecialHandlerI18nKey(key).asTranslatedComponent(num).lightPurple
         */
        return Text.of("TODO")
    }

    class InnerAction(val z: Double) : ConstMediaAction{
        override val argc = 0
        override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
            return listOf(Vec3Iota(Vec3d(0.0, 0.0, z)))
        }
    }

    class Factory : SpecialHandler.Factory<VectorZ>{
        override fun tryMatch(pattern: HexPattern, env: CastingEnvironment): VectorZ? {
            val sig = pattern.anglesSignature()
            if(sig.startsWith("deaqaa") || sig.startsWith("dqdedd")){
                val num = numericalReflection(sig.substring(6)) *
                        (if (sig.startsWith("dqdedd")) -1.0 else 1.0)
                return VectorZ(num)
            } else {
                return null
            }
        }
    }
}
