package mods.hinasch.lib.client;

import org.lwjgl.opengl.GL11;

import mods.hinasch.lib.container.ContainerBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

/**
 * 初期化はinitGui()をOverrideしてください。
 *
 */
public class GuiContainerBase extends GuiContainer{


	protected Container container;
	protected final ResourceLocation background = new ResourceLocation(this.getGuiTextureName());

	protected String message;

	public GuiContainerBase(Container par1Container) {
		super(par1Container);
		this.container = par1Container;
		this.message = "";
		// TODO 自動生成されたコンストラクター・スタブ
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

	}

	public int MessageYpos(){
		return 66;
	}
	public void setMessage(String str){
		this.message = str;
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

	/**
	 * Domain:textures/guiみたいな感じで。
	 * @return
	 */
	public String getGuiTextureName(){
		return "Domain:textures/gui/";
	}
	public String getGuiName(){
		return "Unknown";
	}


	/**
	 * サーバ側に送る情報があればObject[]に突っ込んで返す。<br>
	 * 初期値はnull<br>
	 * ContainerBaseで受け取る。
	 * @return
	 */
	public NBTTagCompound getSendingArgs(){
		return null;
	}

	public void prePacket(GuiButton par1GuiButton){

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
}
