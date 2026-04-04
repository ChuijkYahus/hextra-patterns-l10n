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

/** Replica of FrameForEach, but additionally adds the current index to the stack. */
public class FrameIndexForEach implements ContinuationFrame{
    SpellList data;
    SpellList code;
    List<Iota> baseStack;
    int index;
    ArrayList<Iota> acc;

    public FrameIndexForEach(SpellList data, SpellList code, List<Iota> baseStack, int index, ArrayList<Iota> acc){
        this.data = data;
        this.code = code;
        this.baseStack = baseStack;
        this.index = index;
        this.acc = acc;
    }

    public FrameIndexForEach(SpellList data, SpellList code){
        this(data, code, null, 0, new ArrayList<>());
    }

    @Override
    public @NotNull CastResult evaluate(@NotNull SpellContinuation cont, @NotNull ServerWorld level, @NotNull CastingVM harness){
        List<Iota> stack;
        // If this isn't the very first Thoth step (i.e. no Thoth computations run yet)...
        if(baseStack == null){
            // init stack to the VM stack...
            stack = harness.getImage().getStack();
        }else{
            // else save the stack to the accumulator and reuse the saved base stack.
            acc.addAll(harness.getImage().getStack());
            stack = baseStack;
        }

        // If we still have data to process...
        List<Iota> tStack = new ArrayList<>(stack);
        CastingImage newImage;
        SpellContinuation newCont;
        if(data.getNonEmpty()){
            // push the next datum to the top of the stack,
            var cont2 = cont
                // put the next Thoth object back on the stack for the next Thoth cycle,
                .pushFrame(new FrameIndexForEach(data.getCdr(), code, stack, index + 1, acc))
                // and prep the Thoth'd code block for evaluation.
                .pushFrame(new FrameEvaluate(code, true));
            tStack.add(data.getCar());
            tStack.add(new DoubleIota(index));
            newImage = harness.getImage().withUsedOp();
            newCont = cont2;
        }else{
            // Else, dump our final list onto the stack.
            tStack.add(new ListIota(acc));
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

    /** When halting, we add the stack state at halt to the stack accumulator, then return the original pre-Thoth stack, plus the accumulator. */
    @Override
    public @NotNull Pair<Boolean, List<Iota>> breakDownwards(@NotNull List<? extends Iota> stack){
        List<Iota> newStack = new ArrayList<>(baseStack != null ? List.copyOf(baseStack) : List.of());
        acc.addAll(stack);
        newStack.add(new ListIota(acc));
        return new Pair<>(true, newStack);
    }

    @Override
    public @NotNull NbtCompound serializeToNBT(){
        var tag = new NbtCompound();
        tag.put("data", HexUtils.serializeToNBT(data));
        tag.put("code", HexUtils.serializeToNBT(code));
        if(baseStack != null){
            tag.put("base", HexUtils.serializeToNBT(baseStack));
        }
        tag.putInt("index", index);
        tag.put("accumulator", HexUtils.serializeToNBT(acc));
        return tag;
    }

    @Override
    public int size(){
        return data.size() + code.size() + acc.size() + (baseStack == null ? 0 : baseStack.size());
    }

    @Override
    public @NotNull Type<?> getType(){
        return TYPE;
    }

    public static ContinuationFrame.Type<FrameIndexForEach> TYPE = (tag, world) -> {
        List<Iota> baseStack = null;
        if(tag.contains("base")){
            baseStack = HextraUtils.handroll(HexIotaTypes.LIST.deserialize(tag.getList("base", NbtElement.COMPOUND_TYPE), world).getList());
        }

        return new FrameIndexForEach(
            HexIotaTypes.LIST.deserialize(tag.getList("data", NbtElement.COMPOUND_TYPE), world).getList(),
            HexIotaTypes.LIST.deserialize(tag.getList("code", NbtElement.COMPOUND_TYPE), world).getList(),
            baseStack,
            tag.getInt("index"),
            HextraUtils.handroll(HexIotaTypes.LIST.deserialize(tag.getList("accumulator", NbtElement.COMPOUND_TYPE), world).getList())
        );
    };
}
