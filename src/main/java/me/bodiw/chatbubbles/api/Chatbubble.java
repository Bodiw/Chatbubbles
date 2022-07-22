package me.bodiw.chatbubbles.api;

import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.resource.Resource;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

public class Chatbubble {

    List<OrderedText> lines;

    private int ticks;
    private boolean cancellable;
    private float width;

    Resource resource;

    public Chatbubble(Text text, int ticks, boolean cancellable) {
        this.ticks = ticks;
        this.cancellable = cancellable;
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        this.lines = ChatMessages.breakRenderedChatMessageLines(text, 150, tr);
        float width = 0;
        for (OrderedText ot : lines) {
            width = Math.max(width, tr.getWidth(ot) / 2);
        }
        this.width = width;
    }

    public List<OrderedText> getLines() {
        return lines;
    }

    public int getTicks() {
        return ticks;
    }

    public float getWidth() {
        return width;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public void tick() {
        ticks--;
    }

}
