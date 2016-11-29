package the_fireplace.moreanvils.compat;

import cyano.basemetals.init.Materials;
import cyano.basemetals.material.MetalMaterial;
import the_fireplace.moreanvils.MoreAnvils;
import the_fireplace.moreanvils.blocks.BaseMetalAnvil;

import java.util.Collection;

/**
 * @author The_Fireplace
 */
public class BaseMetalsCompat implements IModCompat {
    @Override
    public void preInit() {
        Materials.init();
        Collection<MetalMaterial> materials = Materials.getAllMetals();
        materials.remove(Materials.vanilla_diamond);
        materials.remove(Materials.vanilla_gold);
        materials.remove(Materials.vanilla_iron);
        materials.remove(Materials.vanilla_stone);
        materials.remove(Materials.vanilla_wood);
        materials.remove(Materials.zinc);
        for(MetalMaterial material:Materials.getAllMetals()){
            if(!material.getName().equals("silicon"))
                MoreAnvils.putAnvil(material.getCapitalizedName(), new BaseMetalAnvil(material));
        }
    }
}
