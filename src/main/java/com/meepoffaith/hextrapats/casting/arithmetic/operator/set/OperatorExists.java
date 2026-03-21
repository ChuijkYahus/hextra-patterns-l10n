package com.meepoffaith.hextrapats.casting.arithmetic.operator.set;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.meepoffaith.hextrapats.init.IotaTypes.NUM_SET;

public class OperatorExists extends OperatorBase{
    public OperatorExists(){
        super(2, IotaMultiPredicate.pair(IotaPredicate.ofType(NUM_SET), IotaPredicate.TRUE));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        DoubleSetIota set = stack.getNumSet(0);
        double iota = stack.getDouble(1);
        boolean found = set.contains(iota);
        return List.of(set, new BooleanIota(found));
    }
}
