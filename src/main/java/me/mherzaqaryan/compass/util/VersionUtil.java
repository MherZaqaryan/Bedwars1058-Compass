package me.mherzaqaryan.compass.util;

import me.mherzaqaryan.compass.CompassPlugin;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

public class VersionUtil {

   public static void playSound(Player player, String sound) {
       String[] args = sound.split(",");
       if (!(args.length == 1 || args.length == 3)) return;
       player.playSound(player.getLocation(), Sound.valueOf(args[0]), args.length == 3 ? Float.parseFloat(args[1]) : 1.0F, args.length == 3 ? Float.parseFloat(args[2]) : 1.0F);
   }

    public static boolean isLegacy() {
       return Stream.of("1_8","1_9","1_10","1_11","1_12").anyMatch(CompassPlugin.VERSION::contains);
    }

}
