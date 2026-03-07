package com.meepoffaith.hextrapats.casting.actions.logic;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;

import java.util.List;

public class OpNoConsumeEquality extends ConstMediaActionBase{
    public int argc = 2;
    public long mediaCost = 0L;
    boolean invert;

    public OpNoConsumeEquality(boolean invert){
        this.invert = invert;
    }

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        Iota left = stack.get(0);
        Iota right = stack.get(1);

        return List.of(left, right, new BooleanIota(Iota.tolerates(left, right) != invert));
    }
}
