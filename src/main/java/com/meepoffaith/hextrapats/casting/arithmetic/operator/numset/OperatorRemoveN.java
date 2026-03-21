package com.meepoffaith.hextrapats.casting.arithmetic.operator.numset;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.meepoffaith.hextrapats.init.IotaTypes.NUM_SET;

public class OperatorRemoveN extends OperatorBase{
    boolean returnBool;

    public OperatorRemoveN(boolean returnBool){
        super(2, IotaMultiPredicate.pair(IotaPredicate.ofType(NUM_SET), IotaPredicate.TRUE));
        this.returnBool = returnBool;
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        DoubleSetIota set = stack.getNumSet(0);
        double iota = stack.getDouble(1);
        boolean removed = set.remove(iota);
        return returnBool ? List.of(set, new BooleanIota(removed)) : asActionResult(set);
    }
}
