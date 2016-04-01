package the_fireplace.moreanvils.blocks;

import net.minecraft.item.ItemArmor;
import the_fireplace.moreanvils.MoreAnvils;

/**
 * @author The_Fireplace
 */
public class BlockGoldAnvil extends MaterialAnvil {
    public BlockGoldAnvil()
    {
        super(ItemArmor.ArmorMaterial.GOLD);
        setUnlocalizedName("gold_anvil");
        setCreativeTab(MoreAnvils.TabMoreAnvils);
    }
}
