package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.vec3.OperatorInRangeVec;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.vec3.OperatorOutRangeVec;
import com.meepoffaith.hextrapats.util.HextraUtils;
import com.meepoffaith.hextrapats.util.MultiPreds;
import com.meepoffaith.hextrapats.util.generics.Func2to1;
import net.minecraft.util.math.Vec3d;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3;
import static com.meepoffaith.hextrapats.init.Arithmetics.*;

public class Vec3BoolArithmetic implements Arithmetic{
    private static final List<HexPattern> OPS = List.of(
        GREATER,
        LESS,
        GREATER_EQ,
        LESS_EQ,
        LEN_EQ,
        LEN_NEQ,
        IN_RANGE,
        OUT_RANGE
    );

    @Override
    public String arithName(){
        return "hextrapats_vec3_bool_math";
    }

    @Override
    public Iterable<HexPattern> opTypes(){
        return OPS;
    }

    @Override
    public Operator getOperator(HexPattern pattern){ // Switchelss behavior :pensive:
        if(pattern.sigsEqual(GREATER)){
            return makeComp((a, b) -> a.length() > b.length());
        }else if(pattern.sigsEqual(LESS)){
            return makeComp((a, b) -> a.length() < b.length());
        }else if(pattern.sigsEqual(GREATER_EQ)){
            return makeComp((a, b) -> HextraUtils.greaterEq(a.length(), b.length()));
        }else if(pattern.sigsEqual(LESS_EQ)){
            return makeComp((a, b) -> HextraUtils.lessEq(a.length(), b.length()));
        }else if(pattern.sigsEqual(LEN_EQ)){
            return makeComp((a, b) -> DoubleIota.tolerates(a.length(), b.length()));
        }else if(pattern.sigsEqual(LEN_NEQ)){
            return makeComp((a, b) -> !DoubleIota.tolerates(a.length(), b.length()));
        }else if(pattern.sigsEqual(IN_RANGE)){
            return new OperatorInRangeVec();
        }else if(pattern.sigsEqual(OUT_RANGE)){
            return new OperatorOutRangeVec();
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Vec3 Bool Arithmetic " + this);
        }
    }

    private OperatorBinary makeComp(Func2to1<Vec3d, Vec3d, Boolean> op){
        return new OperatorBinary(MultiPreds.all(VEC3),
            (i, j) -> new BooleanIota(op.apply(Operator.downcast(i, VEC3).getVec3(), Operator.downcast(j, VEC3).getVec3()))
        );
    }
}
