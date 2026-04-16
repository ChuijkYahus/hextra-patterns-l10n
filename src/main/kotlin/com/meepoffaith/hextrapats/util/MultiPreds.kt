package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType

object MultiPreds {
    fun <T: Iota> all(type: IotaType<T>): IotaMultiPredicate{
        return IotaMultiPredicate.all(IotaPredicate.ofType(type))
    }
}
