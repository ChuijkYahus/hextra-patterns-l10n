package com.meepoffaith.hextrapats.casting.arithmetic.operator.vecset;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.Vec3SetIota;
import com.meepoffaith.hextrapats.util.MultiPreds;
import org.jetbrains.annotations.NotNull;

import static com.meepoffaith.hextrapats.init.IotaTypes.VEC_SET;

public class OperatorAmountV extends OperatorBase{
    public OperatorAmountV(){
        super(1, MultiPreds.all(VEC_SET));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        Vec3SetIota set = stack.getVec3Set(0);
        return asActionResult(new DoubleIota(set.size()));
    }
}
