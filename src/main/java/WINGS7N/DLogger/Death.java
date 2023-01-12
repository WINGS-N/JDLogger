package WINGS7N.DLogger;

import WINGS7N.DLogger.storage.SS;
import WINGS7N.DLogger.storage.debug;
import WINGS7N.providers.IP.GetIP;
import WINGS7N.InventoryWorker.ItemSerialization;
import WINGS7N.providers.JVM.RAM;
import WINGS7N.providers.JVM.Uptime;
import WINGS7N.providers.NMS.APIunsupported;
import WINGS7N.providers.NMS.GetNMSver;
import WINGS7N.providers.TPS.GetTPS;
import WINGS7N.providers.TPS.LagPercent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;


public class Death implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("JDLogger");

    FileConfiguration config = Objects.requireNonNull(plugin).getConfig();

    Logger log = Bukkit.getLogger();

    String os = System.getProperty("os.name");

    String NoIPdataSetting = System.getProperty("JDLogger.NoIP");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void DeathEvent(PlayerDeathEvent e) {

        Player p = e.getEntity();

        if (debug.get()) {
            log.info(ChatColor.DARK_RED + SS.Logging);
        }
        String ServerAddress = "Disabled by JDLogger.NoIP";
        if (!Objects.equals(NoIPdataSetting, "true")) {
            try {
                ServerAddress = GetIP.go();
            } catch (IOException err) {
                ServerAddress = Bukkit.getIp().toString();
            }
        }
        int ServerPort = 0;
        if (!Objects.equals(NoIPdataSetting, "true")) {
            ServerPort = Bukkit.getServer().getPort();
        }
        String Dmsg = " -[JDL:NULL]- ";
        if (e.getDeathMessage() != null) {
            Dmsg = e.getDeathMessage();
        }
        int DroppedXP = e.getDroppedExp();
        EntityType Etype = e.getEntityType();
        String EventName = e.getEventName();
        Location pLoc = p.getLocation();
        int pX = p.getLocation().getBlockX();
        int pY = p.getLocation().getBlockY();
        int pZ = p.getLocation().getBlockZ();
        Chunk pChunk = p.getLocation().getChunk();
        boolean KeepInv = e.getKeepInventory();
        boolean KeepLVL = e.getKeepLevel();
        int AfterDeathXP = e.getNewExp();
        int AfterDeathLVL = e.getNewLevel();
        int RenderDist = p.getClientViewDistance();
        String pDispName = p.getDisplayName();
        ItemStack[] pEqip = p.getEquipment().getArmorContents();
        float pFallDist = p.getFallDistance();
        InetSocketAddress Socket = p.getAddress();
        int PlayerPort = p.getAddress().getPort();
        String HostName = p.getAddress().getHostName();
        Collection<PotionEffect> pActiveEff = p.getActivePotionEffects();
        int pFireTicks = p.getFireTicks();
        int pFoodLVL = p.getFoodLevel();
        double pHealthLVL = p.getHealth();
        ItemStack[] pInv = p.getInventory().getContents();
        GameMode pGM = p.getGameMode();
        Player PlayerKiller = p.getKiller();
        double pLastDmg = p.getLastDamage();
        EntityDamageEvent pLastDmgCause = p.getLastDamageCause();
        String pWorld = p.getWorld().getName();
        float pWalkSpeed = p.getWalkSpeed();
        int EID = p.getEntityId();
        long FirstPlay = p.getFirstPlayed();
        List<Entity> passengers = p.getPassengers();
        Location bedSpawnLoc = p.getBedSpawnLocation();
        int bedX = -0; //-0 is null
        int bedY = -0; //-0 is null
        int bedZ = -0; //-0 is null
        Chunk bedChunk = null;
        if (bedSpawnLoc != null) {
            bedX = bedSpawnLoc.getBlockX();
            bedY = bedSpawnLoc.getBlockY();
            bedZ = bedSpawnLoc.getBlockZ();
            bedChunk = bedSpawnLoc.getChunk();
        }
        String Locale = p.getLocale();
        double RemAIR = p.getRemainingAir();
        UUID pUUID = p.getUniqueId();
        UUID DeathUUID = UUID.randomUUID();
        Entity Vehicle = null;
        Entity VehiclePreproc = p.getVehicle();
        if (VehiclePreproc != null) {
            Vehicle = VehiclePreproc;
        }
        String PlayerName = p.getName();
        long WorldTime = Objects.requireNonNull(p.getLocation().getWorld()).getTime();
        int ChunkHash = p.getLocation().getChunk().hashCode();
        int online = 0;
        for (Player onliplayer : Bukkit.getOnlinePlayers()) {
            online = online + 1;
        }
        int ping = 0;
        if (APIunsupported.run(GetNMSver.run()).equals("UNKNOWN")) {
            ping = p.getPing();
        }


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Objects.requireNonNull(config.getString("LOG.DateFormat")));

        if (config.getString("LOG.DateFormat") == null) {
            dtf = DateTimeFormatter.ofPattern(SS.DateFormat);
            log.warning("Error while reading DateFormat in config.yml, using " + SS.DateFormat);
        }

        ZoneId tz = ZoneId.of(Objects.requireNonNull(config.getString("LOG.TimeZone")));
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.now(), tz);

        if (debug.get()) {
            log.info(SS.TZID + tz);
            log.info(SS.TimeNow + dtf.format(now));
        }


        try {
            //Create files
            File PInvData = new File(SS.DLPath + PlayerName + SS.FolderSeparator);
            if (!PInvData.exists()) {
                if (debug.get()) {
                    log.info(SS.CreatingInvDataFolder + PlayerName);
                }
                PInvData.mkdirs();
            }


            File PlayerPath = new File(SS.DLPath + PlayerName + SS.FolderSeparator + dtf.format(now) + SS.DLInvPath + PlayerName + SS.FolderSeparator);
            if (!PlayerPath.exists()) {
                if (debug.get()) {
                    log.info(SS.CreatingFolder + PlayerName);
                }
                PlayerPath.mkdirs();
            }

            File ArmorData = new File(SS.DLPath + PlayerName + SS.FolderSeparator + dtf.format(now) + SS.DLArmorPath + PlayerName + SS.FolderSeparator);
            if (!ArmorData.exists()) {
                ArmorData.mkdirs();
            }

            if (config.getBoolean("LOG.LogInventory")) {
                FileWriter fstream = new FileWriter(SS.DLPath + PlayerName + SS.FolderSeparator + dtf.format(now) + SS.DLInvPath + PlayerName + SS.FolderSeparator + dtf.format(now) + SS.DotInv);
                BufferedWriter o = new BufferedWriter(fstream);


                o.write(ItemSerialization.itemStackArrayToBase64(pInv));
                o.close();
            }
        } catch (Exception exc) {
            log.severe(SS.ERROR);
            if (debug.get()) {
                log.severe(SS.ERROR + exc.getMessage());
            }
        }

        try {
            if (config.getBoolean("LOG.LogArmor")) {
                //Create file
                FileWriter fstream = new FileWriter(SS.DLPath + PlayerName + SS.FolderSeparator + dtf.format(now) + SS.DLArmorPath + PlayerName + SS.FolderSeparator + dtf.format(now) + SS.DotInv);
                BufferedWriter o = new BufferedWriter(fstream);

                o.write(ItemSerialization.itemStackArrayToBase64(pEqip));
                o.close();
            }

        } catch (Exception exc) {
            log.severe(SS.ERROR);
            if (debug.get()) {
                log.severe(SS.ERROR + exc.getMessage());
                exc.printStackTrace();
            }
        }

        try {
            // Create file
            File MainLogFolder = new File(SS.DLPath);
            if (!MainLogFolder.exists()) {
                MainLogFolder.mkdirs();
                if (debug.get()) {
                    log.info(SS.CreatingMainDLFolder);
                }
            }

            FileWriter fstream = new FileWriter(SS.DLPath + PlayerName + SS.FolderSeparator + dtf.format(now) + SS.DotLog);
            BufferedWriter o = new BufferedWriter(fstream);

            o.write(SS.LogSeparator);
            o.newLine();
            o.write(SS.RightsInLogFile);
            o.newLine();
            o.write(SS.RunningOn + os);
            o.newLine();
            o.write(SS.Using + Bukkit.getVersion());
            o.newLine();
            o.write(SS.Using + "JDLogger " + Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("JDLogger")).getDescription().getVersion());
            o.newLine();
            o.write(SS.site + Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("JDLogger")).getDescription().getWebsite());
            o.newLine();
            o.write("TimeZone: " + config.getString("LOG.TimeZone"));
            o.newLine();
            o.write(SS.ServerIP + ServerAddress);
            o.newLine();
            o.write(SS.ServerPort + ServerPort);
            o.newLine();
            o.write(SS.LogSeparator);
            o.newLine();
            o.newLine();
            o.newLine();

            o.write("Player: " + p);

            o.newLine();

            o.write("DeathMessage: " + Dmsg);

            o.newLine();

            o.write("DroppedEXP: " + DroppedXP);

            o.newLine();

            o.write("EntityType: " + Etype);

            o.newLine();

            o.write("EventName: " + EventName);

            o.newLine();

            o.write("Player Location: " + pLoc);

            o.newLine();

            o.write("Player X: " + pX);

            o.newLine();

            o.write("Player Y: " + pY);

            o.newLine();

            o.write("Player Z: " + pZ);

            o.newLine();

            o.write("Player Chunk: " + pChunk);

            o.newLine();

            o.write("Chunk Hash: " + ChunkHash);

            o.newLine();

            o.write("BedSpawnLocation: " + bedSpawnLoc);

            o.newLine();

            o.write("Bed X: " + bedX);

            o.newLine();

            o.write("Bed Y: " + bedY);

            o.newLine();

            o.write("Bed Z: " + bedZ);

            o.newLine();

            o.write("Bed Chunk: " + bedChunk);

            o.newLine();

            o.write("Is KeepInv?: " + KeepInv);

            o.newLine();

            o.write("Is KeepLVL?: " + KeepLVL);

            o.newLine();

            o.write("AfterXP: " + AfterDeathXP);

            o.newLine();

            o.write("AfterLVL: " + AfterDeathLVL);

            o.newLine();

            o.write("Render: " + RenderDist);

            o.newLine();

            o.write("Locale: " + Locale);

            o.newLine();
            o.newLine();
            o.newLine();
            o.write(SS.LogSeparatorHashTag + " -SYSTEM INFO- " + SS.LogSeparatorHashTag);
            o.newLine();
            o.newLine();

            double tps1m = GetTPS.run()[0];
            double tps5m = GetTPS.run()[1];
            double tps15m = GetTPS.run()[2];
            String tpsmsg = String.format("1m = %f, 5m = %f, 15m = %f", tps1m, tps5m, tps15m);

            o.write("TPS: " + tpsmsg + " (using NMS method)");
            o.newLine();
            o.write("Lag Percent: " + new LagPercent().get(tps1m) + " (using NMS method)");

            o.newLine();
            o.newLine();
            o.write("PING: " + ping + " (1.17+ feature, may be inaccurate, 0 == null)");

            o.newLine();
            o.newLine();
            o.newLine();

            o.write("RAM info may be inaccurate/incorrect!");
            o.newLine();
            o.write("RAM MAX: " + new RAM().max());

            o.newLine();

            o.write("RAM TOTAL: " + new RAM().total());

            o.newLine();

            o.write("RAM USED: " + new RAM().used());

            o.newLine();

            o.write("RAM FREE: " + new RAM().free());

            o.newLine();
            o.newLine();
            o.newLine();

            o.write("Uptime: " + new Uptime().get());

            o.newLine();
            o.newLine();
            o.write(SS.LogSeparatorHashTag + " -SYSTEM INFO- " + SS.LogSeparatorHashTag);
            o.newLine();
            o.newLine();
            o.newLine();

            o.write("DisplayName: " + pDispName);

            o.newLine();

            o.write("Fall: " + pFallDist);

            o.newLine();

            o.write("Remaining AIR (breath): " + RemAIR);

            o.newLine();

            o.write("passengers: " + passengers);

            o.newLine();

            o.write("Socket: " + Socket);

            o.newLine();

            o.write("Player's Hostname: " + HostName);

            o.newLine();

            o.write("Player's Port: " + PlayerPort);

            o.newLine();

            o.write("EID: " + EID);

            o.newLine();

            o.write("UUID: " + pUUID);

            o.newLine();

            o.write("JDL Death UUID: " + DeathUUID);

            o.newLine();

            o.write("First Play: " + FirstPlay);

            o.newLine();

            o.write("Fire: " + pFireTicks);

            o.newLine();

            o.write("Food: " + pFoodLVL);

            o.newLine();

            o.write("Health: " + pHealthLVL);

            o.newLine();

            o.write("Player GameMode: " + pGM);

            o.newLine();

            o.write("Player Killer: " + PlayerKiller + " (Only if player killed by player)");

            o.newLine();

            o.write("Last DMG: " + pLastDmg);

            o.newLine();

            o.write("Last DMG Cause: " + pLastDmgCause);

            o.newLine();

            o.write("World: " + pWorld);

            o.newLine();

            o.write("Walk Speed: " + pWalkSpeed);

            o.newLine();

            o.write("Vehicle: " + Vehicle);

            o.newLine();

            o.write("Effects: " + pActiveEff);

            o.newLine();

            o.write("World Time: " + WorldTime);

            o.newLine();

            o.write("Server online: " + online);

            o.newLine();

            o.newLine();
            o.newLine();
            o.newLine();

            o.write("Inventory: ");
            o.newLine();
            o.write(SS.LogSeparatorHashTag);
            o.newLine();
            o.newLine();
            o.write(SS.InvSavedIn + SS.DLPath + PlayerName + SS.FolderSeparator + dtf.format(now) + SS.DLInvPath + PlayerName + SS.FolderSeparator + dtf.format(now) + SS.DotInv);
            o.newLine();
            o.newLine();
            o.write(SS.LogSeparatorHashTag);

            o.newLine();
            o.newLine();
            o.newLine();

            o.newLine();
            o.write("Armor: ");
            o.newLine();
            o.write(SS.LogSeparatorHashTag);
            o.newLine();
            o.newLine();
            o.write(SS.ArmorSavedIn + SS.DLPath + PlayerName + SS.FolderSeparator + dtf.format(now) + SS.DLArmorPath + PlayerName + SS.FolderSeparator + dtf.format(now) + SS.DotInv);
            o.newLine();
            o.newLine();
            o.write(SS.LogSeparatorHashTag);

            o.newLine();
            o.newLine();
            o.newLine();
            o.newLine();
            o.write("ROLLBACK CMD: /jdl r " + PlayerName + SS.FolderSeparator + dtf.format(now));
            o.newLine();
            o.newLine();
            o.write("ROLLBACK TO YOUR INV CMD: /jdl r " + PlayerName + SS.FolderSeparator + dtf.format(now) + " -s");
            o.newLine();
            o.newLine();
            o.write("TP CMD: /tp " + pX + " " + pY + " " + pZ);

            o.newLine();
            o.newLine();
            o.newLine();
            o.write("This is The End.");
            o.newLine();


            o.close();

        } catch (Exception exc) {
            log.severe(SS.ERROR + exc.getMessage());
            if (debug.get()) {
                exc.printStackTrace();
            }
        }
    }
}
