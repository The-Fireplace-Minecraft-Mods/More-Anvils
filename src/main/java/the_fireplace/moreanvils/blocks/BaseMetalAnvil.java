package the_fireplace.moreanvils.blocks;

import cyano.basemetals.init.Materials;
import cyano.basemetals.material.MetalMaterial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author The_Fireplace
 */
public class BaseMetalAnvil extends MaterialAnvil {
    public MetalMaterial metalMaterial;
    public BaseMetalAnvil(MetalMaterial material){
        super(Materials.getArmorMaterialFor(material), material.getCapitalizedName(), "ingot");
        this.metalMaterial = material;
        setTickRandomly(material.equals(Materials.starsteel));
        if(material.equals(Materials.starsteel))
            setLightLevel(0.5F);
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
        if(metalMaterial == Materials.starsteel && random.nextInt(20) == 1 && state.getValue(DAMAGE) > 0){
            worldIn.setBlockState(pos, blockState.getBaseState().withProperty(FACING, state.getValue(FACING)).withProperty(DAMAGE, state.getValue(DAMAGE)-1));
        }
    }
}