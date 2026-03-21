package com.meepoffaith.hextrapats.casting.arithmetic.operator.set;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import com.meepoffaith.hextrapats.casting.iota.Vec3SetIota;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.meepoffaith.hextrapats.init.IotaTypes.NUM_SET;
import static com.meepoffaith.hextrapats.init.IotaTypes.VEC_SET;

public class OperatorInsert extends OperatorBase{
    boolean returnBool;

    public OperatorInsert(boolean returnBool){
        super(2, IotaMultiPredicate.pair(IotaPredicate.or(IotaPredicate.ofType(NUM_SET), IotaPredicate.ofType(VEC_SET)), IotaPredicate.TRUE));
        this.returnBool = returnBool;
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        Iota set = stack.get(0);
        if(set instanceof DoubleSetIota dSet){
            double num = stack.getDouble(1);
            boolean added = dSet.add(num);
            return returnBool ? List.of(dSet, new BooleanIota(added)) : asActionResult(set);
        }else if(set instanceof Vec3SetIota vSet){
            Vec3d vec = stack.getVec3(1);
            boolean added = vSet.add(vec);
            return returnBool ? List.of(vSet, new BooleanIota(added)) : asActionResult(set);
        }else{
            throw new IllegalStateException("How did we get here");
        }
    }
}
