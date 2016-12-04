package the_fireplace.moreanvils.compat.opentransport;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import the_fireplace.moreanvils.blocks.MaterialAnvil;
import the_fireplace.moreanvils.container.ContainerMaterialAnvil;
import the_fireplace.moreanvils.gui.GuiMaterialAnvil;
import xyz.brassgoggledcoders.opentransport.api.entities.IHolderEntity;
import xyz.brassgoggledcoders.opentransport.api.wrappers.block.IBlockWrapper;
import xyz.brassgoggledcoders.opentransport.api.wrappers.block.guiinterfaces.IGuiInterface;

import javax.annotation.Nonnull;

/**
 * @author The_Fireplace
 */
public class MatAnvilGuiInterface implements IGuiInterface {
    @Override
    public Gui getGUI(EntityPlayer player, IHolderEntity holderEntity, IBlockWrapper blockWrapper) {
        return new GuiMaterialAnvil(player.inventory, player.world, (MaterialAnvil)blockWrapper.getBlock());
    }

    @Override
    public Container getContainer(EntityPlayer player, IHolderEntity holderEntity, IBlockWrapper blockWrapper) {
        return new ContainerAnvilEntity(player, holderEntity, blockWrapper);
    }

    public class ContainerAnvilEntity extends ContainerMaterialAnvil {
        private IHolderEntity holderEntity;
        public ContainerAnvilEntity(EntityPlayer entityPlayer, IHolderEntity holderEntity, IBlockWrapper blockWrapper) {
            super(entityPlayer.inventory, blockWrapper.getWorldWrapper(), BlockPos.ORIGIN, entityPlayer, ((MaterialAnvil)blockWrapper.getBlock()).getArmorMaterial());
            this.holderEntity = holderEntity;
        }

        @Override
        public boolean canInteractWith(@Nonnull EntityPlayer entityPlayer) {
            return holderEntity.isUseableByPlayer(entityPlayer);
        }
    }
}
