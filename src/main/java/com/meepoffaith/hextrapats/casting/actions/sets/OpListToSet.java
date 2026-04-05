package com.meepoffaith.hextrapats.casting.actions.sets;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.*;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota.DoubleSet;
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota;
import com.meepoffaith.hextrapats.casting.iota.VecSetIota;
import com.meepoffaith.hextrapats.casting.iota.VecSetIota.VecSet;
import net.minecraft.entity.Entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OpListToSet extends ConstMediaActionBase{
    public int argc = 1;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        List<Iota> iotas = stack.getJUSTAList(0);

        if(iotas.isEmpty()) throw MishapInvalidIota.of(new ListIota(iotas), 0, "hextrapats:set_list");
        Iota out;
        if(iotas.get(0) instanceof DoubleIota){
            out = toNumSet(iotas);
        }else if(iotas.get(0) instanceof Vec3Iota){
            out = toVecSet(iotas);
        }else if(iotas.get(0) instanceof EntityIota){
            out = toEntSet(iotas);
        }else{
            throw MishapInvalidIota.of(new ListIota(iotas), 0, "hextrapats:set_list");
        }

        return asActionResult(out);
    }

    public DoubleSetIota toNumSet(List<Iota> iotas){
        DoubleSet set = new DoubleSet();
        for(int i = 0; i < iotas.size(); i++){
            if(iotas.get(i) instanceof DoubleIota d){
                set.add(d.getDouble());
            }else{
                throw MishapInvalidIota.of(new ListIota(iotas), 0, "hextrapats:set_list");
            }
        }
        return new DoubleSetIota(set);
    }

    public VecSetIota toVecSet(List<Iota> iotas){
        VecSet set = new VecSet();
        for(int i = 0; i < iotas.size(); i++){
            if(iotas.get(i) instanceof Vec3Iota v){
                set.add(v.getVec3());
            }else{
                throw MishapInvalidIota.of(new ListIota(iotas), 0, "hextrapats:set_list");
            }
        }
        return new VecSetIota(set);
    }

    public EntitySetIota toEntSet(List<Iota> iotas){
        Set<Entity> set = new HashSet<>();
        for(int i = 0; i < iotas.size(); i++){
            if(iotas.get(i) instanceof EntityIota e){
                set.add(e.getEntity());
            }else{
                throw MishapInvalidIota.of(new ListIota(iotas), 0, "hextrapats:set_list");
            }
        }
        return new EntitySetIota(set);
    }
}
