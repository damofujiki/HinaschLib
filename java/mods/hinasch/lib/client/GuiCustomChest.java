package mods.hinasch.lib.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;



public class GuiCustomChest extends GuiContainer {
    private static final ResourceLocation field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    /**
     * window height is calculated with these values; the more rows, the heigher
     */
    private int inventoryRows;

    private int parInventoryRow = 9;

    public GuiCustomChest(Container parent,IInventory par1IInventory, IInventory par2IInventory)
    {
        super(parent);
        this.upperChestInventory = par1IInventory;
        this.lowerChestInventory = par2IInventory;
        this.allowUserInput = false;
        short short1 = 222;
        int i = short1 - 108;
        this.inventoryRows = par2IInventory.getSizeInventory() / 9;
		if(this.inventoryRows<=0){
			this.inventoryRows = 1;
		}

        this.ySize = i + this.inventoryRows * 18;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRendererObj.drawString(this.lowerChestInventory.hasCustomName() ? this.lowerChestInventory.getName() : I18n.format(this.lowerChestInventory.getName(), new Object[0]), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.upperChestInventory.hasCustomName() ? this.upperChestInventory.getName() : I18n.format(this.upperChestInventory.getName(), new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(field_147017_u);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(k, l + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
