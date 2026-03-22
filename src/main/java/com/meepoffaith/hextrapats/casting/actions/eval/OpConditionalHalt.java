package com.meepoffaith.hextrapats.casting.actions.eval;

import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import com.meepoffaith.hextrapats.util.HextraUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OpConditionalHalt implements Action{
    public boolean haltCondition;

    public OpConditionalHalt(boolean haltCondition){
        this.haltCondition = haltCondition;
    }

    @Override
    public @NotNull OperationResult operate(@NotNull CastingEnvironment ctx, @NotNull CastingImage img, @NotNull SpellContinuation cont) throws Mishap{
        var newStack = img.getStack();
        if(newStack.isEmpty()) throw new MishapNotEnoughArgs(1, 0);

        var newCont = cont;

        boolean bool = newStack.remove(newStack.size() - 1).isTruthy();
        if(bool == haltCondition){
            //Halting code copied from OpHalt (Charon)
            boolean done = false;
            while(!done && newCont instanceof SpellContinuation.NotDone n){
                var newInfo = n.getFrame().breakDownwards(newStack);
                done = newInfo.getFirst();
                newStack = newInfo.getSecond();
                newCont = n.getNext();
            }
            if(!done){
                newStack = List.of();
            }
        }

        CastingImage img2 = HextraUtils.copyImage(img.withUsedOp(), newStack);
        return new OperationResult(img2, List.of(), newCont, HexEvalSounds.SPELL);
    }
}
