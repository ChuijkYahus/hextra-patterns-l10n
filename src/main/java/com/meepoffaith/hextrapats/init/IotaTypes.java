package com.meepoffaith.hextrapats.init;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.meepoffaith.hextrapats.HextraPatterns;
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota;
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota;
import com.meepoffaith.hextrapats.casting.iota.VecSetIota;
import net.minecraft.registry.Registry;

public class IotaTypes{
    public static final IotaType<DoubleSetIota> NUM_SET = type("num_set", DoubleSetIota.TYPE);
    public static final IotaType<VecSetIota> VEC_SET = type("vec_set", VecSetIota.TYPE);
    public static final IotaType<EntitySetIota> ENTITY_SET = type("entity_set", EntitySetIota.TYPE);

    public static void init(){}

    private static <U extends Iota, T extends IotaType<U>> T type(String name, T type) {
        return Registry.register(IXplatAbstractions.INSTANCE.getIotaTypeRegistry(), HextraPatterns.modLoc(name), type);
    }
}
