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

public class OperatorRemoveE extends OperatorBase{
    boolean returnBool;

    public OperatorRemoveE(boolean returnBool){
        super(2, MultiPreds.pair(ENTITY_SET, ENTITY));
        this.returnBool = returnBool;
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        EntitySetIota set = stack.getEntitySet(0);
        Entity entity = stack.getEntity(1);
        boolean removed = set.remove(entity);
        return returnBool ? List.of(set, new BooleanIota(removed)) : asActionResult(set);
    }
}
