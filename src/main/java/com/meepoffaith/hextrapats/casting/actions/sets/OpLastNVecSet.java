package com.meepoffaith.hextrapats.casting.actions.sets;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import com.meepoffaith.hextrapats.casting.iota.VecSetIota;
import com.meepoffaith.hextrapats.casting.iota.VecSetIota.VecSet;
import com.meepoffaith.hextrapats.util.HextraUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OpLastNVecSet implements Action{
    @Override
    public @NotNull OperationResult operate(@NotNull CastingEnvironment ctx, @NotNull CastingImage img, @NotNull SpellContinuation cont) throws Mishap{
        List<Iota> stack = img.getStack();

        if(stack.isEmpty()) throw new MishapNotEnoughArgs(1, 0);

        int yoinkCount = OperatorUtils.getPositiveIntUnderInclusive(stack, stack.size() - 1, stack.size() - 1, stack.size());
        VecSetIota out = new VecSetIota(new VecSet());
        for(int i = 0; i < yoinkCount; i++){
            out.add(OperatorUtils.getVec3(stack, stack.size() - 2 - i, stack.size()));
        }
        stack = stack.subList(0, stack.size() - 1 - yoinkCount);
        stack.add(out);

        CastingImage img2 = HextraUtils.copyImage(img.withUsedOp(), stack);
        return new OperationResult(img2, List.of(), cont, HexEvalSounds.NORMAL_EXECUTE);
    }
}
