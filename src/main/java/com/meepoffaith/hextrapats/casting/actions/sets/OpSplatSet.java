package com.meepoffaith.hextrapats.casting.actions.sets;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OpSplatSet extends ConstMediaActionBase{
    public int argc = 1;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        Set<Double> set = stack.getJUSTASet(0);
        List<Iota> out = new ArrayList<>();
        for(double d : set){
            out.add(new DoubleIota(d));
        }
        return out;
    }
}
