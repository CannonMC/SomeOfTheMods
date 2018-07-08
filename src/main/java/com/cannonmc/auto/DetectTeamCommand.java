package com.cannonmc.auto;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class DetectTeamCommand extends CommandBase{

	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
	
	public String getCommandName() {
		
		return "detectteam";
	}

	public String getCommandUsage(ICommandSender sender) {
		return "/detectteam";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Detecting team..."));
		GoalPicker.detectPlayersTeam();
	}

}
