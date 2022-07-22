package me.bodiw.chatbubbles.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.bodiw.chatbubbles.api.Chatbubble;
import me.bodiw.chatbubbles.api.IChatbubble;
import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public class EntityMixin implements IChatbubble {

    private List<Chatbubble> chatbubbles = new ArrayList<>();

    public Chatbubble getChatbubble() {
        if (chatbubbles.isEmpty()) {
            return null;
        }
        return chatbubbles.get(0);
    }

    public List<Chatbubble> getChatbubbles() {
        return chatbubbles;
    }

    public void setChatbubble(int index, Chatbubble chatbubble) {
        chatbubbles.set(index, chatbubble);
    }

    public void queueChatBubble(Chatbubble chatbubble) {
        chatbubbles.add(chatbubble);
    }

    @Inject(method = "baseTick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    private void injected(CallbackInfo ci) {
        if (chatbubbles.isEmpty()) {
            return;
        }
        Chatbubble cb = chatbubbles.get(0);
        cb.tick();
        if (cb.getTicks() <= 0) {
            chatbubbles.remove(0);
        }
    }
}
