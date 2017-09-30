package the_fireplace.moreanvils;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import the_fireplace.moreanvils.blocks.MaterialAnvil;
import the_fireplace.moreanvils.compat.BaseMetalsCompat;
import the_fireplace.moreanvils.compat.IC2Compat;
import the_fireplace.moreanvils.compat.IModCompat;
import the_fireplace.moreanvils.compat.OpenTransportCompat;
import the_fireplace.moreanvils.compat.RailcraftCompat;
import the_fireplace.moreanvils.item.ItemMaterialAnvil;
import the_fireplace.moreanvils.network.MoreAnvilsGuiHandler;
import the_fireplace.moreanvils.network.PacketDispatcher;
import the_fireplace.moreanvils.network.proxy.Common;

/**
 * @author The_Fireplace
 */
@Mod(modid = MoreAnvils.MODID, name = MoreAnvils.MODNAME, updateJSON = "https://bitbucket.org/The_Fireplace/minecraft-mod-updates/raw/master/moreanvils.json", acceptedMinecraftVersions = "[1.12, 1.12.1, 1.12.2)", dependencies = "before:opentransport;after:basemetals;after:modernmetals")
public class MoreAnvils {
    public static final String MODID = "moreanvils";
    public static final String MODNAME = "More Anvils";

    @SidedProxy(clientSide = "the_fireplace.moreanvils.network.proxy.Client", serverSide = "the_fireplace.moreanvils.network.proxy.Common")
    public static Common proxy;

    public static final CreativeTabs TabMoreAnvils = new TabMoreAnvils("more_anvils");

    public static final LinkedHashMap<String, MaterialAnvil> anvils = Maps.newLinkedHashMap();
    public static final LinkedList<IModCompat> compats = Lists.newLinkedList();

    public void addCompats(){
        if(Loader.isModLoaded("basemetals"))
            compats.add(new BaseMetalsCompat());
        if(Loader.isModLoaded("ic2"))
            compats.add(new IC2Compat());
        if(Loader.isModLoaded("railcraft"))
            compats.add(new RailcraftCompat());
        if(Loader.isModLoaded("opentransport"))
            compats.add(new OpenTransportCompat());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PacketDispatcher.registerPackets();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new MoreAnvilsGuiHandler());

        addCompats();

        addGenericAnvil("Diamond", ItemArmor.ArmorMaterial.DIAMOND, "gem");
        addGenericAnvil("Gold", ItemArmor.ArmorMaterial.GOLD);

        compats.forEach(IModCompat::preInit);
    }
    
	private static IForgeRegistry<Item> itemRegistry = null;
	private static IForgeRegistry<Block> blockRegistry = null;
    
	public static void registerItemForBlock(Block block) {
		itemRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
	
	public static void registerBlock(Block block) {
		blockRegistry.register(block.setRegistryName(block.getUnlocalizedName().substring(5)));
	}
    
	
	@SubscribeEvent
	public static void itemRegistry(RegistryEvent.Register<Item> event) {
		itemRegistry = event.getRegistry();
		
		for(String key:anvils.keySet()) {
			itemRegistry.register(new ItemMaterialAnvil(anvils.get(key)).setRegistryName(anvils.get(key).getRegistryName()));
		}
	}
	
	@SubscribeEvent
	public static void blockRegistry(RegistryEvent.Register<Block> event) {
		blockRegistry = event.getRegistry();
		
		for(String key:anvils.keySet()) {
			blockRegistry.register(anvils.get(key));
		 }
	}
	
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
    	for(String key:anvils.keySet()) {
    		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation("anvils"), new ItemStack(anvils.get(key)), "bbb", " i ", "iii", 'b', "block"+key, 'i', anvils.get(key).getPrefix()+key));
    	}
    }

    public static void addGenericAnvil(String name, ItemArmor.ArmorMaterial material){
        addGenericAnvil(name, material, "ingot");
    }

    public static void addGenericAnvil(String name, ItemArmor.ArmorMaterial material, String prefix){
        putAnvil(name, new MaterialAnvil(material, name, prefix));
    }

    public static void putAnvil(String name, MaterialAnvil anvil){
        anvils.putIfAbsent(name, anvil);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide().isClient())
            registerItemRenders();
    }

    @SideOnly(Side.CLIENT)
    public void registerItemRenders() {
        for(String key:anvils.keySet())
            registerAnvilRenderer(anvils.get(key));
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
