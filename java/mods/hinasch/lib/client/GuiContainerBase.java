package mods.hinasch.lib.client;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import mods.hinasch.lib.container.ContainerBase;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.primitive.XY;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

/**
 * 初期化はinitGui()をOverrideしてください。
 *
 */
public class GuiContainerBase extends GuiContainer{


	public static class Icon{
		final public int id;
		final public int x;
		final public int y;
		final public int u;
		final public int v;
		public boolean isDisplayHover = false;
		public Icon(int id,int x, int y,int u,int v,boolean hover) {
			super();
			this.id = id;
			this.x = x;
			this.y = y;
			this.u = u;
			this.v = v;
			this.isDisplayHover = hover;
		}

		public XY getUV(GuiContainerBase gui){
			return new XY(u,v);
		}
	}

	public static class IconButton extends Icon{

		public IconButton(int id, int x, int y, int u, int v, boolean hover) {
			super(id, x, y, u, v, hover);
			// TODO 自動生成されたコンストラクター・スタブ
		}

	}
	protected Minecraft mc = Minecraft.getMinecraft();
	protected Container container;
	protected final ResourceLocation background = new ResourceLocation(this.getGuiTextureName());
	protected final ScaledResolution scaledresolution = new ScaledResolution(this.mc);

	protected String message;

	List<GuiContainerBase.Icon> iconList = Lists.newArrayList();


	public GuiContainerBase(Container par1Container) {
		super(par1Container);
		this.container = par1Container;
		this.message = "";
		// TODO 自動生成されたコンストラクター・スタブ
	}
	/**
	 * ContainerBaseとセットでここでパケットを送る。
	 * 基本的にOverrideひつようなし。
	 */
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton!=null){

			if (!par1GuiButton.enabled)
			{
				return;
			}


			this.prePacket(par1GuiButton);

			if(container instanceof ContainerBase){
				((ContainerBase) container).onButtonPushed(par1GuiButton.id,this.getSendingArgs());
			}




		}
	}
    /**
	 * initGui()などから実行してください。
	 * @param id
	 * @param startX
	 * @param startY
	 * @param width
	 * @param height
	 * @param buttonString
	 * @return
	 */
	public GuiButton addButton(int id,int startX,int startY,int width,int height,String buttonString){
		GuiButton guiButton = new GuiButton(id,startX,startY,width,height,buttonString);
		buttonList.add(guiButton);
		return guiButton;
	}
	protected void addIcon(Icon icon){
		this.iconList.add(icon);
	}

	protected void addIcon(int id,int x,int y,int u,int v,boolean hover){
		this.iconList.add(new Icon(id,x,y,u,v,hover));
	}

	/**
	 * ここでバックグラウンドを表示。
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(background);
		int xStart = width - xSize >> 1;
			int yStart = height - ySize >> 1;
			drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

	}
	/**
	 * テキストはここで描く。
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1,int par2)
	{
		fontRendererObj.drawString(getGuiName(),8,6,0x404040);
		if(!message.equals("")){
			fontRendererObj.drawString(message,8,this.MessageYpos(),0xffffff);
		}

		//途中で解像度を変えると位置判定バグる
		for(Icon icon:this.iconList){
			Minecraft.getMinecraft().getTextureManager().bindTexture(background);


//			UnsagaMod.logger.trace("xy", Mouse.getX() - this.getWindowStartX(),Mouse.getY() - this.getWindowStartY());
			if(icon instanceof IconButton && this.isMouseInBox(this.getMouseX() - this.getWindowStartX(),this.getMouseY()- this.getWindowStartY(), icon.x, icon.y, icon.x+16, icon.y+16)){

//				this.drawRect(icon.x, icon.y, icon.x+16, icon.y+16, 0xffffff);
				GlStateManager.color(0.0F, 1.0F, 0.0F, 1.0F);
			}else{
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			}

			drawTexturedModalRect(icon.x, icon.y, icon.getUV(this).getX(), icon.getUV(this).getY(), 16, 16);
		}

	}

	public List<String> drawIconHoverText(Icon id){
		return Lists.newArrayList();
	}
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
		super.drawScreen(mouseX, mouseY, partialTicks);

		for(Icon ic:this.iconList){
			if(this.isMouseInBox(mouseX - this.getWindowStartX(), mouseY - this.getWindowStartY(), ic.x, ic.y, ic.x+16, ic.y+16)){
				this.drawHoveringText(this.drawIconHoverText(ic), mouseX, mouseY);
			}
		}
    }

	protected void drawStack(ItemStack stack, int x, int y, String altText)
    {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        this.itemRender.zLevel = 200.0F;
        net.minecraft.client.gui.FontRenderer font = null;
        if (stack != null) font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRendererObj;
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
//        this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y - (this.draggedStack == null ? 0 : 8), altText);
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
    }
	public String getGuiName(){
		return "Unknown";
	}

	/**
	 * Domain:textures/guiみたいな感じで。
	 * @return
	 */
	public String getGuiTextureName(){
		return "Domain:textures/gui/";
	}
	public int getMouseX(){
		return Mouse.getX() * (scaledresolution.getScaledWidth()) / this.mc.displayWidth;
	}
	public int getMouseY(){
		int j1 = scaledresolution.getScaledHeight();
		return j1 - Mouse.getY() *j1 / this.mc.displayHeight -1;
	}
	/**
	 * サーバ側に送る情報があればObject[]に突っ込んで返す。<br>
	 * 初期値はnull<br>
	 * ContainerBaseで受け取る。
	 * @return
	 */
	@Nullable
	public NBTTagCompound getSendingArgs(){
		return null;
	}

	public int getWindowStartX(){
		return width - xSize >> 1;
	}
	public int getWindowStartY(){
		return height - ySize >> 1;
	}


	public boolean isMouseInBox(int x,int y,int bx1,int by1,int bx2,int by2){
		return bx1<x && by1<y && bx2>x && by2>y;
	}

	public int MessageYpos(){
		return 66;
	}

	@Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    	UnsagaMod.logger.trace("click", "clicked");
		for(Icon icon:this.iconList){
    		UnsagaMod.logger.trace("click", mouseX - this.getWindowStartX(),mouseY - this.getWindowStartY(),icon.x,icon.y);
	    	if(icon instanceof  IconButton && this.isMouseInBox(mouseX- this.getWindowStartX(), mouseY- this.getWindowStartY(), icon.x, icon.y, icon.x+16, icon.y+16)){
	    		this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				this.prePacket(icon);

				if(container instanceof ContainerBase){
					((ContainerBase) container).onButtonPushed(icon.id,this.getSendingArgs());
				}
	    	}
		}

    }
	public void prePacket(GuiButton par1GuiButton){

	}

	public void onPacketFromServer(NBTTagCompound message){
		HSLib.logger.trace(this.getGuiName(), "届いてます"+message.getString("test"));

	}
	public void prePacket(Icon icon){

	}
	public void setMessage(String str){
		this.message = str;
	}
}
