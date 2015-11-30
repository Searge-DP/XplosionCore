package big_xplosion.core;

import big_xplosion.core.helper.ResourceHelper;
import big_xplosion.core.lib.Reference;
import big_xplosion.core.proxy.IProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class XplosionCore {

	@Mod.Instance(Reference.MOD_ID)
	public static XplosionCore instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IProxy proxy;

	public static ResourceHelper resourceHelper = ResourceHelper.createResourceHelper(Reference.MOD_ID);

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {

	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {

	}

	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) {

	}

}
