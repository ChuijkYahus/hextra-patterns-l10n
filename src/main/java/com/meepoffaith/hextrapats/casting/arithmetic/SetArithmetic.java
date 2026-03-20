package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.set.OperatorAmount;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.set.OperatorExists;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.set.OperatorInsert;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.set.OperatorRemove;
import com.meepoffaith.hextrapats.casting.iota.SetIota;
import com.meepoffaith.hextrapats.util.generics.Func2to1;

import java.util.List;
import java.util.Set;

import static com.meepoffaith.hextrapats.init.IotaTypes.SET;

public class SetArithmetic implements Arithmetic{
    private static final List<HexPattern> OPS = List.of(
        ADD,
        SUB,
        ABS,
        INDEX_OF,
        APPEND,
        REMOVE
    );

    @Override
    public String arithName(){
        return "set_ops";
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
        }else if(pattern.sigsEqual(ABS)){
            return new OperatorAmount();
        }else if(pattern.sigsEqual(INDEX_OF)){
            return new OperatorExists();
        }else if(pattern.sigsEqual(APPEND)){
            return new OperatorInsert();
        }else if(pattern.sigsEqual(REMOVE)){
            return new OperatorRemove();
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Set Arithmetic " + this);
        }
    }

    private OperatorBinary makeSetSettoSet(Func2to1<Set<String>, Set<String>, Set<String>> op){
        return new OperatorBinary(IotaMultiPredicate.all(IotaPredicate.ofType(SET)),
            (i, j) -> new SetIota(op.apply(Operator.downcast(i, SET).getSet(), Operator.downcast(j, SET).getSet()))
        );
    }
}
