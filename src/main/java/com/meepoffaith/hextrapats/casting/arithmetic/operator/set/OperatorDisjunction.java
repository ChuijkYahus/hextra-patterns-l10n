package com.meepoffaith.hextrapats.casting.arithmetic.operator.set;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.SetIota;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.meepoffaith.hextrapats.init.IotaTypes.SET;

public class OperatorDisjunction extends OperatorBase{
    public OperatorDisjunction(){
        super(2, IotaMultiPredicate.all(IotaPredicate.ofType(SET)));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        Set<String> set1 = stack.getJUSTASet(0);
        Set<String> set2 = stack.getJUSTASet(1);
        HashSet<String> disjunction = new HashSet<>();
        for(String key : set1){
            if(!set2.contains(key)){
                disjunction.add(key);
            }else{
                set2.remove(key);
            }
        }
        disjunction.addAll(set2);
        return asActionResult(new SetIota(disjunction));
    }
}
