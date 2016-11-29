package the_fireplace.moreanvils.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import the_fireplace.moreanvils.blocks.MaterialAnvil;
import the_fireplace.moreanvils.container.ContainerMaterialAnvil;
import the_fireplace.moreanvils.gui.GuiMaterialAnvil;

public class MoreAnvilsGuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                if(world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof MaterialAnvil)
                    return new ContainerMaterialAnvil(player.inventory, world, new BlockPos(x, y, z), player, ((MaterialAnvil)world.getBlockState(new BlockPos(x, y, z)).getBlock()).getArmorMaterial());
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                if(world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof MaterialAnvil)
                    return new GuiMaterialAnvil(player.inventory, world, (MaterialAnvil)world.getBlockState(new BlockPos(x, y, z)).getBlock());
            default:
                return null;
        }
    }
}