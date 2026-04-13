package com.meepoffaith.hextrapats.casting.actions.queryentity;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import net.minecraft.entity.Entity;

import java.util.List;

public class OpBodyYaw extends ConstMediaActionBase{
    public int argc = 1;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment env){
        Entity e = stack.getEntity(0);
        env.assertEntityInRange(e);

        return asActionResult(new DoubleIota(Math.toRadians(e.getBodyYaw())));
    }
}
