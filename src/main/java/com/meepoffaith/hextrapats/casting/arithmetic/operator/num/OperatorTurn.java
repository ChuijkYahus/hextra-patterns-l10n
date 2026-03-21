package com.meepoffaith.hextrapats.casting.arithmetic.operator.num;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.util.MathUtils;
import com.meepoffaith.hextrapats.util.MultiPreds;
import org.jetbrains.annotations.NotNull;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;

public class OperatorTurn extends OperatorBase{
    public OperatorTurn(){
        super(3, MultiPreds.all(DOUBLE));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        double from = stack.getDouble(0);
        double to = stack.getDouble(1);
        double speed = stack.getDouble(2);

        if(MathUtils.angleDist(from, to) < speed) return asActionResult(new DoubleIota(to));

        from = MathUtils.mod(from, MathUtils.TAU);
        to = MathUtils.mod(to, MathUtils.TAU);

        double fwdDist = Math.abs(from - to);
        double backDst = MathUtils.TAU - fwdDist;

        if(from > to == backDst > fwdDist){
            from -= speed;
        }else{
            from += speed;
        }

        return asActionResult(new DoubleIota(from));
    }
}
