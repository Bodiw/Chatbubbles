package me.bodiw.chatbubbles.api;

import java.util.List;

import net.minecraft.entity.Entity;

public class Chatbubbles {

    public static void setChatbubble(Entity entity, Chatbubble chatbubble) {
        IChatbubble e2 = (IChatbubble) entity;
        if (e2.getChatbubbles().size() > 0) {
            e2.setChatbubble(0, chatbubble);
        } else {
            e2.queueChatBubble(chatbubble);
        }
    }

    public static void setChatbubble(Entity entity, int index, Chatbubble chatbubble) {
        ((IChatbubble) entity).setChatbubble(0, chatbubble);
    }

    public static void queueChatBubble(Entity entity, Chatbubble chatbubble) {
        ((IChatbubble) entity).queueChatBubble(chatbubble);
    }

    public static List<Chatbubble> getChatbubbles(Entity entity) {
        return ((IChatbubble) entity).getChatbubbles();
    }
}
