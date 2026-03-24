package com.meepoffaith.hextrapats.casting.arithmetic.operator.set;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota;
import com.meepoffaith.hextrapats.casting.iota.VecSetIota;
import com.meepoffaith.hextrapats.util.AnySet;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS;

public class OperatorIntersection extends OperatorBase{
    public OperatorIntersection(){
        super(2, ALL_SETS);
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        AnySet set = stack.getSet(0);
        return set.operate(
            d -> {
                Set<Double> set2 = stack.getNumSet(1).getSet();
                HashSet<Double> intersection = new HashSet<>();
                for(double key : d.getSet()){
                    if(set2.contains(key)) intersection.add(key);
                }
                return asActionResult(new DoubleSetIota(intersection));
            },
            v -> {
                Set<Vec3d> set2 = stack.getVec3Set(1).getSet();
                HashSet<Vec3d> intersection = new HashSet<>();
                for(Vec3d key : v.getSet()){
                    if(set2.contains(key)) intersection.add(key);
                }
                return asActionResult(new VecSetIota(intersection));
            }, e -> {
                Set<Entity> set2 = stack.getEntitySet(1).getSet();
                HashSet<Entity> intersection = new HashSet<>();
                for(Entity key : e.getSet()){
                    if(set2.contains(key)) intersection.add(key);
                }
                return asActionResult(new EntitySetIota(intersection));
            }
        );
    }
}
