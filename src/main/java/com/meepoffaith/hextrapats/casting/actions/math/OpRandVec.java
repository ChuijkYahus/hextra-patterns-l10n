package com.meepoffaith.hextrapats.casting.actions.math;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class OpRandVec extends ConstMediaActionBase{
    public int argc = 0;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        double pitch = -Math.PI / 2 + Math.random() * Math.PI;
        double yaw = Math.random() * 2 * Math.PI;
        //Replicates Vec3d.fromPolar, but with radian doubles instead of degree floats as input.
        double f = Math.cos(-yaw - Math.PI);
        double g = Math.sin(-yaw - Math.PI);
        double h = -Math.cos(-pitch);
        double i = Math.sin(-pitch);
        return asActionResult(new Vec3Iota(new Vec3d(g * h, i, f * h)));
    }
}
