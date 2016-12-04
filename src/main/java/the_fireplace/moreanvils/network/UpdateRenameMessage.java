package the_fireplace.moreanvils.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import the_fireplace.moreanvils.container.ContainerMaterialAnvil;

/**
 * @author The_Fireplace
 */
public class UpdateRenameMessage implements IMessage {

    public UpdateRenameMessage() {
    }

    public String name;

    public UpdateRenameMessage(String newName) {
        this.name = newName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
    }

    public static class Handler extends AbstractServerMessageHandler<UpdateRenameMessage> {

        @Override
        public IMessage handleServerMessage(EntityPlayer player, final UpdateRenameMessage message, final MessageContext ctx) {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.world;
            mainThread.addScheduledTask(() -> {
                ContainerMaterialAnvil anvil = (ContainerMaterialAnvil) ctx.getServerHandler().playerEntity.openContainer;
                anvil.updateItemName(message.name);
            });
            return null;
        }

    }
}