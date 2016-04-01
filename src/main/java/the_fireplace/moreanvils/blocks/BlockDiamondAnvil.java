package the_fireplace.moreanvils.blocks;

import net.minecraft.item.ItemArmor;
import the_fireplace.moreanvils.MoreAnvils;

/**
 * @author The_Fireplace
 */
public class BlockDiamondAnvil extends MaterialAnvil {
    public BlockDiamondAnvil()
    {
        super(ItemArmor.ArmorMaterial.DIAMOND);
        setUnlocalizedName("diamond_anvil");
        setCreativeTab(MoreAnvils.TabMoreAnvils);
    }
}
