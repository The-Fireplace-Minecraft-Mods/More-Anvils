package the_fireplace.moreanvils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @author The_Fireplace
 */
public class TabMoreAnvils extends CreativeTabs {
    public TabMoreAnvils(String label) {
        super(label);
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(MoreAnvils.anvils.get("Gold"));
    }
}
