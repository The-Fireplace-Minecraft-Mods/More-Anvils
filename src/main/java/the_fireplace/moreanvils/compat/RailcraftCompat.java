package the_fireplace.moreanvils.compat;

import mods.railcraft.common.items.ItemMaterials;
import the_fireplace.moreanvils.MoreAnvils;

/**
 * @author The_Fireplace
 */
public class RailcraftCompat implements IModCompat {
    @Override
    public void preInit() {
        MoreAnvils.addGenericAnvil("Steel", ItemMaterials.STEEL_ARMOR);
    }
}
