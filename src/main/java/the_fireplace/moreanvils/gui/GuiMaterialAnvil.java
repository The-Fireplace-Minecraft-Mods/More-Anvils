package the_fireplace.moreanvils.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import the_fireplace.moreanvils.blocks.MaterialAnvil;
import the_fireplace.moreanvils.container.ContainerMaterialAnvil;
import the_fireplace.moreanvils.network.PacketDispatcher;
import the_fireplace.moreanvils.network.UpdateRenameMessage;

import java.io.IOException;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiMaterialAnvil extends GuiContainer implements IContainerListener {
    private static final ResourceLocation anvilResource = new ResourceLocation("textures/gui/container/anvil.png");
    private static final ResourceLocation goldHammerResource = new ResourceLocation("moreanvils:textures/gui/gold_hammer.png");
    private static final ResourceLocation diamondHammerResource = new ResourceLocation("moreanvils:textures/gui/diamond_hammer.png");
    private ContainerMaterialAnvil anvil;
    private GuiTextField nameField;
    private InventoryPlayer playerInventory;

    public GuiMaterialAnvil(InventoryPlayer inventoryIn, World worldIn, MaterialAnvil anvil) {
        super(new ContainerMaterialAnvil(inventoryIn, worldIn, Minecraft.getMinecraft().thePlayer, anvil));
        this.playerInventory = inventoryIn;
        this.anvil = (ContainerMaterialAnvil) this.inventorySlots;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.nameField = new GuiTextField(0, this.fontRendererObj, i + 62, j + 24, 103, 12);
        this.nameField.setTextColor(-1);
        this.nameField.setDisabledTextColour(-1);
        this.nameField.setEnableBackgroundDrawing(false);
        this.nameField.setMaxStringLength(30);
        this.inventorySlots.removeListener(this);
        this.inventorySlots.addListener(this);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        this.inventorySlots.removeListener(this);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     *
     * @param mouseX Mouse x coordinate
     * @param mouseY Mouse y coordinate
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        this.fontRendererObj.drawString(I18n.format("container.repair"), 60, 6, 4210752);

        if (this.anvil.maximumCost > 0) {
            int i = 8453920;
            boolean flag = true;
            String s = I18n.format("container.repair.cost", this.anvil.maximumCost);

            if (this.anvil.maximumCost >= this.anvil.maximumCap && !this.mc.thePlayer.capabilities.isCreativeMode) {
                s = I18n.format("container.repair.expensive");
                i = 16736352;
            } else if (!this.anvil.getSlot(2).getHasStack()) {
                flag = false;
            } else if (!this.anvil.getSlot(2).canTakeStack(this.playerInventory.player)) {
                i = 16736352;
            }

            if (flag) {
                int j = -16777216 | (i & 16579836) >> 2 | i & -16777216;
                int k = this.xSize - 8 - this.fontRendererObj.getStringWidth(s);
                int l = 67;

                if (this.fontRendererObj.getUnicodeFlag()) {
                    drawRect(k - 3, l - 2, this.xSize - 7, l + 10, -16777216);
                    drawRect(k - 2, l - 1, this.xSize - 8, l + 9, -12895429);
                } else {
                    this.fontRendererObj.drawString(s, k, l + 1, j);
                    this.fontRendererObj.drawString(s, k + 1, l, j);
                    this.fontRendererObj.drawString(s, k + 1, l + 1, j);
                }

                this.fontRendererObj.drawString(s, k, l, i);
            }
        }

        GlStateManager.enableLighting();
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {
            this.renameItem();
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    private void renameItem() {
        String s = this.nameField.getText();
        //TODO: Make sure this works
        PacketDispatcher.sendToServer(new UpdateRenameMessage(s));
        Slot slot = this.anvil.getSlot(0);

        if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && s.equals(slot.getStack().getDisplayName())) {
            s = "";
        }

        this.anvil.updateItemName(s);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it.
     *
     * @param mouseX       Mouse x coordinate
     * @param mouseY       Mouse y coordinate
     * @param partialTicks How far into the current tick (1/20th of a second) the game is
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        this.nameField.drawTextBox();
    }

    /**
     * Draws the background layer of this container (behind the items).
     *
     * @param partialTicks How far into the current tick the game is, with 0.0 being the start of the tick and 1.0 being
     *                     the end.
     * @param mouseX       Mouse x coordinate
     * @param mouseY       Mouse y coordinate
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(anvilResource);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(i + 59, j + 20, 0, this.ySize + (this.anvil.getSlot(0).getHasStack() ? 0 : 16), 110, 16);

        if ((this.anvil.getSlot(0).getHasStack() || this.anvil.getSlot(1).getHasStack()) && !this.anvil.getSlot(2).getHasStack()) {
            this.drawTexturedModalRect(i + 99, j + 45, this.xSize, 0, 28, 21);
        }
        if (this.anvil.material == ItemArmor.ArmorMaterial.DIAMOND) {
            this.mc.getTextureManager().bindTexture(diamondHammerResource);
            this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        } else if (this.anvil.material == ItemArmor.ArmorMaterial.GOLD) {
            this.mc.getTextureManager().bindTexture(goldHammerResource);
            this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        }
    }

    /**
     * update the crafting window inventory with the items in the list
     */
    public void updateCraftingInventory(Container containerToSend, List<ItemStack> itemsList) {
        this.sendSlotContents(containerToSend, 0, containerToSend.getSlot(0).getStack());
    }

    /**
     * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
     * contents of that slot. Args: Container, slot number, slot contents
     */
    public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
        if (slotInd == 0) {
            this.nameField.setText(stack == null ? "" : stack.getDisplayName());
            this.nameField.setEnabled(stack != null);

            if (stack != null) {
                this.renameItem();
            }
        }
    }

    /**
     * Sends two ints to the client-side Container. Used for furnace burning time, smelting progress, brewing progress,
     * and enchanting level. Normally the first int identifies which variable to update, and the second contains the new
     * value. Both are truncated to shorts in non-local SMP.
     */
    public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {
    }

    public void sendAllWindowProperties(Container containerIn, IInventory inventory) {
    }
}