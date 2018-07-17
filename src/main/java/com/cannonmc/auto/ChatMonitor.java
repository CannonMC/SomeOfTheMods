package com.cannonmc.auto;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
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

	public static boolean AutoLeave = false;

	@SubscribeEvent
	public void onChat(final ClientChatReceivedEvent event) throws InterruptedException {
		unformattedMessage = event.message.getFormattedText();
		unformattedMessage = EnumChatFormatting.getTextWithoutFormattingCodes(unformattedMessage);
		System.out.println("System msg:" + unformattedMessage);
		
		if (!AutoLeave) {
			if (unformattedMessage.startsWith(" ")) {
				if (unformattedMessage.contains("The Bridge - 2v2v2v2")) {
					AutoMove.bridgeMode = "FOURTEAM"; // FOURTEAM
					AutoMove.active = true;
					System.out.println("AutoMove has been activated for four team!");
					GoalPicker.detectPlayersTeam();
					AutoMove.randomTeamPicker();
				} else if (unformattedMessage.contains("The Bridge - 1v1")) {
					AutoMove.bridgeMode = "TWOTEAM"; // TWOTEAM
					AutoMove.active = true;
					System.out.println("AutoMove has been activated for two team!");
					GoalPicker.detectPlayersTeam();
				} else if (unformattedMessage.contains(" WINS!")) {
					KeyBinding.onTick(AutoMove.keyToggle);
					
					System.out.println("AutoMove has been deactivated!");
				}
			}

		} else {
			if (unformattedMessage.startsWith(" ")) {
				if (unformattedMessage.contains("The Bridge - ") && unformattedMessage.contains("v")) {
					System.out.println("Leaving game...");
					Minecraft.getMinecraft().thePlayer.sendChatMessage("/leave");
				}
			}

		}

		// Detects when people are being mean
		if (unformattedMessage.contains(" bot")) {
			if (unformattedMessage.contains(Minecraft.getMinecraft().thePlayer.getName())) {
				TimeUnit.SECONDS.sleep(2);
				Minecraft.getMinecraft().thePlayer.sendChatMessage("no im not");
			}
		}

	}

}
