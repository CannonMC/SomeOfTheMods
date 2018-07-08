package com.cannonmc.auto;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.scoreboard.Scoreboard;
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

import java.lang.reflect.Array;
import java.util.Collection;

public class ChatMonitor {

    public Minecraft mc;
    public Collection SBNames;
    public String unformattedMessage;
    public String ScoreboardNames;

    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        unformattedMessage = event.message.getFormattedText();
        System.out.println("System msg:" + unformattedMessage);
        //§r§f§lThe Bridge§r§8 - §r§e§l2v2v2v2§r
        //Red wins = §r§c§lRED WINS!§r
        //Blue Wins = §r§9§lBlue WINS!§r
        if(unformattedMessage.contains("§r§f§lThe Bridge§r§8 - §r§e§l1v1§r")) {
            AutoMove.active = true;
            System.out.println("Auto Move has been activated!");
        } else if(unformattedMessage.contains("§r§c§lRED WINS!§r") || unformattedMessage.contains("§r§9§lBlue WINS!§r")) {
            AutoMove.active = false;
        }
    }
}