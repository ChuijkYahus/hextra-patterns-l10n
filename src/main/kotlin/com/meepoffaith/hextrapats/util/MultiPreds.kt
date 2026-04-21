package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType


object MultiPreds {
    fun all(type: IotaType<*>): IotaMultiPredicate =
        IotaMultiPredicate.all(IotaPredicate.ofType(type))

    fun pair(first: IotaType<*>, second: IotaType<*>): IotaMultiPredicate =
        IotaMultiPredicate.pair(IotaPredicate.ofType(first), IotaPredicate.ofType(second))

    fun triple(first: IotaType<*>, second: IotaType<*>, third: IotaType<*>): IotaMultiPredicate =
        IotaMultiPredicate.triple(IotaPredicate.ofType(first), IotaPredicate.ofType(second), IotaPredicate.ofType(third))

    fun quad(first: IotaType<*>, second: IotaType<*>, third: IotaType<*>, fourth: IotaType<*>): IotaMultiPredicate =
        Quad(IotaPredicate.ofType(first), IotaPredicate.ofType(second), IotaPredicate.ofType(third), IotaPredicate.ofType(fourth))

    fun any(needs: IotaType<*>, fallback: IotaType<*>): IotaMultiPredicate =
        IotaMultiPredicate.any(IotaPredicate.ofType(needs), IotaPredicate.ofType(fallback))

    data class Quad(
        val first: IotaPredicate,
        val second: IotaPredicate,
        val third: IotaPredicate,
        val fourth: IotaPredicate
    ) : IotaMultiPredicate {
        override fun test(iotas: Iterable<Iota?>): Boolean {
            val it = iotas.iterator()
            return it.hasNext() && first.test(it.next()) &&
                    it.hasNext() && second.test(it.next()) &&
                    it.hasNext() && third.test(it.next()) &&
                    it.hasNext() && fourth.test(it.next()) && !it.hasNext()
        }
    }
}
