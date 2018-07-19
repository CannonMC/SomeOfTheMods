package com.cannonmc.auto.command;

import java.util.List;

import com.cannonmc.auto.AutoMove;
import com.cannonmc.auto.util.GoalPicker;
import com.cannonmc.auto.util.ScoreboardUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class AutoMoveCommand extends CommandBase{
	
	private EntityPlayerSP thePlayer;
	public Minecraft mc; 
    
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
	
	public String getCommandName() {
		return "automove";
	}

	public String getCommandUsage(ICommandSender sender) {
		return "/Automove <X> <Z>";
	}

	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(args.length > 0) {
			final String s = args[0];
			System.out.println(s);
			
			switch (s) {
				case "debug": {
					AutoMove.randomTeamPicker();
					GoalPicker.detectPlayersTeam();
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Team detected & target found"));
					break;
				}
				case "mode": {
					if (AutoMove.bypassMode == "BREAK") {
						AutoMove.bypassMode = "PLACE";
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Obstacle: " + EnumChatFormatting.GREEN + "NEW"));
					} else {
						AutoMove.bypassMode = "BREAK";
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Obstacle: " + EnumChatFormatting.RED + "OLD"));
					}
					break;
				}
				case "scoreboard": {
					Minecraft minecraft = Minecraft.getMinecraft();
					Scoreboard scoreboard = minecraft.theWorld.getScoreboard();
					if (scoreboard == null) {
						sender.addChatMessage(new ChatComponentText("There are no active scoreboards in this world."));
						return;
					}


					List<String> sidebarScores = ScoreboardUtils.getSidebarScores(scoreboard);
					for (String sidebarScore : sidebarScores) {
						sender.addChatMessage(new ChatComponentText(sidebarScore));
						System.out.println();
					}
					break;
				}
			}
			
		}else {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Incorrect usage of command."));
		}
		
	}
}

