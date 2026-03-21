package com.meepoffaith.hextrapats.casting.arithmetic.operator.numset;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import com.meepoffaith.hextrapats.util.MultiPreds;
import org.jetbrains.annotations.NotNull;

import static com.meepoffaith.hextrapats.init.IotaTypes.NUM_SET;

public class OperatorAmountN extends OperatorBase{
    public OperatorAmountN(){
        super(1, MultiPreds.all(NUM_SET));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        DoubleSetIota set = stack.getNumSet(0);
        return asActionResult(new DoubleIota(set.size()));
    }
}
