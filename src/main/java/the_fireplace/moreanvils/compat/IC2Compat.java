package the_fireplace.moreanvils.compat;

import net.minecraftforge.common.util.EnumHelper;
import the_fireplace.moreanvils.MoreAnvils;

/**
 * @author The_Fireplace
 */
public class IC2Compat implements IModCompat {
    @Override
    public void preInit() {
        MoreAnvils.addGenericAnvil("Bronze", EnumHelper.addArmorMaterial("MA_IC2_BRONZE", "MA_IC2_BRONZE", 15, new int[]{2, 6, 5, 2}, 9, null, 0.0F));//Copy of IC2 Bronze; the original is a local variable
    }
}
