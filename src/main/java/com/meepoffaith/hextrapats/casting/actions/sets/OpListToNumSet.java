package com.meepoffaith.hextrapats.casting.actions.sets;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;

import java.util.List;

public class OpListToNumSet extends ConstMediaActionBase{
    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        return super.execute(stack, ctx);
    }
}
