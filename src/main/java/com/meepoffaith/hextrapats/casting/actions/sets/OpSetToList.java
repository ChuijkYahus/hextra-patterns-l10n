package com.meepoffaith.hextrapats.casting.actions.sets;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.*;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.util.AnySet;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OpSetToList extends ConstMediaActionBase{
    public int argc = 1;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        AnySet set = stack.getSet(0);
        List<Iota> list = new ArrayList<>();
        return set.operate(
            dSet -> {
                for(double d : dSet){
                    list.add(new DoubleIota(d));
                }
                return asActionResult(new ListIota(list));
            }, vSet -> {
                for(Vec3d v : vSet){
                    list.add(new Vec3Iota(v));
                }
                return asActionResult(new ListIota(list));
            }, eSet -> {
                for(Entity d : eSet){
                    list.add(new EntityIota(d));
                }
                return asActionResult(new ListIota(list));
            }
        );
    }
}
