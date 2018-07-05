package com.cannonmc.auto;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ChatMonitor {

    public Minecraft mc;

    public String unformattedMessage;

    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        unformattedMessage = event.message.getUnformattedText();
        System.out.println("This is a system printed message:" + unformattedMessage);
        //mc.thePlayer.addChatMessage(new ChatComponentText(unformattedMessage));
    }
}