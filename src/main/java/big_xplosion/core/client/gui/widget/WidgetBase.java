package big_xplosion.core.client.gui.widget;

import big_xplosion.core.client.gui.GuiBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.Rectangle;
import java.util.List;

@SideOnly(Side.CLIENT)
public class WidgetBase {

	private final int id;
	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private final int u;
	private final int v;
	private final GuiBase gui;
	private final ResourceLocation[] textures;
	private int textureIndex = 0;
	private int value = 0;
	private boolean enabled = true;
	private boolean playSound = true;

	public WidgetBase(int id, int x, int y, int u, int v, int width, int height, GuiBase gui, String... textures) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.u = u;
		this.v = v;
		this.width = width;
		this.height = height;
		this.gui = gui;
		this.textures = new ResourceLocation[textures.length];

		for (int i = 0; i < textures.length; i++)
			this.textures[i] = new ResourceLocation(textures[i]);
	}

	public WidgetBase(int id, int x, int y, int u, int v, int width, int height, GuiBase gui, ResourceLocation... textures) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.u = u;
		this.v = v;
		this.width = width;
		this.height = height;
		this.gui = gui;
		this.textures = textures;
	}

	public WidgetBase(int id, int x, int y, int u, int v, int width, int height, GuiBase gui, int value, ResourceLocation... textures) {
		this(id, x, y, u, v, width, height, gui, textures);
		this.value = value;
	}

	public WidgetBase(int id, int x, int y, int u, int v, int width, int height, GuiBase gui, int value, String... textures) {
		this(id, x, y, u, v, width, height, gui, textures);
		this.value = value;
	}

	public WidgetBase(int id, int x, int y, int width, int height, GuiBase gui) {
		this(id, x, y, 0, 0, width, height, gui, "");
	}

	public WidgetBase setEnableClockSound(boolean value) {
		playSound = value;
		return this;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void render(int mouseX, int mouseY) {
		float shade = enabled ? 1.0F : 0.2F;
		GL11.glColor4f(shade, shade, shade, shade);
		if (textures[textureIndex] != null)
			gui.bindTexture(textures[textureIndex]);
		gui.drawTexturedModalRect(x, y, u, v, width, height);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void onWidgetClicked(int x, int y, int button) {
		if (playSound) {
			gui.soundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}
		gui.widgetActionPerformed(this);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {

	}
}
