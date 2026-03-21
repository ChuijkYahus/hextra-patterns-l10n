package com.meepoffaith.hextrapats.casting.arithmetic.operator.numset;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import com.meepoffaith.hextrapats.util.MultiPreds;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.meepoffaith.hextrapats.init.IotaTypes.NUM_SET;

public class OperatorIntersectionN extends OperatorBase{
    public OperatorIntersectionN(){
        super(2, MultiPreds.all(NUM_SET));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        Set<Double> set1 = stack.getNumSet(0).getSet();
        Set<Double> set2 = stack.getNumSet(1).getSet();
        HashSet<Double> intersection = new HashSet<>();
        for(double key : set1){
            if(set2.contains(key)) intersection.add(key);
        }
        return asActionResult(new DoubleSetIota(intersection));
    }
}
