package com.cannonmc.auto;

import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        } else if(unformattedMessage.contains("§r§c§lRED WINS!§r")) {
            AutoMove.active = false;
            System.out.println("Auto Move has now been deactivated because red won!");
        } else if(unformattedMessage.contains("§r§9§lBlue WINS!§r")) {
            AutoMove.active = false;
            System.out.println("Auto Move has now been deactivated because blue won!");
        }
    }
}
