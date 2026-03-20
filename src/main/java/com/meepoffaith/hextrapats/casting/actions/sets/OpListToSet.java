package com.meepoffaith.hextrapats.casting.actions.sets;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.iota.SetIota;

import java.util.HashSet;
import java.util.List;

public class OpListToSet extends ConstMediaActionBase{
    public int argc = 1;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        List<Iota> list = stack.getJUSTAList(0);
        SetIota out = new SetIota(new HashSet<>());
        for(Iota iota : list){
            out.add(iota);
        }
        return asActionResult(out);
    }
}
