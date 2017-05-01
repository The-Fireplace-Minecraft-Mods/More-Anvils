package the_fireplace.moreanvils.network.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author The_Fireplace
 */
public class Common {
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.getServerHandler().playerEntity;
    }
    public String translateToLocal(String s, Object... args){
        return s;
    }
}
