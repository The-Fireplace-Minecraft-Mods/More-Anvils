package the_fireplace.moreanvils.blocks;

import com.mcmoddev.basemetals.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author The_Fireplace
 */
public class BaseMetalAnvil extends MaterialAnvil {
    public MMDMaterial metalMaterial;
    public BaseMetalAnvil(MMDMaterial material){
        super(Materials.getArmorMaterialFor(material), material.getCapitalizedName(), "ingot");
        this.metalMaterial = material;
        setTickRandomly(material.equals(Materials.getMaterialByName("starsteel")));
        if(material.equals(Materials.getMaterialByName("starsteel")))
            setLightLevel(0.5F);
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
        if(metalMaterial == Materials.getMaterialByName("starsteel") && random.nextInt(20) == 1 && state.getValue(DAMAGE) > 0){
            worldIn.setBlockState(pos, blockState.getBaseState().withProperty(FACING, state.getValue(FACING)).withProperty(DAMAGE, state.getValue(DAMAGE)-1));
        }
    }
}