package me.bodiw.chatbubbles.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.bodiw.chatbubbles.api.Chatbubble;
import me.bodiw.chatbubbles.api.Chatbubbles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Shadow
    private MinecraftClient client;

    @Inject(method = "onGameMessage(Lnet/minecraft/network/packet/s2c/play/GameMessageS2CPacket;)V", at = @At("TAIL"))
    private void addChatBubble(GameMessageS2CPacket packet, CallbackInfo ci) {
        if (packet.getType() != MessageType.CHAT) {
            return;
        }
        Entity player = client.world.getPlayerByUuid(packet.getSender());
        if (player != null) {
            Text text = packet.getMessage();
            String str = text.getString().substring(0, Math.min(text.getString().length(), 20));
            if (str.contains(" ")) {
                text = new LiteralText(text.getString().substring(str.indexOf(" ") + 1));
            }
            Chatbubbles.setChatbubble(player, new Chatbubble(text, Math.min(Math.max(60, text.getString().length() * 8), 400), true));
        }
    }
}
