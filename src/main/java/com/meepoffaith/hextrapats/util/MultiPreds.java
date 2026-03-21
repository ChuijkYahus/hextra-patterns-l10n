package com.meepoffaith.hextrapats.util;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;

public class MultiPreds{
    public static IotaMultiPredicate all(IotaType<?> type){
        return IotaMultiPredicate.all(IotaPredicate.ofType(type));
    }

    public static IotaMultiPredicate pair(IotaType<?> first, IotaType<?> second){
        return IotaMultiPredicate.pair(IotaPredicate.ofType(first), IotaPredicate.ofType(second));
    }

    public static IotaMultiPredicate triple(IotaType<?> first, IotaType<?> second, IotaType<?> third){
        return IotaMultiPredicate.triple(IotaPredicate.ofType(first), IotaPredicate.ofType(second), IotaPredicate.ofType(third));
    }

    public static IotaMultiPredicate quad(IotaType<?> first, IotaType<?> second, IotaType<?> third, IotaType<?> fourth){
        return new Quad(IotaPredicate.ofType(first), IotaPredicate.ofType(second), IotaPredicate.ofType(third), IotaPredicate.ofType(fourth));
    }

    public static IotaMultiPredicate any(IotaType<?> needs, IotaType<?> fallback){
        return IotaMultiPredicate.any(IotaPredicate.ofType(needs), IotaPredicate.ofType(fallback));
    }

    record Quad(IotaPredicate first, IotaPredicate second, IotaPredicate third, IotaPredicate fourth) implements IotaMultiPredicate{
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
}
