package com.meepoffaith.hextrapats.casting.arithmetic.operator.set;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
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

import java.util.List;

import static com.meepoffaith.hextrapats.util.MultiPreds.SET_OP;

public class OperatorExists extends OperatorBase{
    public OperatorExists(){
        super(2, SET_OP);
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        AnySet set = stack.getSet(0);
        return set.operate(
            d -> {
                double num = stack.getDouble(1);
                boolean found = d.contains(num);
                return List.of(new DoubleSetIota(d), new BooleanIota(found));
            },
            v -> {
                Vec3d vec = stack.getVec3(1);
                boolean found = v.contains(vec);
                return List.of(new VecSetIota(v), new BooleanIota(found));
            },
            e -> {
                Entity entity = stack.getEntity(1);
                boolean found = e.contains(entity);
                return List.of(new EntitySetIota(e), new BooleanIota(found));
            }
        );
    }
}
