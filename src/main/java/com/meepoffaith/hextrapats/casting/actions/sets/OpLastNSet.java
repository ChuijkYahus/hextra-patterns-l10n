package com.meepoffaith.hextrapats.casting.actions.sets;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.EntityIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota.DoubleSet;
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota;
import com.meepoffaith.hextrapats.casting.iota.VecSetIota;
import com.meepoffaith.hextrapats.casting.iota.VecSetIota.VecSet;
import com.meepoffaith.hextrapats.util.HextraUtils;

import java.util.HashSet;
import java.util.List;

public class OpLastNSet extends ConstMediaActionBase{
    @Override
    public OperationResult operate(CastingEnvironment env, CastingImage img, SpellContinuation cont){
        List<Iota> stack = img.getStack();

        if(stack.size() < 2) throw new MishapNotEnoughArgs(2, stack.size());

        int yoinkCount = OperatorUtils.getPositiveIntUnderInclusive(stack, stack.size() - 1, stack.size() - 1, stack.size());
        Iota start = stack.get(stack.size() - 2);
        Iota out;
        if(start instanceof DoubleIota){
            out = bucketNums(stack, yoinkCount);
        }else if(start instanceof Vec3Iota){
            out = bucketVecs(stack, yoinkCount);
        }else if(start instanceof EntityIota){
            out = bucketEnts(stack, yoinkCount);
        }else{
            throw MishapInvalidIota.of(start, 2, "hextrapats:not_set_item");
        }

        stack = stack.subList(0, stack.size() - 1 - yoinkCount);
        stack.add(out);

        CastingImage img2 = HextraUtils.copyImage(img.withUsedOp(), stack);
        return new OperationResult(img2, List.of(), cont, HexEvalSounds.NORMAL_EXECUTE);
    }

    public DoubleSetIota bucketNums(List<Iota> stack, int yoinkCount){
        DoubleSetIota out = new DoubleSetIota(new DoubleSet());
        for(int i = 0; i < yoinkCount; i++){
            out.add(OperatorUtils.getDouble(stack, stack.size() - 2 - i, stack.size()));
        }
        return out;
    }

    public VecSetIota bucketVecs(List<Iota> stack, int yoinkCount){
        VecSetIota out = new VecSetIota(new VecSet());
        for(int i = 0; i < yoinkCount; i++){
            out.add(OperatorUtils.getVec3(stack, stack.size() - 2 - i, stack.size()));
        }
        return out;
    }

    public EntitySetIota bucketEnts(List<Iota> stack, int yoinkCount){
        EntitySetIota out = new EntitySetIota(new HashSet<>());
        for(int i = 0; i < yoinkCount; i++){
            out.add(OperatorUtils.getEntity(stack, stack.size() - 2 - i, stack.size()));
        }
        return out;
    }
}
