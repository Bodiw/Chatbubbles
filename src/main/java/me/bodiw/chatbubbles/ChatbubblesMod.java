package me.bodiw.chatbubbles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public class ChatbubblesMod implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("chatbubbles");

	@Override
	public void onInitialize() {
		LOGGER.info("Bubbling up the world");
	}

}
