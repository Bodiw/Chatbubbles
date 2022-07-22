package me.bodiw.chatbubbles.api;

import java.util.List;

public interface IChatbubble {

    public Chatbubble getChatbubble();

    public void setChatbubble(int index, Chatbubble chatbubble);

    public void queueChatBubble(Chatbubble chatbubble);

    public List<Chatbubble> getChatbubbles();
}
