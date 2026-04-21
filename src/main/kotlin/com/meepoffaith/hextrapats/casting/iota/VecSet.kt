package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.util.MathUtils
import net.minecraft.util.math.Vec3d

class VecSet : HashSet<Vec3d> {
    constructor(): super()
    constructor(set: VecSet) : super(set)

    override fun add(e: Vec3d): Boolean {
        return super.add(MathUtils.roundToTolerance(e))
    }

    override fun contains(o: Vec3d): Boolean {
        return super.contains(MathUtils.roundToTolerance(o))
    }

    override fun remove(o: Vec3d): Boolean {
        return super.remove(MathUtils.roundToTolerance(o))
    }

    fun asActionResult(): List<Iota> = listOf(VecSetIota(this))
}
