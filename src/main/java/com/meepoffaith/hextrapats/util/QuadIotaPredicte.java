package com.meepoffaith.hextrapats.util;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.Iota;

public class QuadIotaPredicte implements IotaMultiPredicate{
    IotaPredicate first, second, third, fourth;

    public QuadIotaPredicte(IotaPredicate first, IotaPredicate second, IotaPredicate third, IotaPredicate fourth){
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    @Override
    public boolean test(Iterable<Iota> iotas){
        var it = iotas.iterator();
        return it.hasNext() && first.test(it.next()) &&
            it.hasNext() && second.test(it.next()) &&
            it.hasNext() && third.test(it.next()) &&
            it.hasNext() && fourth.test(it.next()) &&
            !it.hasNext();
    }
}
