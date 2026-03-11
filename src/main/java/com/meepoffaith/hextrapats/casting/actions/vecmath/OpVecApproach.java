package com.meepoffaith.hextrapats.casting.actions.vecmath;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class OpVecApproach extends ConstMediaActionBase{
    public int argc = 3;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        Vec3d from = stack.getVec3(0);
        Vec3d to = stack.getVec3(1);
        double speed = stack.getDouble(2);
        Vec3d step = to.subtract(from);

        if(step.length() < speed) return asActionResult(new Vec3Iota(to));

        step = step.normalize().multiply(speed);

        return asActionResult(new Vec3Iota(from.add(step)));
    }
}
