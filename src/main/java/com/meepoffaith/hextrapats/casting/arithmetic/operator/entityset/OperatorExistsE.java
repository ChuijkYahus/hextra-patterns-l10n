package com.meepoffaith.hextrapats.casting.arithmetic.operator.entityset;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota;
import com.meepoffaith.hextrapats.util.MultiPreds;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.ENTITY;
import static com.meepoffaith.hextrapats.init.IotaTypes.ENTITY_SET;

public class OperatorExistsE extends OperatorBase{
    public OperatorExistsE(){
        super(2, MultiPreds.pair(ENTITY_SET, ENTITY));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        EntitySetIota set = stack.getEntitySet(0);
        Entity entity = stack.getEntity(1);
        boolean found = set.contains(entity);
        return List.of(set, new BooleanIota(found));
    }
}
