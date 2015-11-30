package big_xplosion.core.client.gui;

import big_xplosion.core.client.gui.widget.WidgetBase;
import big_xplosion.core.helper.RenderingHelper;
import big_xplosion.core.helper.TranslationHelper;
import big_xplosion.core.lib.Textures;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GuiBase extends GuiContainer {

	private static final ResourceLocation PLAYER_INV_TEXTURE = Textures.Gui.BASIC_PLAYER_INV;
	private final List<WidgetBase> widgets = Lists.newArrayList();
	private final int xSizePlayerInv = 176;
	private final int ySizePlayerInv = 100;
	private final ResourceLocation texture;
	private int titleXoffset = 5;
	private int titleYoffset = 8;
	private boolean shouldDrawWidgets = true;
	private int textColor = 0x003A81;
	private boolean centerTitle = true;
	private int tileGuiYSize = 0;
	private boolean drawPlayerInv = false;

	public GuiBase(Container container) {
		super(container);
		texture = texture();
		xSize = getXSize();
		ySize = getYSize();
		tileGuiYSize = getYSize();
	}

	public GuiBase setDrawPlayerInv(boolean draw) {
		drawPlayerInv = draw;
		if (draw)
			ySize = getYSize() + ySizePlayerInv;
		return this;
	}

	public GuiBase setTitleXOffset(int offset) {
		titleXoffset = offset;
		return this;
	}

	public GuiBase setTitleYOffset(int offset) {
		titleYoffset = offset;
		return this;
	}

	public GuiBase setTextColor(int color) {
		textColor = color;
		return this;
	}

	public GuiBase setCenterTitle(boolean value) {
		centerTitle = value;
		return this;
	}

	public GuiBase setDrawWidgets(boolean value) {
		shouldDrawWidgets = value;
		return this;
	}

	public abstract ResourceLocation texture();

	public abstract int getXSize();

	public abstract int getYSize();

	public abstract String getInventoryName();

	public abstract void initialize();

	public SoundHandler soundHandler() {
		return Minecraft.getMinecraft().getSoundHandler();
	}

	public void bindTexture(ResourceLocation texture) {
		RenderingHelper.bindTexture(texture);
	}

	public void drawString(String text, int x, int y) {
		drawString(text, x, y, textColor);
	}

	public void drawString(String text, int x, int y, int color) {
		fontRendererObj.drawString(text, x, y, color);
	}

	public void widgetActionPerformed(WidgetBase widget) {

	}

	public boolean hasCustomName() {
		return false;
	}

	public String getCustomName() {
		return null;
	}

	public void addWidget(WidgetBase widget) {
		widgets.add(widget);
	}

	@Override
	public void initGui() {
		super.initGui();
		if (drawPlayerInv)
			guiTop = (height - ySize) / 2;
		initialize();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(texture());
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, tileGuiYSize);

		if (drawPlayerInv) {
			bindTexture(PLAYER_INV_TEXTURE);
			drawTexturedModalRect(guiLeft, guiTop + tileGuiYSize, 0, 0, xSizePlayerInv, ySizePlayerInv);
		}
		bindTexture(texture());
		drawBackgroundPreWidgets(f, x, y);

		if (shouldDrawWidgets)
			drawWidgets(x, y);
		bindTexture(texture());

		drawBackgroundPostWidgets(f, x, y);
	}

	public void drawBackgroundPreWidgets(float f, int x, int y) {

	}

	public void drawBackgroundPostWidgets(float f, int x, int y) {

	}

	protected void drawWidgets(int x, int y) {
		for (WidgetBase widget : widgets) {
			widget.render(x, y);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		if (drawPlayerInv)
			drawString(TranslationHelper.translate("container.inventory"), 8, tileGuiYSize + 6, textColor);
		String name = hasCustomName() && getCustomName() != null ? getCustomName() : TranslationHelper.translate("gui.%s.name", getInventoryName());
		drawString(name, centerTitle ? (int) (getXSize() / 2 - name.length() * 2.5) : titleXoffset, titleYoffset, textColor);
		drawForegroundExtra(x, y);
	}

	public void drawForegroundExtra(int x, int y) {

	}

	@Override
	public void setWorldAndResolution(Minecraft minecraft, int width, int height) {
		widgets.clear();
		super.setWorldAndResolution(minecraft, width, height);
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		for (WidgetBase widget : widgets) {
			if (widget.getBounds().contains(x, y) && widget.isEnabled())
				widget.onWidgetClicked(x, y, button);
		}
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);
		List<String> tooltips = Lists.newArrayList();

		for (WidgetBase widget : widgets)
			if (widget.getBounds().contains(x, y))
				widget.addTooltip(x, y, tooltips, isShiftKeyDown());

		if (!tooltips.isEmpty()) {
			List<String> finalLines = Lists.newArrayList();
			for (String line : tooltips) {
				String[] lines = WordUtils.wrap(line, 30).split(System.getProperty("line.separator"));
				Collections.addAll(finalLines, lines);
			}
			drawHoveringText(finalLines, x, y, fontRendererObj);
		}
	}

	public void redraw() {
		widgets.clear();
		buttonList.clear();
		initialize();
	}

	public int getGuiLeft() {
		return guiLeft;
	}

	public int getGuiTop() {
		return guiTop;
	}
}
