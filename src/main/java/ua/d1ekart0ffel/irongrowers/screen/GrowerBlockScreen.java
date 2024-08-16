package ua.d1ekart0ffel.irongrowers.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import ua.d1ekart0ffel.irongrowers.IronGrowers;

@OnlyIn(Dist.CLIENT)
public class GrowerBlockScreen extends AbstractContainerScreen<GrowerBlockMenu> {
    final ResourceLocation GROWER_BLOCK_GUI = ResourceLocation.fromNamespaceAndPath(IronGrowers.MODID,"textures/gui/grower_block_gui.png");
    final ResourceLocation GROWTH_PARTICLES_GUI = ResourceLocation.fromNamespaceAndPath(IronGrowers.MODID,"textures/gui/growth_particles_gui.png");

    public GrowerBlockScreen(GrowerBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public float getBurnProgress() {
        int p = this.menu.getProgress();
        int mp = this.menu.getMaxProgress();
        return mp != 0 && p != 0 ? Mth.clamp((float) p / (float) mp, 0.0F, 1.0F) : 0.0F;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(GROWER_BLOCK_GUI, x, y, 0, 0, 175, 165, 256, 256);
        int i = Mth.ceil(getBurnProgress() * 19.0F);

        if (this.menu.getProgress() != -1) {
            guiGraphics.blit(GROWTH_PARTICLES_GUI, x + 81, y + 27 + i, 0, i, 15, 19 - i, 15, 19);
        }
    }
}