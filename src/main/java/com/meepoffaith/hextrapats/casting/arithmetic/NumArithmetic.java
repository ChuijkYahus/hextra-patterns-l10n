package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorUnary;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.num.OperatorApproach;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.num.OperatorTurn;
import com.meepoffaith.hextrapats.util.MathUtils;
import com.meepoffaith.hextrapats.util.generics.Func1to1;
import com.meepoffaith.hextrapats.util.generics.Func2to1;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;
import static com.meepoffaith.hextrapats.init.Arithmetics.*;

public class NumArithmetic implements Arithmetic{
    private static final List<HexPattern> OPS = List.of(
        INVERT,
        INCREMENT,
        DECREMENT,
        APPROACH,
        ANGLE_DIST,
        ANGLE_APPROACH
    );

    @Override
    public String arithName(){
        return "hextrapats_double_math";
    }

    @Override
    public Iterable<HexPattern> opTypes(){
        return OPS;
    }

    @Override
    public Operator getOperator(HexPattern pattern){
        if(pattern.sigsEqual(INVERT)){
            return makeNumToNum(d -> -d);
        }else if(pattern.sigsEqual(INCREMENT)){
            return makeNumToNum(d -> d + 1);
        }else if(pattern.sigsEqual(DECREMENT)){
            return makeNumToNum(d -> d - 1);
        }else if(pattern.sigsEqual(APPROACH)){
            return new OperatorApproach();
        }else if(pattern.sigsEqual(ANGLE_DIST)){
            return makeNumNumtoNum(MathUtils::angleDist);
        }else if(pattern.sigsEqual(ANGLE_APPROACH)){
            return new OperatorTurn();
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Vec3 Bool Arithmetic " + this);
        }
    }

    private OperatorUnary makeNumToNum(Func1to1<Double, Double> op){
        return new OperatorUnary(IotaMultiPredicate.all(IotaPredicate.ofType(DOUBLE)),
            i -> new DoubleIota(op.apply(Operator.downcast(i, DOUBLE).getDouble()))
        );
    }

    private OperatorBinary makeNumNumtoNum(Func2to1<Double, Double, Double> op){
        return new OperatorBinary(IotaMultiPredicate.all(IotaPredicate.ofType(DOUBLE)),
            (i, j) -> new DoubleIota(op.apply(Operator.downcast(i, DOUBLE).getDouble(), Operator.downcast(j, DOUBLE).getDouble()))
        );
    }
}
