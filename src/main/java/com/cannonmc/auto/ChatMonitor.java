package com.cannonmc.auto;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatMonitor {
	
    public Minecraft mc;
    public Collection SBNames;
    public String unformattedMessage;
    public String ScoreboardNames;

    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) throws InterruptedException {
        unformattedMessage = event.message.getFormattedText();
        unformattedMessage = EnumChatFormatting.getTextWithoutFormattingCodes(unformattedMessage);
        System.out.println("System msg:" + unformattedMessage);
        //§r§f§lThe Bridge§r§8 - §r§e§l2v2v2v2§r
        //Red wins = §r§c§lRED WINS!§r
        //Blue Wins = §r§9§lBlue WINS!§r
        if (unformattedMessage.startsWith(" ")) {
        	if(unformattedMessage.contains("The Bridge - 2v2v2v2")) {
                AutoMove.active = true;
                System.out.println("AutoMove has been activated!");
                GoalPicker.detectPlayersTeam();
                AutoMove.randomTeamPicker();
            } else if(unformattedMessage.contains(" WINS!")) {
                AutoMove.active = false;
                System.out.println("AutoMove has been deactivated!");
            }
        }
        
        //Detects when people are being mean
        if(unformattedMessage.contains(" bot")) {
        	if(unformattedMessage.contains(Minecraft.getMinecraft().thePlayer.getName())) {
        		TimeUnit.SECONDS.sleep(2);
        		Minecraft.getMinecraft().thePlayer.sendChatMessage("no im not");
        	}
        }
        
    }
}
