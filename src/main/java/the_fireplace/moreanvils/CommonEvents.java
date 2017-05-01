package the_fireplace.moreanvils;

import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import the_fireplace.moreanvils.config.ConfigValues;

/**
 * @author The_Fireplace
 */
public class CommonEvents {
    @SubscribeEvent
    public void anvilUpdate(AnvilUpdateEvent event){
        if(ConfigValues.DISABLEPWP)
        if(event.getLeft().getTagCompound() != null){
            if(event.getLeft().getTagCompound().hasKey("RepairCost")){
                event.setCost(event.getCost()-event.getLeft().getTagCompound().getByte("RepairCost"));
            }
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.getModID().equals(MoreAnvils.MODID))
            MoreAnvils.syncConfig();
    }
}
