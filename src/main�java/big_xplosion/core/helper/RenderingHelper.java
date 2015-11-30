package big_xplosion.core.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class RenderingHelper {

	public static final ResourceLocation MC_BLOCK_SHEET = TextureMap.locationBlocksTexture;
	public static final ResourceLocation MC_ITEM_SHEET = TextureMap.locationItemsTexture;

	public static TextureManager textureManager() {
		return Minecraft.getMinecraft().getTextureManager();
	}

	public static void bindTexture(ResourceLocation texture) {
		textureManager().bindTexture(texture);
	}
}
