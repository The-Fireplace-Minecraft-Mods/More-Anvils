package the_fireplace.moreanvils.network.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author The_Fireplace
 */
public class Client extends Common {
    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx){
        return (ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayerEntity(ctx));
    }
    @Override
    public String translateToLocal(String s, Object... args){
        return I18n.format(s, args);
    }
}
