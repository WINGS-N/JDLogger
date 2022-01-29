package WINGS.InventoryWorker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import WINGS.DLogger.storage.SS;
import net.md_5.bungee.api.ChatColor;

public class DecodeInventoryFromFileLegacy {

	public DecodeInventoryFromFileLegacy(CommandSender sender, String args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission(SS.checkPerm)) {
					String InvDataFileName = SS.DLPath + args;
					String ArmorDataFileName = SS.DLPath + args;
					ItemStack[] InvDataDecoded;
					ItemStack[] ArmorDataDecoded;
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
					    br.close();
					    
						InvDataDecoded = ItemSerialization.itemStackArrayFromBase64(InvDataCoded);
						p.getInventory().setContents(InvDataDecoded);
						
					} catch (IOException exc) {
						exc.printStackTrace();
						p.sendMessage(SS.cantFind + args);
						p.sendMessage(SS.Space);
						if(p.hasPermission(SS.DebugPerm)) {
							p.sendMessage(ChatColor.RED + exc.getMessage());
							p.sendMessage(SS.Space);
							p.sendMessage(ChatColor.DARK_RED + SS.atFileNotFound);
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
						br.close();
						
						ArmorDataDecoded = ItemSerialization.itemStackArrayFromBase64(ArmorDataCoded);
						p.getInventory().setArmorContents(ArmorDataDecoded);
						
					} catch (IOException exc) {
						exc.printStackTrace();
						p.sendMessage(ChatColor.DARK_RED + SS.cantFind + args);
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