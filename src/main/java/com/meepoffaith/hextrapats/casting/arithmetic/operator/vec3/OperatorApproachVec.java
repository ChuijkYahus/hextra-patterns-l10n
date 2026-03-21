package com.meepoffaith.hextrapats.casting.arithmetic.operator.vec3;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.util.MultiPreds;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;
import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3;

public class OperatorApproachVec extends OperatorBase{
    public OperatorApproachVec(){
        super(3, MultiPreds.triple(VEC3, VEC3, DOUBLE));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        Vec3d from = stack.getVec3(0);
        Vec3d to = stack.getVec3(1);
        double speed = stack.getDouble(2);

        Vec3d step = to.subtract(from);
        double stepLen = step.length();

        if(stepLen < speed) return asActionResult(new Vec3Iota(to));

        step = step.multiply(speed / stepLen);
        return asActionResult(new Vec3Iota(from.add(step)));
    }
}
