package the_fireplace.moreanvils.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import the_fireplace.moreanvils.blocks.MaterialAnvil;

/**
 * @author The_Fireplace
 */
public class UpdateRenameMessage implements IMessage {

    public UpdateRenameMessage() {
    }

    public String name;
    public BlockPos blockPos;

    public UpdateRenameMessage(String newName, BlockPos pos) {
        this.name = newName;
        this.blockPos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        String[] values = ByteBufUtils.readUTF8String(buf).split(System.lineSeparator());
        name = values[0];
        blockPos = BlockPos.fromLong(Long.parseLong(values[1]));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name + System.lineSeparator() + String.valueOf(blockPos.toLong()));
    }

    public static class Handler extends AbstractServerMessageHandler<UpdateRenameMessage> {

        @Override
        public IMessage handleServerMessage(EntityPlayer player, UpdateRenameMessage message, MessageContext ctx) {
            if(((MaterialAnvil)player.worldObj.getBlockState(message.blockPos).getBlock()).container != null)
            ((MaterialAnvil)player.worldObj.getBlockState(message.blockPos).getBlock()).container.repairedItemName = message.name;
            return null;
        }

    }
}