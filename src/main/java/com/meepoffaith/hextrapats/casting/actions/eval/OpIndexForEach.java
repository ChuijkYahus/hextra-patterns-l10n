package com.meepoffaith.hextrapats.casting.actions.eval;

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
import com.meepoffaith.hextrapats.casting.eval.vm.FrameIndexForEach;
import com.meepoffaith.hextrapats.util.HextraUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OpIndexForEach implements Action{
    @Override
    public @NotNull OperationResult operate(@NotNull CastingEnvironment ctx, @NotNull CastingImage image, @NotNull SpellContinuation cont) throws Mishap{
        List<Iota> stack = image.getStack();

        if(stack.size() < 2) throw new MishapNotEnoughArgs(2, stack.size());

        var instrs = OperatorUtils.getList(stack, stack.size() - 2, stack.size());
        var datums = OperatorUtils.getList(stack, stack.size() - 1, stack.size());
        stack.remove(stack.size() - 1);
        stack.remove(stack.size() - 1);

        var frame = new FrameIndexForEach(datums, instrs);
        var image2 = HextraUtils.copyImage(image.withUsedOp(), stack);

        return new OperationResult(image2, List.of(), cont.pushFrame(frame), HexEvalSounds.THOTH);
    }
}
