package com.meepoffaith.hextrapats.casting.actions.lists;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;

import java.util.ArrayList;
import java.util.List;

public class OpShuffle extends ConstMediaActionBase{
    public int argc = 1;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        List<Iota> list = stack.getJUSTAList(0);
        List<Iota> out = new ArrayList<>();

        while(!list.isEmpty()){
            out.add(list.remove(ctx.getWorld().getRandom().nextBetweenExclusive(0, list.size())));
        }

        return asActionResult(new ListIota(out));
    }
}
