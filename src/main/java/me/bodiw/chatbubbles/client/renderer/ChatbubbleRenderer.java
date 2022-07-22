package me.bodiw.chatbubbles.client.renderer;

import java.util.List;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

public class ChatbubbleRenderer {

    public static final SpriteIdentifier CHATBUBBLE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("chatbubbles", "misc/bubble_0"));

    public static void render(Entity entity, List<OrderedText> lines, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderDispatcher erd, TextRenderer tr, float maxWidth) {

        double d = erd.getSquaredDistanceToCamera(entity);
        if (d > 4096.0) {
            return;
        }
        if (entity.isInvisible() || entity.isSneaking()) {
            return;
        }

        int l = 0;

        for (OrderedText line : lines) {
            renderText(entity, line, matrices, vertexConsumers, light, erd, tr, -lines.size() * 4 + l * 9);
            l++;
        }

        renderChatbubble(matrices, vertexConsumers, entity, erd, maxWidth / 16);

    }

    private static void renderText(Entity entity, OrderedText text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderDispatcher erd, TextRenderer tr, float offset) {
        float f = entity.getHeight() + 1.0f;
        float h = -tr.getWidth(text) / 2;
        matrices.push();
        matrices.translate(-0.01, f, -0.01);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-erd.camera.getYaw()));
        matrices.scale(-0.025f, -0.025f, 0.025f);
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        tr.draw(text, h, offset, 0xFFFFFFFF, true, matrix4f, vertexConsumers, false, 0, light);
        matrices.pop();
    }

    private static void renderChatbubble(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity, EntityRenderDispatcher erd, float textWidth) {
        Sprite sprite = CHATBUBBLE.getSprite();
        float height = entity.getHeight() + 0.1f;
        textWidth = Math.max(textWidth, entity.getWidth() * 1.61f);
        // Size up the chatbubble for the text
        matrices.push();
        matrices.scale(textWidth, 1.0f, textWidth);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-erd.camera.getYaw()));
        matrices.translate(0.01, height, 0.01);
        // Rotate the chatbubble and offset if to fix z-fighting

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(TexturedRenderLayers.getEntityTranslucentCull());
        MatrixStack.Entry entry = matrices.peek();

        // Actual rendering or something
        float m = sprite.getMaxU();
        float n = sprite.getMinV();
        float o = sprite.getMinU();
        float p = sprite.getMaxV();

        ChatbubbleRenderer.drawChatbubbleVertex(entry, vertexConsumer, 0.5f, 0.0f, 0.0f, o, p);
        ChatbubbleRenderer.drawChatbubbleVertex(entry, vertexConsumer, -0.5f, 0.0f, 0.0f, m, p);
        ChatbubbleRenderer.drawChatbubbleVertex(entry, vertexConsumer, -0.5f, 1.4f, 0.0f, m, n);
        ChatbubbleRenderer.drawChatbubbleVertex(entry, vertexConsumer, 0.5f, 1.4f, 0.0f, o, n);

        matrices.pop();
    }

    private static void drawChatbubbleVertex(MatrixStack.Entry entry, VertexConsumer vertices, float x, float y, float z, float u, float v) {
        vertices.vertex(entry.getPositionMatrix(), x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(0, 10).light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE).normal(entry.getNormalMatrix(), 0.0f, 1.0f, 0.0f).next();
    }
}
