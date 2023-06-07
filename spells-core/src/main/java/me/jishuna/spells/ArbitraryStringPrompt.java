package me.jishuna.spells;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class ArbitraryStringPrompt extends StringPrompt {

    @Override
    public String getPromptText(ConversationContext context) {
        return "test";
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        context.setSessionData("message", input);
        return Prompt.END_OF_CONVERSATION;
    }

}
