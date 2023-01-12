package WINGS7N.InventoryWorker;

import WINGS7N.DLogger.storage.SS;
import WINGS7N.DLogger.storage.debug;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;
import java.util.logging.Logger;

public class DecodeInventoryFromFile {

    //public String InvDataCoded = "DATA";

    Logger log = Bukkit.getLogger();

    Plugin plugin = Bukkit.getPluginManager().getPlugin("JDLogger");

    public DecodeInventoryFromFile(CommandSender s, String args, boolean self) {
        Player p = null;
        Player rollback_to_p = p;

        if (s instanceof Player && self) {
            p = (Player) s;
            rollback_to_p = p;
        }


        String rollback_p;
        if (args.split("/").length > 0) {
            rollback_p = args.split("/")[0];
        } else {
            rollback_p = "-[JDL:NULL]-";
        }


        if (s.hasPermission(SS.checkPerm)) {
            if (debug.get()) {
                log.info(SS.DetectedArgs + args);
            }

            //true shit code
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

            if (!self) {
                if (Bukkit.getPlayer(rollback_p) != null) {
                    rollback_to_p = Objects.requireNonNull(Bukkit.getPlayer(rollback_p));
                } else {
                    s.sendMessage(String.format(SS.player404, rollback_p));
                }
            }

            if (rollback_p == null) {
                s.sendMessage(SS.ERROR + "rollback_p is null");
                return;
            }

            assert rollback_to_p != null;

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
                    log.info(SS.SendingInvData + rollback_to_p.getName());
                }

                rollback_to_p.getInventory().setContents(InvDataDecoded);

                s.sendMessage(SS.prefix + "rolled back: " + InvDataFileName);

            } catch (Exception exc) {
                if (debug.get()) {
                    exc.printStackTrace();
                }
                s.sendMessage(SS.cantFind + args + SS.DotInv);
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

                rollback_to_p.getInventory().setArmorContents(ArmorDataDecoded);

                s.sendMessage(SS.prefix + "rolled back: " + ArmorDataFileName);

            } catch (Exception exc) {
                if (debug.get()) {
                    exc.printStackTrace();
                }

                s.sendMessage(ChatColor.DARK_RED + SS.cantFind + args + SS.DotInv);
            }
        }
    }
}