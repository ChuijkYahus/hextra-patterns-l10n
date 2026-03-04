package com.meepoffaith.hextrapats.casting.arithmetic.operator;

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.util.QuadaryOperator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OperatorQuadary extends OperatorBasic{
    public QuadaryOperator<Iota> inner;

	public OperatorQuadary(IotaMultiPredicate accepts, QuadaryOperator<Iota> inner) {
        super(4, accepts);
        this.inner = inner;
    }

    @Override
    public @NotNull Iterable<Iota> apply(Iterable<? extends Iota> iotas, @NotNull CastingEnvironment env) {
        var it = iotas.iterator();
        return List.of(inner.apply(it.next(), it.next(), it.next(), it.next()));
    }
}
