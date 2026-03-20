package com.meepoffaith.hextrapats.casting.arithmetic.operator.set;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.SetIota;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.meepoffaith.hextrapats.init.IotaTypes.SET;

public class OperatorInsert extends OperatorBase{
    boolean returnBool;

    public OperatorInsert(boolean returnBool){
        super(2, IotaMultiPredicate.pair(IotaPredicate.ofType(SET), IotaPredicate.TRUE));
        this.returnBool = returnBool;
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        SetIota set = stack.getSet(0);
        Iota iota = stack.get(1);
        boolean added = set.add(iota);
        return returnBool ? List.of(set, new BooleanIota(added)) : asActionResult(set);
    }
}
