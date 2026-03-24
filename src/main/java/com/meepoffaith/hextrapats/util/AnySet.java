package com.meepoffaith.hextrapats.util;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota;
import com.meepoffaith.hextrapats.casting.iota.VecSetIota;
import com.meepoffaith.hextrapats.util.generics.Func1to1;

import java.util.List;

/** Handles set type validation for me. */
public class AnySet{
    public DoubleSetIota doubleSetIota;
    public VecSetIota vecSetIota;
    public EntitySetIota entitySetIota;

    public AnySet(Iota iota, int reversedIdx){
        if(iota instanceof DoubleSetIota d){
            this.doubleSetIota = d;
        }else if(iota instanceof VecSetIota v){
            this.vecSetIota = v;
        }else if(iota instanceof EntitySetIota e){
            this.entitySetIota = e;
        }else{
            throw MishapInvalidIota.ofType(iota, reversedIdx, "set");
        }
    }

    public List<Iota> operate(
        Func1to1<DoubleSetIota, List<Iota>> doubles,
        Func1to1<VecSetIota, List<Iota>> vecs,
        Func1to1<EntitySetIota, List<Iota>> entitySets
    ){
        if(doubleSetIota != null){
            return doubles.apply(doubleSetIota);
        }else if(vecSetIota != null){
            return vecs.apply(vecSetIota);
        }else if(entitySetIota != null){
            return entitySets.apply(entitySetIota);
        }else{
            throw new IllegalStateException("how did we get here");
        }
    }
}
