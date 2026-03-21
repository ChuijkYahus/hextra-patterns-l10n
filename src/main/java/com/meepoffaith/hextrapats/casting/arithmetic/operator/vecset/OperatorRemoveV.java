package com.meepoffaith.hextrapats.casting.arithmetic.operator.vecset;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.Vec3SetIota;
import com.meepoffaith.hextrapats.util.MultiPreds;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3;
import static com.meepoffaith.hextrapats.init.IotaTypes.VEC_SET;

public class OperatorRemoveV extends OperatorBase{
    boolean returnBool;

    public OperatorRemoveV(boolean returnBool){
        super(2, MultiPreds.pair(VEC_SET, VEC3));
        this.returnBool = returnBool;
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        Vec3SetIota set = stack.getVec3Set(0);
        Vec3d iota = stack.getVec3(1);
        boolean removed = set.remove(iota);
        return returnBool ? List.of(set, new BooleanIota(removed)) : asActionResult(set);
    }
}
