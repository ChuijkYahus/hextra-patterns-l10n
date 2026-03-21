package com.meepoffaith.hextrapats.casting.actions.sets;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.iota.Vec3SetIota;

import java.util.HashSet;
import java.util.List;

public class OpEmptyVecSet extends ConstMediaActionBase{
    public int argc = 0;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        return asActionResult(new Vec3SetIota(new HashSet<>()));
    }
}
