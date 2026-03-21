package com.meepoffaith.hextrapats.casting.arithmetic.operator.entityset;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota;
import com.meepoffaith.hextrapats.util.MultiPreds;
import org.jetbrains.annotations.NotNull;

import static com.meepoffaith.hextrapats.init.IotaTypes.ENTITY_SET;

public class OperatorAmountE extends OperatorBase{
    public OperatorAmountE(){
        super(1, MultiPreds.all(ENTITY_SET));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        EntitySetIota set = stack.getEntitySet(0);
        return asActionResult(new DoubleIota(set.size()));
    }
}
