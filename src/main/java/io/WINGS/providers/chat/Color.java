package io.WINGS.providers.chat;

import net.md_5.bungee.api.ChatColor;

public interface Color {
    static String translate(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
