package me.mherzaqaryan.compass.util;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextUtil {

    public static String colorize(String s) {
        return s == null ? " " : ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String strip(String s) {
        return s == null ? " " : ChatColor.stripColor(s);
    }

    public static String[] colorize(String[] lines) {
        return colorize(Arrays.asList(lines.clone())).toArray(new String[0]);
    }

    public static List<String> colorize(List<String> s) {
        List<String> colorized = new ArrayList<>();
        s.forEach(st -> colorized.add(colorize(st)));
        return colorized;
    }

    public static TextComponent msgHoverClick(String msg, String hover, String click, ClickEvent.Action clickAction) {
        TextComponent tc = new TextComponent(colorize(msg));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(colorize(hover))).create()));
        tc.setClickEvent(new ClickEvent(clickAction, click));
        return tc;
    }

    public static String toUppercaseFirstChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

}
