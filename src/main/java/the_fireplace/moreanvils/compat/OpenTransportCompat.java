package the_fireplace.moreanvils.compat;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import the_fireplace.moreanvils.MoreAnvils;
import the_fireplace.moreanvils.blocks.MaterialAnvil;
import the_fireplace.moreanvils.compat.opentransport.MatAnvilGuiInterface;
import xyz.brassgoggledcoders.opentransport.api.events.RegisterBlockWrappersEvent;
import xyz.brassgoggledcoders.opentransport.api.wrappers.block.BlockWrapper;

/**
 * @author The_Fireplace
 */
public class OpenTransportCompat implements IModCompat {
    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void blockWrappers(RegisterBlockWrappersEvent event){
        for(MaterialAnvil anvil: MoreAnvils.anvils.values())
            event.getBlockWrapperRegistry().registerWrapper(new BlockWrapper(anvil).setGuiInterface(new MatAnvilGuiInterface()));
    }
}
