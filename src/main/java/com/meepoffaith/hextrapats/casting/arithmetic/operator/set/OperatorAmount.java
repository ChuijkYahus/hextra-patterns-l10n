package com.meepoffaith.hextrapats.casting.arithmetic.operator.set;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.util.AnySet;
import org.jetbrains.annotations.NotNull;

import static com.meepoffaith.hextrapats.util.MultiPreds.ANY_SET;

public class OperatorAmount extends OperatorBase{
    public OperatorAmount(){
        super(1, IotaMultiPredicate.all(ANY_SET));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        AnySet set = stack.getSet(0);
        return set.operate( //Maybe I should make an interface or something
            d -> asActionResult(new DoubleIota(d.size())),
            v -> asActionResult(new DoubleIota(v.size())),
            e -> asActionResult(new DoubleIota(e.size()))
        );
    }
}
