package com.meepoffaith.hextrapats.init;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import com.meepoffaith.hextrapats.HextraPatterns;
import com.meepoffaith.hextrapats.casting.iota.SetIota;
import net.minecraft.registry.Registry;

public class IotaTypes{
    public static final IotaType<SetIota> SET = type("set", SetIota.TYPE);

    private static <U extends Iota, T extends IotaType<U>> T type(String name, T type) {
        return Registry.register(IXplatAbstractions.INSTANCE.getIotaTypeRegistry(), HextraPatterns.modLoc(name), type);
    }
}
