package com.meepoffaith.hextrapats.casting.arithmetic.operator.set;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota.DoubleSet;
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota;
import com.meepoffaith.hextrapats.casting.iota.VecSetIota;
import com.meepoffaith.hextrapats.casting.iota.VecSetIota.VecSet;
import com.meepoffaith.hextrapats.util.AnySet;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS;

public class OperatorDisjunction extends OperatorBase{
    public OperatorDisjunction(){
        super(2, ALL_SETS);
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        AnySet set = stack.getSet(0);
        return set.operate(
            d -> {
                DoubleSet set2 = stack.getNumSet(1).getSet();
                DoubleSet disjunction = new DoubleSet();
                for(double key : d){
                    if(!set2.contains(key)){
                        disjunction.add(key);
                    }else{
                        set2.remove(key);
                    }
                }
                disjunction.addAll(set2);
                return asActionResult(new DoubleSetIota(disjunction));
            }, v -> {
                VecSet set2 = stack.getVec3Set(1).getSet();
                VecSet disjunction = new VecSet();
                for(Vec3d key : v){
                    if(!set2.contains(key)){
                        disjunction.add(key);
                    }else{
                        set2.remove(key);
                    }
                }
                disjunction.addAll(set2);
                return asActionResult(new VecSetIota(disjunction));
            }, e -> {
                Set<Entity> set2 = stack.getEntitySet(1).getSet();
                HashSet<Entity> disjunction = new HashSet<>();
                for(Entity key : e){
                    if(!set2.contains(key)){
                        disjunction.add(key);
                    }else{
                        set2.remove(key);
                    }
                }
                disjunction.addAll(set2);
                return asActionResult(new EntitySetIota(disjunction));
            }
        );
    }
}
