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

public class OperatorInsert extends OperatorBase{
    boolean returnBool;

    public OperatorInsert(boolean returnBool){
        super(2, SET_OP);
        this.returnBool = returnBool;
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        AnySet set = stack.getSet(0);
        return set.operate(
            d -> {
                double num = stack.getDouble(1);
                boolean added = d.add(num);
                DoubleSetIota out = new DoubleSetIota(d);
                return returnBool ? List.of(out, new BooleanIota(added)) : asActionResult(out);
            },
            v -> {
                Vec3d vec = stack.getVec3(1);
                boolean added = v.add(vec);
                VecSetIota out = new VecSetIota(v);
                return returnBool ? List.of(out, new BooleanIota(added)) : asActionResult(out);
            }, e -> {
                Entity entity = stack.getEntity(1);
                boolean added = e.add(entity);
                EntitySetIota out = new EntitySetIota(e);
                return returnBool ? List.of(out, new BooleanIota(added)) : asActionResult(out);
            }
        );
    }
}
