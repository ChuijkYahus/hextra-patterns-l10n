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
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS;

public class OperatorAddSets extends OperatorBase{
    public OperatorAddSets(){
        super(2, ALL_SETS);
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        AnySet set = stack.getSet(0);
        return set.operate(
            d -> {
                DoubleSet set2 = stack.getNumSet(1).getSet();
                d.addAll(set2);
                return asActionResult(new DoubleSetIota(d));
            },
            v -> {
                VecSet set2 = stack.getVec3Set(1).getSet();
                v.addAll(set2);
                return asActionResult(new VecSetIota(v));
            }, e -> {
                Set<Entity> set2 = stack.getEntitySet(1).getSet();
                e.addAll(set2);
                return asActionResult(new EntitySetIota(e));
            }
        );
    }
}
