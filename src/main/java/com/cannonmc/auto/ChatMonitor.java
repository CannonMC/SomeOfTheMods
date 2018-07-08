package com.cannonmc.auto;

import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
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

        if(unformattedMessage.contains("2000")) {
            System.out.println("You were born in the year 2000!");
        }
       
    }
}