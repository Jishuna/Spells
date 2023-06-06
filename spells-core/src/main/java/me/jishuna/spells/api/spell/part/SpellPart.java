package me.jishuna.spells.api.spell.part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.ChatPaginator;

import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.config.annotation.PostLoad;
import me.jishuna.jishlib.items.ItemBuilder;
import me.jishuna.spells.api.SpellsAPI;
import me.jishuna.spells.api.altar.recipe.ingredient.AltarRecipeIngredient;
import me.jishuna.spells.api.altar.recipe.ingredient.MaterialIngredient;
import net.md_5.bungee.api.ChatColor;

public abstract class SpellPart implements Comparable<SpellPart> {
    public static final SpellPart EMPTY = new SpellPart(NamespacedKey.fromString("part:empty"), 0) {
    };

    private final NamespacedKey key;
    private ItemStack displayItem;

    @ConfigEntry("recipe")
    @Comment("The items required to craft this spell part at an altar. In order from first to last.")
    private List<AltarRecipeIngredient> recipe = new ArrayList<>();

    @ConfigEntry("lore")
    @Comment("The lore of this spell part when in item form.")
    private List<String> lore = new ArrayList<>();

    @ConfigEntry("display-name")
    @Comment("The display name of this spell part when in item form.")
    private String displayName = "unknown";

    @ConfigEntry("mana-cost")
    @Comment("The mana cost of this spell part.")
    private int manaCost;

    @ConfigEntry("enabled")
    @Comment("Enable/disable this spell part. Disabled parts cannot be used in spells.")
    private boolean enabled = true;

    protected SpellPart(NamespacedKey key, int cost) {
        this.key = key;
        this.manaCost = cost;
    }

    public boolean isAllowedModifier(ModifierPart modifier) {
        return true;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getConfigFolder() {
        return "";
    }

    public ItemStack getDisplayItem() {
        return displayItem.clone();
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public List<AltarRecipeIngredient> getRecipe() {
        return Collections.unmodifiableList(this.recipe);
    }

    public void setRecipe(Material... ingredients) {
        List<AltarRecipeIngredient> ingredientList = new ArrayList<>();
        for (Material material : ingredients) {
            ingredientList.add(new MaterialIngredient(material));
        }
        this.recipe = ingredientList;
    }

    @PostLoad
    private void onLoad() {
        this.displayItem = ItemBuilder.create(Material.PLAYER_HEAD).name(displayName).lore(lore).skullTexture("92228765df0e2ebd6c3a3fde070ddc8c551ceb4a43b959e1c44d349ce516560").persistentData(NamespacedKey.fromString("spells:part"), SpellsAPI.SPELL_PART_TYPE, this).build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SpellPart part) {
            return Objects.equals(this.key, part.key);
        }
        return false;
    }

    @Override
    public int compareTo(SpellPart other) {
        int diff = Integer.compare(getPriority(this), getPriority(other));

        if (diff != 0) {
            return diff;
        }
        return ChatColor.stripColor(this.displayName).compareTo(ChatColor.stripColor(other.displayName));
    }

    protected void setDefaultLore(String lore) {
        List<String> defaultLore = new ArrayList<>();

        if (this instanceof ShapePart) {
            defaultLore.add(ChatColor.DARK_GRAY + "Shape");
        } else if (this instanceof SubshapePart) {
            defaultLore.add(ChatColor.DARK_GRAY + "Subshape");
        } else if (this instanceof ModifierPart) {
            defaultLore.add(ChatColor.DARK_GRAY + "Modifier");
        } else if (this instanceof FilterPart) {
            defaultLore.add(ChatColor.DARK_GRAY + "Filter");
        } else if (this instanceof ActionPart) {
            defaultLore.add(ChatColor.DARK_GRAY + "Action");
        }

        defaultLore.add("");
        for (String line : ChatPaginator.wordWrap(lore, 50)) {
            defaultLore.add(ChatColor.WHITE + line);
        }

        this.lore = defaultLore;
    }

    private int getPriority(SpellPart part) {
        if (part instanceof ShapePart) {
            return 1;
        } else if (part instanceof SubshapePart) {
            return 2;
        } else if (part instanceof ModifierPart) {
            return 3;
        } else if (part instanceof ActionPart) {
            return 4;
        }
        return 5;
    }
}
