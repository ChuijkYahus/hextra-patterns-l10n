package com.meepoffaith.hextrapats.casting.bases;

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class OperatorBase extends OperatorBasic{
    public OperatorBase(int arity, @NotNull IotaMultiPredicate accepts){
        super(arity, accepts);
    }

    public abstract @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx);

    @Override
    public @NotNull Iterable<Iota> apply(@NotNull Iterable<? extends Iota> itr, @NotNull CastingEnvironment ctx) throws Mishap{
        return operate(new HexIotaStack(itr, ctx), ctx);
    }

    protected <AnyIota extends Iota> List<AnyIota> asActionResult(AnyIota iota) {
        return List.of(iota);
    }
}
