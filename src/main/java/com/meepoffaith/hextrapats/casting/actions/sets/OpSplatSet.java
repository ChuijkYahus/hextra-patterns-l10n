package com.meepoffaith.hextrapats.casting.actions.sets;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.EntityIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.util.AnySet;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class OpSplatSet extends ConstMediaActionBase{
    public int argc = 1;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        AnySet set = stack.getSet(0);
        List<Iota> out = new ArrayList<>();
        return set.operate(
            dSet -> {
                for(double d : dSet){
                    out.add(new DoubleIota(d));
                }
                return out;
            }, vSet -> {
                for(Vec3d v : vSet){
                    out.add(new Vec3Iota(v));
                }
                return out;
            }, eSet -> {
                for(Entity d : eSet){
                    out.add(new EntityIota(d));
                }
                return out;
            }
        );
    }
}
