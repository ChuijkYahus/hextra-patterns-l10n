package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.OperatorQuadary;
import com.meepoffaith.hextrapats.util.HextraUtils;
import com.meepoffaith.hextrapats.util.generics.Func4to1;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;
import static com.meepoffaith.hextrapats.init.Arithmetics.IN_RANGE;
import static com.meepoffaith.hextrapats.init.Arithmetics.OUT_RANGE;

public class BoolArithmetic implements Arithmetic{
    private static final List<HexPattern> OPS = List.of(
        IN_RANGE,
        OUT_RANGE
    );

    @Override
    public String arithName(){
        return "hextrapats_bool_math";
    }

    @Override
    public Iterable<HexPattern> opTypes(){
        return OPS;
    }

    @Override
    public Operator getOperator(HexPattern pattern){
        if(pattern.equals(IN_RANGE)){
            return makeRange((val, a, b, op) -> {
                double min = Math.min(a, b);
                double max = Math.max(a, b);
                return switch(op){
                    case 0 -> min < val && val < max;
                    case 1 -> HextraUtils.lessEq(min, val) && val < max;
                    case 2 -> min < val && HextraUtils.lessEq(val, max);
                    case 3 -> HextraUtils.lessEq(min, val) && HextraUtils.lessEq(val, max);
                    default -> throw new InvalidOperatorException(op + " is not a valid op for Range Exaltation");
                };
            });
        }else if(pattern.equals(OUT_RANGE)){
            return makeRange((val, a, b, op) -> {
                double min = Math.min(a, b);
                double max = Math.max(a, b);
                return switch(op){
                    case 0 -> val < min || max < val;
                    case 1 -> HextraUtils.lessEq(val, min) || max < val;
                    case 2 -> val < min || HextraUtils.lessEq(max, val);
                    case 3 -> HextraUtils.lessEq(val, min) || HextraUtils.lessEq(max, val);
                    default -> throw new InvalidOperatorException(op + " is not a valid op for Range Exaltation II");
                };
            });
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Bool Arithmetic " + this);
        }
    }

    private OperatorQuadary makeRange(Func4to1<Double, Double, Double, Integer, Boolean> op){
        return new OperatorQuadary(IotaMultiPredicate.all(IotaPredicate.ofType(DOUBLE)),
            (i, j, k, l) -> new BooleanIota(op.apply(
                Operator.downcast(i, DOUBLE).getDouble(),
                Operator.downcast(j, DOUBLE).getDouble(),
                Operator.downcast(k, DOUBLE).getDouble(),
                HextraUtils.getInt(l, 4)
            ))
        );
    }
}
