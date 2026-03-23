package com.meepoffaith.hextrapats.casting.actions.eval;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.SpellList.LList;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.*;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation.NotDone;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import com.meepoffaith.hextrapats.util.HextraUtils;
import com.mojang.datafixers.util.Either;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OpConditionalEval implements Action{
    public boolean opCondition;

    public OpConditionalEval(boolean opCondition){
        this.opCondition = opCondition;
    }

    @Override
    public @NotNull OperationResult operate(@NotNull CastingEnvironment env, @NotNull CastingImage image, @NotNull SpellContinuation cont) throws Mishap{
        List<Iota> stack = image.getStack();
        if(stack.size() < 2) throw new MishapNotEnoughArgs(2, stack.size());
        Iota toRun = stack.remove(stack.size() - 1);
        boolean shouldEval = stack.remove(stack.size() - 1).isTruthy();

        if(shouldEval == opCondition){
            return exec(env, image, cont, stack, toRun);
        }else{
            var image2 = HextraUtils.copyImage(image.withUsedOp(), stack);
            return new OperationResult(image2, List.of(), cont, HexEvalSounds.SPELL);
        }
    }

    //Taken from OpEval
    private OperationResult exec(CastingEnvironment env, CastingImage image, SpellContinuation cont, List<Iota> newStack, Iota iota){
        Either<Iota, SpellList> instrs = OperatorUtils.evaluatable(iota, 0);
        SpellContinuation newCont;
        if(instrs.left().isPresent() || (cont instanceof SpellContinuation.NotDone n && n.getFrame() instanceof FrameFinishEval)){
            newCont = cont;
        }else{
            newCont = cont.pushFrame(FrameFinishEval.INSTANCE);
        }
        SpellList instrsList = instrs.map(it -> new LList(0, List.of(it)), it -> it);
        FrameEvaluate frame = new FrameEvaluate(instrsList, true);

        CastingImage image2 = HextraUtils.copyImage(image.withUsedOp(), newStack);
        return new OperationResult(image2, List.of(), newCont.pushFrame(frame), HexEvalSounds.HERMES);
    }
}
