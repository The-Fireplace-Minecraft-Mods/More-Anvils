package the_fireplace.moreanvils.compat;

import com.google.common.collect.Lists;
import com.mcmoddev.basemetals.init.Materials;
import com.mcmoddev.basemetals.util.Config;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.material.MMDMaterial;
import the_fireplace.moreanvils.MoreAnvils;
import the_fireplace.moreanvils.blocks.BaseMetalAnvil;

import java.util.Collection;

/**
 * @author The_Fireplace
 */
public class BaseMetalsCompat implements IModCompat {
    @Override
    public void preInit() {
        Config.init();
        Materials.init();
        Collection<MMDMaterial> materials = Lists.newArrayList(Materials.getAllMaterials());
	    materials.remove(Materials.getMaterialByName("gold"));
        materials.remove(Materials.getMaterialByName("iron"));
        materials.remove(Materials.getMaterialByName("zinc"));
        for(MMDMaterial material:materials){
            if(material.getType() == MMDMaterial.MaterialType.METAL && material.hasBlock(Names.BLOCK) && !material.hasBlock(Names.ANVIL))
                MoreAnvils.putAnvil(material.getCapitalizedName(), new BaseMetalAnvil(material));
        }
    }
}
