package com.meepoffaith.hextrapats.init;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexArithmetics;
import com.meepoffaith.hextrapats.HextraPatterns;
import com.meepoffaith.hextrapats.casting.arithmetic.Vec3Arithmetic;
import com.meepoffaith.hextrapats.casting.arithmetic.Vec3BoolArithmetic;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Arithmetics{
    public static HexPattern LEN_EQ = Patterns.LEN_EQ.prototype();
    public static HexPattern LEN_NEQ = Patterns.LEN_NEQ.prototype();
    public static HexPattern IN_RANGE = Patterns.IN_RANGE.prototype();
    public static HexPattern OUT_RANGE = Patterns.OUT_RANGE.prototype();

    public static void init(){
        registerArithmetic("vec3bool", new Vec3BoolArithmetic());
        registerArithmetic("vec3math", new Vec3Arithmetic());
    }

    private static void registerArithmetic(
        String name,
        Arithmetic a
    ){
        Registry.register(HexArithmetics.REGISTRY,  new Identifier(HextraPatterns.MOD_ID, name), a);
    }
}
