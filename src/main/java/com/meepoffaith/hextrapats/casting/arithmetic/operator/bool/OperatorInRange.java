package com.meepoffaith.hextrapats.casting.arithmetic.operator.bool;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.util.HextraUtils;
import org.jetbrains.annotations.NotNull;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;

public class OperatorInRange extends OperatorBase{
    public OperatorInRange(){
        super(4, IotaMultiPredicate.all(IotaPredicate.ofType(DOUBLE)));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        double val = stack.getDouble(0);
        double a = stack.getDouble(1);
        double b = stack.getDouble(2);
        int op = stack.getPositiveIntUnderInclusive(3, 3);
        double min = Math.min(a, b);
        double max = Math.max(a, b);
        boolean in = switch(op){
            case 0 -> min < val && val < max;
            case 1 -> HextraUtils.lessEq(min, val) && val < max;
            case 2 -> min < val && HextraUtils.lessEq(val, max);
            case 3 -> HextraUtils.lessEq(min, val) && HextraUtils.lessEq(val, max);
            default -> throw new IllegalStateException("How the fuck");
        };
        return asActionResult(new BooleanIota(in));
    }
}
