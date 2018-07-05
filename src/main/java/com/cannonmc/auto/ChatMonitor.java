package com.cannonmc.auto;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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

        SBNames = Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveNames();
        System.out.println(SBNames);

        ScoreboardNames = Minecraft.getMinecraft().theWorld.getScoreboard().getObjective().getDisplayName();
        System.out.println("2018 TEST:" + ScoreboardNames);


        //S3BPacketScoreboardObjective scoreobjective = this.mc.theWorld.getScoreboard().func_96539_a(1);
        //ScoreObjective objective = board.func_96539_a(1);

        if(unformattedMessage.contains("2000")) {
            System.out.println("You were born in the year 2000!");
        }
        //mc.thePlayer.addChatMessage(new ChatComponentText(unformattedMessage));
    }
}