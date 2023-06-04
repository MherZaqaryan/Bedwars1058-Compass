package club.mher.compass.util.bw2023;

import club.mher.compass.util.TextUtil;
import club.mher.compass.util.VersionUtil;
import com.tomkeuper.bedwars.api.BedWars;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemBuilder {

    private final BedWars bedWars;
    private ItemStack is;

    public ItemBuilder(BedWars bedWars, String material) {
        this.bedWars = bedWars;
        String[] args = material.split(":");
        if (args.length == 2) {
            if (VersionUtil.isLegacy())
                is = new ItemStack(Material.valueOf(args[0]), 1, (byte) Integer.parseInt(args[1]));
            else is = new ItemStack(Material.valueOf(args[0]));
        }
        else is = new ItemStack(Material.valueOf(material));
    }

    public ItemBuilder(Material m, BedWars bedWars){
        this(bedWars, m, 1);
    }

    public ItemBuilder(BedWars bedWars, ItemStack is){
        this.bedWars = bedWars;
        this.is = is;
    }

    public ItemBuilder(BedWars bedWars, Material m, int data){
        this.bedWars = bedWars;
        is = new ItemStack(m, 1, (byte) data);
    }

    public ItemBuilder setDurability(short dur){
        is.setDurability(dur);
        return this;
    }

    public ItemBuilder setDisplayName(String name){
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(TextUtil.colorize(name));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level){
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench){
        is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner){
        try{
            SkullMeta im = (SkullMeta)is.getItemMeta();
            im.setOwner(owner);
            is.setItemMeta(im);
        } catch(ClassCastException ignored){}
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level){
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments){
        is.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder setEnchanted(boolean b) {
        if (!b) return this;
        ItemMeta meta = is.getItemMeta();
        meta.addEnchant(Enchantment.LUCK, 0, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setInfinityDurability(){
        is.setDurability(Short.MAX_VALUE);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(lore.stream().map(TextUtil::colorize).collect(Collectors.toList()));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(String line){
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if(!lore.contains(line))return this;
        lore.remove(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(int index){
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (index < 0 || index > lore.size()) return this;
        lore.remove(index);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line){
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if(im.hasLore())lore = new ArrayList<>(im.getLore());
        lore.add(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line, int pos) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    @Deprecated
    public ItemBuilder setWoolColor(DyeColor color){
        if(!is.getType().equals(Material.WOOL))return this;
        this.is.setDurability(color.getData());
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color){
        try{
            LeatherArmorMeta im = (LeatherArmorMeta)is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch(ClassCastException ignored) {}
        return this;
    }

    public ItemBuilder addTag(String key, String value) {
        NBTItem item = new NBTItem(bedWars, is);
        item.setString(key, value);
        this.is = item.getItem();
        return this;
    }

    public ItemBuilder addTag(String key, Integer value) {
        NBTItem item = new NBTItem(bedWars, is);
        item.setInteger(key, value);
        this.is = item.getItem();
        return this;
    }

    public ItemStack build(){
        return is;
    }

}
