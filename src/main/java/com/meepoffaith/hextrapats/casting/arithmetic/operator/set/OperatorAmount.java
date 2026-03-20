package com.meepoffaith.hextrapats.casting.arithmetic.operator.set;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.SetIota;
import org.jetbrains.annotations.NotNull;

import static com.meepoffaith.hextrapats.init.IotaTypes.SET;

public class OperatorAmount extends OperatorBase{
    public OperatorAmount(){
        super(1, IotaMultiPredicate.all(IotaPredicate.ofType(SET)));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        SetIota set = stack.getSet(0);
        return asActionResult(new DoubleIota(set.size()));
    }
}
