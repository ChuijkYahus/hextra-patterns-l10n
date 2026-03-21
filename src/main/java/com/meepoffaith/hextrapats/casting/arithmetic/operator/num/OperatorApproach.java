package com.meepoffaith.hextrapats.casting.arithmetic.operator.num;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.util.MultiPreds;
import org.jetbrains.annotations.NotNull;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;

public class OperatorApproach extends OperatorBase{
    public OperatorApproach(){
        super(3, MultiPreds.all(DOUBLE));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        double from = stack.getDouble(0);
        double to = stack.getDouble(1);
        double speed = stack.getDouble(2);

        if(Math.abs(from - to) < speed) return asActionResult(new DoubleIota(to));

        if(to < from){
            from -= speed;
        }else{
            from += speed;
        }

        return asActionResult(new DoubleIota(from));
    }
}
