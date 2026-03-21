package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.vecset.*;
import com.meepoffaith.hextrapats.casting.iota.Vec3SetIota;
import com.meepoffaith.hextrapats.util.MultiPreds;
import com.meepoffaith.hextrapats.util.generics.Func2to1;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Set;

import static com.meepoffaith.hextrapats.init.Arithmetics.SET_INSERT_RET;
import static com.meepoffaith.hextrapats.init.Arithmetics.SET_REMOVE_RET;
import static com.meepoffaith.hextrapats.init.IotaTypes.VEC_SET;

public class VecSetArithmetic implements Arithmetic{
    private static final List<HexPattern> OPS = List.of(
        ADD,
        SUB,
        AND,
        XOR,
        ABS,
        INDEX_OF,
        APPEND,
        SET_INSERT_RET,
        REMOVE,
        SET_REMOVE_RET
    );

    @Override
    public String arithName(){
        return "vec_set_ops";
    }

    @Override
    public Iterable<HexPattern> opTypes(){
        return OPS;
    }

    @Override
    public Operator getOperator(HexPattern pattern){
        if(pattern.sigsEqual(ADD)){
            return makeSetSettoSet((s1, s2) -> {
                s1.addAll(s2);
                return s1;
            });
        }else if(pattern.sigsEqual(SUB)){
            return makeSetSettoSet((s1, s2) -> {
                s1.removeAll(s2);
                return s1;
            });
        }else if(pattern.sigsEqual(AND)){
            return new OperatorIntersection();
        }else if(pattern.sigsEqual(XOR)){
            return new OperatorDisjunction();
        }else if(pattern.sigsEqual(ABS)){
            return new OperatorAmount();
        }else if(pattern.sigsEqual(INDEX_OF)){
            return new OperatorExists();
        }else if(pattern.sigsEqual(APPEND)){
            return new OperatorInsert(false);
        }else if(pattern.sigsEqual(SET_INSERT_RET)){
            return new OperatorInsert(true);
        }else if(pattern.sigsEqual(REMOVE)){
            return new OperatorRemove(false);
        }else if(pattern.sigsEqual(SET_REMOVE_RET)){
            return new OperatorRemove(true);
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Set Arithmetic " + this);
        }
    }

    private OperatorBinary makeSetSettoSet(Func2to1<Set<Vec3d>, Set<Vec3d>, Set<Vec3d>> op){
        return new OperatorBinary(MultiPreds.all(VEC_SET),
            (i, j) -> new Vec3SetIota(op.apply(Operator.downcast(i, VEC_SET).getSet(), Operator.downcast(j, VEC_SET).getSet()))
        );
    }
}
