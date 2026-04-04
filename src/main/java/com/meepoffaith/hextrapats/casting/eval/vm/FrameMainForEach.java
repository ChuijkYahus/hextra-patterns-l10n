package com.meepoffaith.hextrapats.casting.eval.vm;

import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.eval.CastResult;
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType;
import at.petrak.hexcasting.api.casting.eval.vm.*;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.utils.HexUtils;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import com.meepoffaith.hextrapats.util.HextraUtils;
import kotlin.Pair;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FrameMainForEach implements ContinuationFrame{
    SpellList data;
    SpellList code;
    int index;

    public FrameMainForEach(SpellList data, SpellList code, int index){
        this.data = data;
        this.code = code;
        this.index = index;
    }

    public FrameMainForEach(SpellList data, SpellList code, boolean withIndex){
        this(data, code, withIndex ? 0 : -1);
    }

    @Override
    public @NotNull CastResult evaluate(@NotNull SpellContinuation cont, @NotNull ServerWorld level, @NotNull CastingVM harness){
        List<Iota> stack = harness.getImage().getStack(); //Always use the current stack.

        List<Iota> tStack = new ArrayList<>(stack);
        CastingImage newImage;
        SpellContinuation newCont;
        if(data.getNonEmpty()){
            // push the next datum to the top of the stack,
            var cont2 = cont
                // put the next Thoth object back on the stack for the next Thoth cycle,
                .pushFrame(new FrameMainForEach(data.getCdr(), code, index == -1 ? -1 : index + 1))
                // and prep the Thoth'd code block for evaluation.
                .pushFrame(new FrameEvaluate(code, true));
            tStack.add(data.getCar());
            if(index != -1) tStack.add(new DoubleIota(index));
            newImage = harness.getImage().withUsedOp();
            newCont = cont2;
        }else{
            // Else, dump our final list onto the stack.
            newImage = harness.getImage();
            newCont = cont;
        }

        return new CastResult(
            new ListIota(code),
            newCont,
            // reset escapes so they don't carry over to other iterations or out of thoth
            HextraUtils.copyImage(newImage.withResetEscape(), tStack),
            List.of(),
            ResolvedPatternType.EVALUATED,
            HexEvalSounds.THOTH
        );
    }

    @Override
    public @NotNull Pair<Boolean, List<Iota>> breakDownwards(@NotNull List<? extends Iota> stack){
        return new Pair<>(true, new ArrayList<>(stack));
    }

    @Override
    public @NotNull NbtCompound serializeToNBT(){
        var tag = new NbtCompound();
        tag.put("data", HexUtils.serializeToNBT(data));
        tag.put("code", HexUtils.serializeToNBT(code));
        tag.putInt("index", index);
        return tag;
    }

    @Override
    public int size(){
        return data.size() + code.size();
    }

    @Override
    public @NotNull Type<?> getType(){
        return TYPE;
    }

    public static ContinuationFrame.Type<FrameMainForEach> TYPE = (tag, world) -> new FrameMainForEach(
        HexIotaTypes.LIST.deserialize(tag.getList("data", NbtElement.COMPOUND_TYPE), world).getList(),
        HexIotaTypes.LIST.deserialize(tag.getList("code", NbtElement.COMPOUND_TYPE), world).getList(),
        tag.getInt("index")
    );
}
