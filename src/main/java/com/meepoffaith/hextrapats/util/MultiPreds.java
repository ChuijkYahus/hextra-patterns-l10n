package com.meepoffaith.hextrapats.util;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.*;
import static com.meepoffaith.hextrapats.init.IotaTypes.*;

public class MultiPreds{
    public static IotaMultiPredicate SET_OP = either3(pair(NUM_SET, DOUBLE), pair(VEC_SET, VEC3), pair(ENTITY_SET, ENTITY));
    public static IotaMultiPredicate ALL_SETS = either3(all(NUM_SET), all(VEC_SET), all(ENTITY_SET));
    public static IotaPredicate ANY_SET = IotaPredicate.or(IotaPredicate.ofType(NUM_SET), IotaPredicate.or(IotaPredicate.ofType(VEC_SET), IotaPredicate.ofType(ENTITY_SET)));

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

    public static IotaMultiPredicate either3(IotaMultiPredicate first, IotaMultiPredicate second, IotaMultiPredicate third){
        return new Either3(first, second, third);
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

    record Either3(IotaMultiPredicate first, IotaMultiPredicate second, IotaMultiPredicate third) implements IotaMultiPredicate{
        @Override
        public boolean test(Iterable<Iota> iotas){
            return first.test(iotas) || second.test(iotas) || third.test(iotas);
        }
    }


}
