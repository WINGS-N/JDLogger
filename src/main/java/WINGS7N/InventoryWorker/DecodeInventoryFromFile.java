package WINGS7N.InventoryWorker;

import WINGS7N.DLogger.storage.SS;
import WINGS7N.DLogger.storage.debug;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class DecodeInventoryFromFile {

    //public String InvDataCoded = "DATA";

    Logger log = Bukkit.getLogger();

    Plugin plugin = Bukkit.getPluginManager().getPlugin("JDLogger");

    public DecodeInventoryFromFile(CommandSender s, String args) {
        if (s instanceof Player) {
            Player p = (Player) s;
            if (p.hasPermission(SS.checkPerm)) {
                if (debug.get()) {
                    log.info(SS.DetectedArgs + args);
                }

                String InvDataFileName = SS.DLPath + args + SS.DLInvPath + args + SS.DotInv;
                String ArmorDataFileName = SS.DLPath + args + SS.DLArmorPath + args + SS.DotInv;

                if (debug.get()) {
                    log.info(SS.InvSavedIn + InvDataFileName);
                    log.info(SS.ArmorSavedIn + ArmorDataFileName);
                }

                ItemStack[] InvDataDecoded;
                ItemStack[] ArmorDataDecoded;

                if (debug.get()) {
                    log.info(SS.SettingIS);
                }

                try {
                    BufferedReader br = new BufferedReader(new FileReader(InvDataFileName));
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }

                    String InvDataCoded = sb.toString();

                    if (debug.get()) {
                        log.info(SS.InvDataCV + InvDataCoded);
                    }

                    br.close();

                    if (debug.get()) {
                        log.info(SS.Decoding);
                    }
                    InvDataDecoded = ItemSerialization.itemStackArrayFromBase64(InvDataCoded);

                    if (debug.get()) {
                        log.info(SS.SendingInvData + s.getName());
                    }
                    p.getInventory().setContents(InvDataDecoded);

                } catch (IOException exc) {
                    if (debug.get()) {
                        exc.printStackTrace();
                    }
                    p.sendMessage(SS.cantFind + args + SS.DotInv);
                    p.sendMessage(SS.Space);
                }

                try {
                    BufferedReader br = new BufferedReader(new FileReader(ArmorDataFileName));
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }

                    String ArmorDataCoded = sb.toString();
                    if (debug.get()) {
                        log.info(SS.ArmorDataCV + ArmorDataCoded);
                    }

                    br.close();

                    if (debug.get()) {
                        log.info(SS.Decoding);
                    }

                    ArmorDataDecoded = ItemSerialization.itemStackArrayFromBase64(ArmorDataCoded);

                    if (debug.get()) {
                        log.info(SS.SendingArmorData + s.getName());
                    }

                    p.getInventory().setArmorContents(ArmorDataDecoded);

                } catch (IOException exc) {
                    if (debug.get()) {
                        exc.printStackTrace();
                    }

                    p.sendMessage(ChatColor.DARK_RED + SS.cantFind + args + SS.DotInv);
                }
            }
        }
    }
}