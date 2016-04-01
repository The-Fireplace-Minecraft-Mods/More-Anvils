package the_fireplace.moreanvils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import the_fireplace.moreanvils.blocks.BlockDiamondAnvil;
import the_fireplace.moreanvils.blocks.BlockGoldAnvil;

/**
 * @author The_Fireplace
 */
@Mod(modid = MoreAnvils.MODID, name = MoreAnvils.MODNAME)
public class MoreAnvils {
    public static final String MODID = "moreanvils";
    public static final String MODNAME = "More Anvils";
    public static String VERSION;
    public static final String curseCode = "";

    public static final CreativeTabs TabMoreAnvils = new TabMoreAnvils("more_anvils");

    public static final Block diamond_anvil = new BlockDiamondAnvil();
    public static final Block gold_anvil = new BlockGoldAnvil();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerBlock(diamond_anvil, ItemAnvilBlock.class, "diamond_anvil");
        GameRegistry.registerBlock(gold_anvil, ItemAnvilBlock.class, "gold_anvil");

        GameRegistry.addRecipe(new ShapedOreRecipe(diamond_anvil, "bbb", " i ", "iii", 'b', "blockDiamond", 'i', "gemDiamond"));
        GameRegistry.addRecipe(new ShapedOreRecipe(gold_anvil, "bbb", " i ", "iii", 'b', "blockGold", 'i', "ingotGold"));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide().isClient())
            registerItemRenders();
    }

    @SideOnly(Side.CLIENT)
    public void registerItemRenders() {
        registerAnvilRenderer(diamond_anvil);
        registerAnvilRenderer(gold_anvil);
    }

    @SideOnly(Side.CLIENT)
    public void registerAnvilRenderer(Block anvil) {
        ModelBakery.registerItemVariants(Item.getItemFromBlock(anvil),
                new ModelResourceLocation(MODID + ":" + anvil.getUnlocalizedName().substring(5) + "_intact", "inventory"),
                new ModelResourceLocation(MODID + ":" + anvil.getUnlocalizedName().substring(5) + "_slightly_damaged", "inventory"),
                new ModelResourceLocation(MODID + ":" + anvil.getUnlocalizedName().substring(5) + "_very_damaged", "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(anvil), 0, new ModelResourceLocation(MODID + ":" + anvil.getUnlocalizedName().substring(5) + "_intact", "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(anvil), 1, new ModelResourceLocation(MODID + ":" + anvil.getUnlocalizedName().substring(5) + "_slightly_damaged", "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(anvil), 2, new ModelResourceLocation(MODID + ":" + anvil.getUnlocalizedName().substring(5) + "_very_damaged", "inventory"));
    }
}
