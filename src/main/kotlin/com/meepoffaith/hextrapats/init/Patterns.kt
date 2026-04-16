package com.meepoffaith.hextrapats.init

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.castables.OperationAction
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.hex.HexActions
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hextrapats.HextraPats
import com.meepoffaith.hextrapats.casting.actions.NoConsOperationAction
import com.meepoffaith.hextrapats.casting.actions.eval.OpConditionalEval
import com.meepoffaith.hextrapats.casting.actions.eval.OpConditionalHalt
import net.minecraft.registry.Registry

object Patterns {
    /*
    val ROT_ABOUT_X = register("rot_about_x", "aaqqqqqea", HexDir.SOUTH_WEST)
    val ROT_ABOUT_Y = register("rot_about_x", "aaqqqqqew", HexDir.SOUTH_WEST)
    val ROT_ABOUT_Z = register("rot_about_x", "aaqqqqqed", HexDir.SOUTH_WEST)
    val CONSTRUCT_ABOUT_X = register("cons_about_x", "daqqqqqea", HexDir.NORTH_WEST)
    val CONSTRUCT_ABOUT_Y = register("cons_about_y", "daqqqqqew", HexDir.NORTH_WEST)
    val CONSTRUCT_ABOUT_Z = register("cons_about_z", "daqqqqqed", HexDir.NORTH_WEST)
    val NORMALIZE = register("normalize", "eeeeedww", HexDir.SOUTH_WEST)
    val LEN_EQ = register("len_eq", "adqqaqw", HexDir.EAST)
    val LEN_NEQ = register("len_neq", "daeedew", HexDir.EAST)
    val IN_RANGE = register("in_range", "qqqq", HexDir.SOUTH_WEST)
    val OUT_RANGE = register("out_range", "eaae", HexDir.SOUTH_EAST)
    val INVERT = register("invert", "waqawqa", HexDir.SOUTH_WEST)
    val INCREMENT = register("increment", "waawawaaw", HexDir.NORTH_EAST)
    val DECREMENT = register("decrement", "wddwdwddw", HexDir.NORTH_WEST)
    val APPROACH = register("approach", "dedqadeeed", HexDir.SOUTH_WEST)
    val ANGLE_DIST = register("angle_dist", "awdaqqqqqea", HexDir.NORTH_EAST)
    val ANGLE_APPROACH = register("angle_approach", "awdaqqqqqwd", HexDir.NORTH_EAST)
    val SET_INSERT_RET = register("set_insert_ret", "edqdewd", HexDir.SOUTH_WEST )
    val SET_REMOVE_RET = register("set_remove_ret", "edqdewaqaaed", HexDir.SOUTH_WEST)
     */

    fun init(){
        register("true_eval", "deaqqaaqa", HexDir.SOUTH_EAST, OpConditionalEval(true))
        register("false_eval", "deaqqdded", HexDir.SOUTH_EAST, OpConditionalEval(false))
        register("true_halt", "aqdeedded", HexDir.SOUTH_WEST, OpConditionalHalt(true))
        register("false_halt", "aqdeeaaqa", HexDir.SOUTH_WEST, OpConditionalHalt(false))
    }

    private fun register(name: String, signature: String, startDir: HexDir, action: Action){
        Registry.register(HexActions.REGISTRY, HextraPats.modLoc(name),
            ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action)
        )
    }

    private fun register(name: String, signature: String, startDir: HexDir): HexPattern {
        val pattern = HexPattern.fromAngles(signature, startDir)
        Registry.register(HexActions.REGISTRY, HextraPats.modLoc(name),
            ActionRegistryEntry(pattern, OperationAction(pattern))
        )
        return pattern
    }

    private fun <T : SpecialHandler> registerSpecialHandler(name: String, handler: SpecialHandler.Factory<T>){
        Registry.register(IXplatAbstractions.INSTANCE.specialHandlerRegistry, HextraPats.modLoc(name), handler);
    }

    private fun registerNoConsumeOp(name: String, signature: String, startDir: HexDir, copied: HexPattern, argc: Int){
        val pattern = HexPattern.fromAngles(signature, startDir)
        Registry.register(HexActions.REGISTRY, HextraPats.modLoc(name),
            ActionRegistryEntry(pattern, NoConsOperationAction(pattern, argc))
        )
    }
}
