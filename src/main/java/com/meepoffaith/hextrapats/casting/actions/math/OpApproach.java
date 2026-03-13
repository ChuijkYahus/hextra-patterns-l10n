package com.meepoffaith.hextrapats.casting.actions.math;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;

import java.util.List;

public class OpApproach extends ConstMediaActionBase{
    public int argc = 3;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        double from = stack.getDouble(0);
        double to = stack.getDouble(1);
        double speed = stack.getDouble(2);

        if(Math.abs(from - to) < speed) return asActionResult(new DoubleIota(to));

        if(to < from){
            from -= speed;
        }else{
            from += speed;
        }

        return asActionResult(new DoubleIota(from));
    }
}
