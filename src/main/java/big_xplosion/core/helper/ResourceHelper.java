package big_xplosion.core.helper;

import net.minecraft.util.ResourceLocation;

public class ResourceHelper {

	private String modid;

	private ResourceHelper(String modid) {
		this.modid = modid;
	}

	public static ResourceHelper createResourceHelper(String modid) {
		return new ResourceHelper(modid);
	}

	public ResourceLocation getLocation(String location) {
		return getLocation(modid, location);
	}

	public ResourceLocation getLocation(String modid, String location) {
		return new ResourceLocation(modid, location);
	}
}
