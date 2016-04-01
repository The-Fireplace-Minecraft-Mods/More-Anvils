package the_fireplace.moreanvils.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import the_fireplace.moreanvils.blocks.MaterialAnvil;
import the_fireplace.moreanvils.container.ContainerMaterialAnvil;

public class MoreAnvilsGuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                return new ContainerMaterialAnvil(player.inventory, world, new BlockPos(x, y, z), player, ((MaterialAnvil)world.getBlockState(new BlockPos(x, y, z)).getBlock()).getArmorMaterial());
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                return new GuiMaterialAnvil(player.inventory, player.worldObj, (MaterialAnvil)player.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock());
            default:
                return null;
        }
    }
}