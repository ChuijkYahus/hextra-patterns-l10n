package com.meepoffaith.hextrapats;

import com.meepoffaith.hextrapats.init.Arithmetics;
import com.meepoffaith.hextrapats.init.IotaTypes;
import com.meepoffaith.hextrapats.init.Patterns;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HextraPatterns implements ModInitializer {
	public static final String MOD_ID = "hextrapats";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Is anyone there? Hello? I'm trAPPED IN HERE AND CAN'T GET OUT H E L P !");

		IotaTypes.init();
		Patterns.init();
		Arithmetics.init();
	}

	public static Identifier modLoc(String name){
		return new Identifier(MOD_ID, name);
	}
}
