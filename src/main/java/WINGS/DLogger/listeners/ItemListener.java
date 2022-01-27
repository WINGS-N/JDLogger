/*
package WINGS.DLogger;

import WINGS.DLogger.storage.SS;
import WINGS.InventoryWorker.DecodeInventoryFromFile;
import WINGS.InventoryWorker.DecodeInventoryFromFileLegacy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;
import java.util.logging.Logger;

public class ItemListener implements Listener {

    Logger log = Bukkit.getLogger();

    @EventHandler
    public void InvCheckClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission(SS.checkPerm)) {
            if (e.getClickedBlock() != null) {
                if (Objects.equals(e.getAction().toString(), "RIGHT_CLICK_BLOCK")) {
                    if (e.getHand() != null) {
                        if (e.getItem() != null) {
                            if (e.getItem().getItemMeta() != null) {
                                String FileName = e.getItem().getItemMeta().getDisplayName();
                                if (e.getItem().getType() == Material.BLAZE_ROD) {
                                    log.info(SS.SendingTo + DecodeInventoryFromFile.class.getName());
                                    log.info(SS.SendingData + p.getName() + SS.Space + FileName);
                                    new DecodeInventoryFromFile(p, FileName);
                                }
                                if (e.getItem().getType() == Material.STICK) {
                                    String FileNameLegacy = e.getItem().getItemMeta().getDisplayName();
                                    new DecodeInventoryFromFileLegacy(p, FileNameLegacy);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
*/