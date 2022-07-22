package me.bodiw.chatbubbles.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;

public class ChatbubblesModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        
        // Bubble sprites
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlas, registry) -> {
            registry.register(new Identifier("chatbubbles", "misc/bubble_0"));
        });

    }

}
