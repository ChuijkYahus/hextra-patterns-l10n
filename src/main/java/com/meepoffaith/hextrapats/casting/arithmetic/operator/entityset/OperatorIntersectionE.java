package com.meepoffaith.hextrapats.casting.arithmetic.operator.entityset;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota;
import com.meepoffaith.hextrapats.util.MultiPreds;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.meepoffaith.hextrapats.init.IotaTypes.ENTITY_SET;

public class OperatorIntersectionE extends OperatorBase{
    public OperatorIntersectionE(){
        super(2, MultiPreds.all(ENTITY_SET));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        Set<Entity> set1 = stack.getEntitySet(0).getSet();
        Set<Entity> set2 = stack.getEntitySet(1).getSet();
        HashSet<Entity> intersection = new HashSet<>();
        for(Entity key : set1){
            if(set2.contains(key)) intersection.add(key);
        }
        return asActionResult(new EntitySetIota(intersection));
    }
}
