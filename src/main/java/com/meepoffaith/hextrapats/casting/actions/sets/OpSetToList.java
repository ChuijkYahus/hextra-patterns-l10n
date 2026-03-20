package com.meepoffaith.hextrapats.casting.actions.sets;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.util.HextraUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OpSetToList extends ConstMediaActionBase{
    public int argc = 1;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        Set<String> set = stack.getJUSTASet(0);
        List<Iota> list = new ArrayList<>(set.size());
        for(String s : set){
            list.add(HextraUtils.deserialize(s, ctx.getWorld()));
        }
        return asActionResult(new ListIota(list));
    }
}
