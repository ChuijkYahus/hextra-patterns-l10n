package com.meepoffaith.hextrapats

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import com.meepoffaith.hextrapats.init

object Hextrapats : ModInitializer {
    private val logger = LoggerFactory.getLogger("hextrapats")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")

		Patterns.init();
	}
}
