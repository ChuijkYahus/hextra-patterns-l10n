package com.meepoffaith.hextrapats.casting.arithmetic.operator.vec3;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.util.HextraUtils;
import com.meepoffaith.hextrapats.util.MultiPreds;
import org.jetbrains.annotations.NotNull;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;
import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3;

public class OperatorOutRangeVec extends OperatorBase{
    public OperatorOutRangeVec(){
        super(4, MultiPreds.quad(VEC3, DOUBLE, DOUBLE, DOUBLE));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        double len = stack.getVec3(0).length();
        double a = stack.getDouble(1);
        double b = stack.getDouble(2);
        int op = stack.getPositiveIntUnderInclusive(3, 3);
        double min = Math.min(a, b);
        double max = Math.max(a, b);
        boolean out = switch(op){
            case 0 -> len < min || max < len;
            case 1 -> HextraUtils.lessEq(len, min) || max < len;
            case 2 -> len < min || HextraUtils.lessEq(max, len);
            case 3 -> HextraUtils.lessEq(len, min) || HextraUtils.lessEq(max, len);
            default -> throw new IllegalStateException("How the fuck");
        };
        return asActionResult(new BooleanIota(out));
    }
}
