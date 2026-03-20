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
import com.meepoffaith.hextrapats.casting.iota.SetIota;
import com.meepoffaith.hextrapats.util.HextraUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;

public class OpLastNToSet implements Action{
    @Override
    public @NotNull OperationResult operate(@NotNull CastingEnvironment ctx, @NotNull CastingImage img, @NotNull SpellContinuation cont) throws Mishap{
        List<Iota> stack = img.getStack();

        if(stack.isEmpty()) throw new MishapNotEnoughArgs(1, 0);

        int yoinkCount = OperatorUtils.getPositiveIntUnderInclusive(stack, stack.size() - 1, stack.size() - 1, stack.size());
        stack.remove(stack.size() - 1);
        SetIota out = new SetIota(new HashSet<>());
        for(int i = 0; i < yoinkCount; i++){
            out.add(stack.remove(stack.size() - 1));
        }
        stack.add(out);

        CastingImage img2 = HextraUtils.copyImage(img.withUsedOp(), stack);
        return new OperationResult(img2, List.of(), cont, HexEvalSounds.NORMAL_EXECUTE);
    }
}
