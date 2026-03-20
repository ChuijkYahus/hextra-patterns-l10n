package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.bool.OperatorInRange;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.bool.OperatorOutRange;

import java.util.List;

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
        if(pattern.sigsEqual(IN_RANGE)){
            return new OperatorInRange();
        }else if(pattern.sigsEqual(OUT_RANGE)){
            return new OperatorOutRange();
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Bool Arithmetic " + this);
        }
    }
}
