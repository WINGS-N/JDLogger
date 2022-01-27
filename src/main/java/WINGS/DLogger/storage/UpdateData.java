package WINGS.DLogger.storage;

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

    //GetJAR deprecated server (Korea)
    String deprecatedURL = "https://_disablelink_korea.wings.host/minecraft/plugins/" + UpdatePlugin + "/";

    //GetJAR server (GitHub)
    String URL = "https://github.com/WINGS07/JDLogger_releases/releases/latest/download/";
}
