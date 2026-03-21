package com.meepoffaith.hextrapats.casting.arithmetic.operator.vecset;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.Vec3SetIota;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.meepoffaith.hextrapats.init.IotaTypes.VEC_SET;

public class OperatorInsert extends OperatorBase{
    boolean returnBool;

    public OperatorInsert(boolean returnBool){
        super(2, IotaMultiPredicate.pair(IotaPredicate.ofType(VEC_SET), IotaPredicate.TRUE));
        this.returnBool = returnBool;
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        Vec3SetIota set = stack.getVec3Set(0);
        Vec3d vec = stack.getVec3(1);
        boolean added = set.add(vec);
        return returnBool ? List.of(set, new BooleanIota(added)) : asActionResult(set);
    }
}
