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

import java.util.Set;

import static com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS;

public class OperatorSubtractSets extends OperatorBase{
    public OperatorSubtractSets(){
        super(2, ALL_SETS);
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        AnySet set = stack.getSet(0);
        return set.operate(
            d -> {
                Set<Double> set1 = d.getSet();
                Set<Double> set2 = stack.getNumSet(1).getSet();
                set1.removeAll(set2);
                return asActionResult(new DoubleSetIota(set1));
            },
            v -> {
                Set<Vec3d> set1 = v.getSet();
                Set<Vec3d> set2 = stack.getVec3Set(1).getSet();
                set1.removeAll(set2);
                return asActionResult(new VecSetIota(set1));
            }, e -> {
                Set<Entity> set1 = e.getSet();
                Set<Entity> set2 = stack.getEntitySet(1).getSet();
                set1.removeAll(set2);
                return asActionResult(new EntitySetIota(set1));
            }
        );
    }
}
