package mods.hinasch.lib.client;

import java.util.OptionalInt;

import joptsimple.internal.Strings;
import mods.hinasch.lib.container.ContainerTextMenu;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.primitive.XY;
import mods.hinasch.lib.util.UtilNBT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiTextMenu extends GuiContainerBase{

	ITextMenuAdapter adapter;
	GuiButton buttonUp;
	GuiButton buttonDown;
	int scrollPos = 0;
	public static final int BUTTON_UP_ID = 5;
	public static final int BUTTON_DOWN_ID = 6;
	public static final int SIZE_Y = 20;
	public GuiTextMenu(EntityPlayer ep,ITextMenuAdapter adapter) {
		super(new ContainerTextMenu(ep));
		this.adapter = adapter;
		// TODO 自動生成されたコンストラクター・スタブ
	}
	@Override
	public void initGui()
	{
		super.initGui();
		int i = width  - xSize >> 1;
		int j = height - ySize >> 1;

		this.buttonUp = new GuiScrollButton(background, BUTTON_UP_ID, i+5, j+5,false);
		this.buttonDown = new GuiScrollButton(background, BUTTON_DOWN_ID, i+5, j+25,true);
		this.buttonList.add(buttonDown);
		this.buttonList.add(buttonUp);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		int i = width  - xSize >> 1;
		int j = height - ySize >> 1;
		//		HSLib.logger.trace("mouse:", mouseX-i,mouseY-j);
		OptionalInt index = this.getMouseOverElement(mouseX-i, mouseY-j);
		if(index.isPresent()){
			HSLib.logger.trace("element", index);
			this.drawHoveringText(this.adapter.getHoverText(index.getAsInt()), mouseX, mouseY);
		}

	}
	@Override
	protected void drawGuiContainerForegroundLayer(int par1,int par2)
	{
		super.drawGuiContainerForegroundLayer(par1, par2);

		//引数はARGBなのに注意
		String page = String.format("Page %d/%d", this.adapter.getMaxElement()-this.adapter.getColumn(),this.scrollPos);
		this.fontRendererObj.drawString(page, 0, 0, 0xffffff);
		int sizeY = 20;
		for(int i=0;i<this.adapter.getColumn();i++){
			int index = this.scrollPos + i;
			if(this.adapter.getMaxElement()>index){
				String text = this.adapter.getText(index);
				XY startPoint = new XY(4*8,4+(i*SIZE_Y));


				this.drawGradientRect(startPoint.getX(), startPoint.getY(), startPoint.getX()+4*10, startPoint.getY()+sizeY,-2130706433,16777215);
				this.fontRendererObj.drawString(text, startPoint.getX() + 16, startPoint.getY(), 0xffffff);
				ItemStack drawStack = this.adapter.getIconStack(index);
				if(drawStack!=null){
					this.drawStack(drawStack, startPoint.getX(), startPoint.getY(), "item");
				}

			}

		}
	}

	public OptionalInt getMouseOverElement(int x,int y){
		for(int i=0;i<this.adapter.getColumn();i++){
			XY sp = new XY(4*8,4+(i*SIZE_Y));
			XY ep = new XY(sp.getX()+40,sp.getY()+SIZE_Y);
			if(sp.getX()<x && sp.getY()<y && ep.getX()>x && ep.getY()>y){
				return OptionalInt.of(this.scrollPos+i);
			}
		}
		return OptionalInt.empty();
	}
	@Override
	public String getGuiTextureName(){
		return HSLib.MODID+":textures/gui/binder.png";
	}

	@Override
	public String getGuiName(){
		return "";
	}

	@Override
	public void onGuiClosed(){
		super.onGuiClosed();
		//    	ExtendedPlayerData.getData(openPlayer).setInteractingChest(null);
	}

	@Override
	public NBTTagCompound getSendingArgs(){
		NBTTagCompound nbt = UtilNBT.createCompound();
		nbt.setInteger("scroll", this.scrollPos);
		return nbt;
	}

	@Override
	public void prePacket(GuiButton par1GuiButton){

		if(par1GuiButton==this.buttonUp){
			if(this.scrollPos>0){
				this.scrollPos -= 1;
			}
		}
		if(par1GuiButton==this.buttonDown){
			if(this.scrollPos<(this.adapter.getMaxElement()-this.adapter.getColumn())){
				this.scrollPos += 1;
			}
		}
	}

	public static class GuiScrollButton extends GuiButton{

		ResourceLocation resource;
		boolean isDown;
		public static final int SIZE = 9;
		public GuiScrollButton(ResourceLocation res,int buttonId, int x, int y,boolean isDown) {
			super(buttonId, x, y, SIZE, SIZE, Strings.EMPTY);
			this.resource = res;
			this.isDown = isDown;
			// TODO 自動生成されたコンストラクター・スタブ
		}

        public void drawButton(Minecraft mc, int mouseX, int mouseY)
        {
            if (this.visible)
            {
                boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(resource);
                int i = 0;
                int j = 165;

                if (this.isDown)
                {
                    i += SIZE;
                }

                this.drawTexturedModalRect(this.xPosition, this.yPosition, i, j, SIZE, SIZE);
            }
        }
	}
}
