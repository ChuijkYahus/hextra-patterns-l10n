package com.meepoffaith.hextrapats.init;

import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.meepoffaith.hextrapats.HextraPatterns;
import com.meepoffaith.hextrapats.casting.eval.vm.FrameIndexForEach;
import com.meepoffaith.hextrapats.casting.eval.vm.FrameMainForEach;
import net.minecraft.registry.Registry;

public class ContinuationTypes{
    public static final ContinuationFrame.Type<FrameIndexForEach> INDEX_FOREACH = continuation("index_foreach", FrameIndexForEach.TYPE);
    public static final ContinuationFrame.Type<FrameMainForEach> MAIN_FOREACH = continuation("main_foreach", FrameMainForEach.TYPE);

    public static void init(){}

    private static <U extends ContinuationFrame, T extends ContinuationFrame.Type<U>> T continuation(String name, T continuation){
        return Registry.register(IXplatAbstractions.INSTANCE.getContinuationTypeRegistry(), HextraPatterns.modLoc(name), continuation);
    }
}
