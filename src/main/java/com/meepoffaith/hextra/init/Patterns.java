package com.meepoffaith.hextra.init;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.OperationAction;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import com.meepoffaith.hextra.HextraPatterns;
import com.meepoffaith.hextra.casting.actions.MathActions.DegRad;
import com.meepoffaith.hextra.casting.actions.MathActions.RadDeg;
import com.meepoffaith.hextra.casting.actions.VecActions.VecNegOne;
import com.meepoffaith.hextra.casting.actions.VecActions.VecOne;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.meepoffaith.hextra.init.Arithmetics.*;

public class Patterns{
    private static final Map<Identifier, ActionRegistryEntry> PATTERNS = new LinkedHashMap<>();

    public static final HexPattern DEG_TO_RAD = make("deg_to_rad", "qqqqqdwdq", HexDir.WEST, new DegRad());
    public static final HexPattern RAD_TO_DEG = make("rad_to_deg", "qdwdqqqqq", HexDir.NORTH_EAST, new RadDeg());

    //Come on, Elise!
    public static final HexPattern VEC_ONE = make("haha_ha_one", "qqqqqeq", HexDir.NORTH_WEST, new VecOne());
    public static final HexPattern VEC_NEGONE = make("eno_ah_ahah", "eeeeeqq", HexDir.SOUTH_WEST, new VecNegOne());

    public static HexPattern ROT_ABOUT_X = make("rot_about_x", "aaqqqqqea", HexDir.SOUTH_WEST);
    public static HexPattern ROT_ABOUT_Y = make("rot_about_y", "aaqqqqqew", HexDir.SOUTH_WEST);
    public static HexPattern ROT_ABOUT_Z = make("rot_about_z", "aaqqqqqed", HexDir.SOUTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_X = make("cons_about_x", "daqqqqqea", HexDir.NORTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_Y = make("cons_about_y", "daqqqqqew", HexDir.NORTH_WEST);
    public static HexPattern CONSTRUCT_ABOUT_Z = make("cons_about_z", "daqqqqqed", HexDir.NORTH_WEST);
    public static HexPattern NORMALIZE = make("normalize", "eeeeedww", HexDir.SOUTH_WEST);

    public static void init(){
        for(Map.Entry<Identifier, ActionRegistryEntry> entry : PATTERNS.entrySet()){
            Registry.register(HexActions.REGISTRY, entry.getKey(), entry.getValue());
        }
    }

    private static HexPattern make(String name, String sig, HexDir dir, Action act){
        HexPattern pattern = HexPattern.fromAngles(sig, dir);
        PATTERNS.put(new Identifier(HextraPatterns.MOD_ID, name), new ActionRegistryEntry(pattern, act));
        return pattern;
    }

    private static HexPattern make(String name, String sig, HexDir dir){
        HexPattern pattern = HexPattern.fromAngles(sig, dir);
        PATTERNS.put(new Identifier(HextraPatterns.MOD_ID, name), new ActionRegistryEntry(pattern, new OperationAction(pattern)));
        return pattern;
    }
}
