package the_fireplace.moreanvils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author The_Fireplace
 */
public class TabMoreAnvils extends CreativeTabs {
    public TabMoreAnvils(String label) {
        super(label);
    }

    @Override
    @Nonnull
    public ItemStack getTabIconItem() {
        return new ItemStack(MoreAnvils.anvils.get("Gold"));
    }
}
