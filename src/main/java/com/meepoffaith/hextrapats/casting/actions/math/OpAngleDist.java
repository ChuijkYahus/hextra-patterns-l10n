package com.meepoffaith.hextrapats.casting.actions.math;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.util.MathUtils;

import java.util.List;

public class OpAngleDist extends ConstMediaActionBase{
    public int argc = 2;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        double ang1 = stack.getDouble(0);
        double ang2 = stack.getDouble(1);

        return asActionResult(new DoubleIota(MathUtils.angleDist(ang1, ang2)));
    }
}
