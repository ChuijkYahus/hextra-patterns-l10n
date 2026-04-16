package com.meepoffaith.hextrapats.casting.actions.vecmath

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import net.minecraft.util.math.Vec3d

class OpRandVec : ConstMediaAction {
    override val argc = 0
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val rand = env.world.random
        return listOf(Vec3Iota(Vec3d(rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian()).normalize()))
    }
}
