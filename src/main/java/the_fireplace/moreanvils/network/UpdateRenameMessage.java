package the_fireplace.moreanvils.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import the_fireplace.moreanvils.MoreAnvils;

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
        public IMessage handleServerMessage(EntityPlayer player, UpdateRenameMessage message, MessageContext ctx) {
            MoreAnvils.instance.playerAnvilMap.get(player).updateItemName(message.name);//TODO: Make sure this works
            return null;
        }

    }
}