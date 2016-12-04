package the_fireplace.moreanvils.item;

import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import the_fireplace.moreanvils.blocks.MaterialAnvil;

import javax.annotation.Nonnull;

public class ItemMaterialAnvil extends ItemAnvilBlock
{
    MaterialAnvil material;
    public ItemMaterialAnvil(MaterialAnvil block)
    {
        super(block);
        material=block;
    }

    @Override
    @Nonnull
    public String getItemStackDisplayName(@Nonnull ItemStack stack)
    {
        switch(stack.getItemDamage()) {
            case 0:
                return String.format(I18n.translateToLocal("moreanvils.anvil"), I18n.translateToLocal("moreanvils." + material.getName().toLowerCase()));
            case 1:
                return String.format(I18n.translateToLocal("moreanvils.anvil.slightly_damaged"), I18n.translateToLocal("moreanvils."+material.getName().toLowerCase()));
            case 2:
                return String.format(I18n.translateToLocal("moreanvils.anvil.very_damaged"), I18n.translateToLocal("moreanvils."+material.getName().toLowerCase()));
            default:
                return String.format(I18n.translateToLocal("moreanvils.anvil"), I18n.translateToLocal("moreanvils."+material.getName().toLowerCase()));
        }
    }
}