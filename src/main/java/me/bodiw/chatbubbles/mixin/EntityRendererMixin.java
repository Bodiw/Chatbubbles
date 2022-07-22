package me.bodiw.chatbubbles.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.bodiw.chatbubbles.api.Chatbubble;
import me.bodiw.chatbubbles.api.IChatbubble;
import me.bodiw.chatbubbles.client.renderer.ChatbubbleRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

	@Shadow
	private EntityRenderDispatcher dispatcher;

	@Shadow
	private TextRenderer textRenderer;

	@Inject(method = { "render" }, at = @At("HEAD"))
	private void injected(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {

		Chatbubble cb = ((IChatbubble) entity).getChatbubble();
		if (cb != null) {
			ChatbubbleRenderer.render(entity, cb.getLines(), matrices, vertexConsumers, light, dispatcher, textRenderer, cb.getWidth());
		}
	}
}