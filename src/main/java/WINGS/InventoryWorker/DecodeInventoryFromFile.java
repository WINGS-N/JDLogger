package WINGS.InventoryWorker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import WINGS.DLogger.storage.SS;
import net.md_5.bungee.api.ChatColor;

public class DecodeInventoryFromFile {
	
	//public String InvDataCoded = "DATA";
	
	Logger log = Bukkit.getLogger();
	 
	Plugin plugin = Bukkit.getPluginManager().getPlugin("JDLogger");
	
	FileConfiguration config = Objects.requireNonNull(plugin).getConfig();

	public DecodeInventoryFromFile(CommandSender s, String args) {
		if(s instanceof Player) {
			Player p = (Player) s;
			if(p.hasPermission(SS.checkPerm)) {
					if(config.getBoolean("DEV.DEBUG")) {
						log.info(SS.DetectedArgs + args);
					}
					
					String InvDataFileName = SS.DLPath + args + SS.DLInvPath + args + SS.DotInv;
					String ArmorDataFileName = SS.DLPath + args + SS.DLArmorPath + args + SS.DotInv;
					
					if(config.getBoolean("DEV.DEBUG")) {
						log.info(SS.InvSavedIn + InvDataFileName);
						log.info(SS.ArmorSavedIn + ArmorDataFileName);
					}
					
					ItemStack[] InvDataDecoded;
					ItemStack[] ArmorDataDecoded;
					
					if(config.getBoolean("DEV.DEBUG")) {
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
					    
					    if(config.getBoolean("DEV.DEBUG")) {
					    	log.info(SS.InvDataCV + InvDataCoded);
					    }
					    
					    br.close();
					    
					    if(config.getBoolean("DEV.DEBUG")) {
					    	log.info(SS.Decoding);
					    }
						InvDataDecoded = ItemSerialization.itemStackArrayFromBase64(InvDataCoded);
						
						if(config.getBoolean("DEV.DEBUG")) {
							log.info(SS.SendingInvData + s.getName());
						}
						p.getInventory().setContents(InvDataDecoded);
						
					} catch (IOException exc) {
						if(config.getBoolean("DEV.DEBUG")) {
						exc.printStackTrace();
						}
						p.sendMessage(SS.cantFind + args + SS.DotInv);
						p.sendMessage(SS.Space);
						if(config.getBoolean("DEV.DEBUG")) {
						if(p.hasPermission(SS.DebugPerm)) {
							p.sendMessage(ChatColor.RED + exc.getMessage());
							p.sendMessage(SS.Space);
							p.sendMessage(ChatColor.DARK_RED + SS.atFileNotFound);
						}
						}
					}
					
					try {
						BufferedReader br = new BufferedReader(new FileReader(ArmorDataFileName));
						StringBuilder sb = new StringBuilder();
						String line = br.readLine();
						
						while (line !=null) {
							sb.append(line);
							sb.append(System.lineSeparator());
							line = br.readLine();
						}
						
						String ArmorDataCoded = sb.toString();
						if(config.getBoolean("DEV.DEBUG")) {
							log.info(SS.ArmorDataCV + ArmorDataCoded);
						}
						
						br.close();
						
						if(config.getBoolean("DEV.DEBUG")) {
							log.info(SS.Decoding);
						}
						
						ArmorDataDecoded = ItemSerialization.itemStackArrayFromBase64(ArmorDataCoded);
						
						if(config.getBoolean("DEV.DEBUG")) {
						log.info(SS.SendingArmorData + s.getName());	
						}
						
						p.getInventory().setArmorContents(ArmorDataDecoded);
						
					} catch (IOException exc) {
						if(config.getBoolean("DEV.DEBUG")) {
						exc.printStackTrace();
						}
						
						p.sendMessage(ChatColor.DARK_RED + SS.cantFind + args + SS.DotInv);
						
						if(config.getBoolean("DEV.DEBUG")) {
						if(p.hasPermission(SS.DebugPerm)) {
							p.sendMessage(SS.Space);
							p.sendMessage(SS.Space);
							p.sendMessage(ChatColor.DARK_RED + SS.atFileNotFound);
						}
						}
					}
				}
			}
	}
}