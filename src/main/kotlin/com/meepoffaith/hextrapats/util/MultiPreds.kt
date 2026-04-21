package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.iota.IotaType

object MultiPreds {
    fun all(type: IotaType<*>): IotaMultiPredicate{
        return IotaMultiPredicate.all(IotaPredicate.ofType(type))
    }

    fun pair(first: IotaType<*>, second: IotaType<*>): IotaMultiPredicate{
        return IotaMultiPredicate.pair(IotaPredicate.ofType(first), IotaPredicate.ofType(second))
    }

    fun triple(first: IotaType<*>, second: IotaType<*>, third: IotaType<*>): IotaMultiPredicate{
        return IotaMultiPredicate.triple(IotaPredicate.ofType(first), IotaPredicate.ofType(second), IotaPredicate.ofType(third))
    }

    fun any(needs: IotaType<*>, fallback: IotaType<*>): IotaMultiPredicate{
        return IotaMultiPredicate.any(IotaPredicate.ofType(needs), IotaPredicate.ofType(fallback))
    }
}
