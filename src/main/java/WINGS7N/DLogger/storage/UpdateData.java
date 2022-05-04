package WINGS7N.DLogger.storage;

import net.md_5.bungee.api.ChatColor;

public interface UpdateData {

    //Plugin
    String UpdatePlugin = SS.pl;
    String prefix = ChatColor.BLACK +
            "[" +
            ChatColor.LIGHT_PURPLE +
            SS.pl +
            ChatColor.BLACK +
            "]" +
            ChatColor.RESET +
            " ";

    //File ext
    String ext = ".jar";

    //GetJAR server (GitHub)
    String URL = "https://github.com/WINGS7N/JDLogger/releases/latest/download/";
    String deprecatedURL = "https://github.com/WINGS07/JDLogger/releases/latest/download/";
}
