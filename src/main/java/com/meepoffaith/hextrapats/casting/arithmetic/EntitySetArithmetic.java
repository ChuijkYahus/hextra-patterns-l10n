package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.entityset.*;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.numset.*;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota;
import com.meepoffaith.hextrapats.util.MultiPreds;
import com.meepoffaith.hextrapats.util.generics.Func2to1;
import net.minecraft.entity.Entity;

import java.util.List;
import java.util.Set;

import static com.meepoffaith.hextrapats.init.Arithmetics.SET_INSERT_RET;
import static com.meepoffaith.hextrapats.init.Arithmetics.SET_REMOVE_RET;
import static com.meepoffaith.hextrapats.init.IotaTypes.ENTITY_SET;
import static com.meepoffaith.hextrapats.init.IotaTypes.NUM_SET;

public class EntitySetArithmetic implements Arithmetic{
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
        return "entity_set_ops";
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
            return new OperatorIntersectionE();
        }else if(pattern.sigsEqual(XOR)){
            return new OperatorDisjunctionE();
        }else if(pattern.sigsEqual(ABS)){
            return new OperatorAmountE();
        }else if(pattern.sigsEqual(INDEX_OF)){
            return new OperatorExistsE();
        }else if(pattern.sigsEqual(APPEND)){
            return new OperatorInsertE(false);
        }else if(pattern.sigsEqual(SET_INSERT_RET)){
            return new OperatorInsertE(true);
        }else if(pattern.sigsEqual(REMOVE)){
            return new OperatorRemoveE(false);
        }else if(pattern.sigsEqual(SET_REMOVE_RET)){
            return new OperatorRemoveE(true);
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Set Arithmetic " + this);
        }
    }

    private OperatorBinary makeSetSettoSet(Func2to1<Set<Entity>, Set<Entity>, Set<Entity>> op){
        return new OperatorBinary(MultiPreds.all(NUM_SET),
            (i, j) -> new EntitySetIota(op.apply(Operator.downcast(i, ENTITY_SET).getSet(), Operator.downcast(j, ENTITY_SET).getSet()))
        );
    }
}
