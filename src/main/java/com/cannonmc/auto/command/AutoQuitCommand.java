package com.cannonmc.auto.command;

import com.cannonmc.auto.util.ChatMonitor;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class AutoQuitCommand extends CommandBase{

	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
	
	@Override
	public String getCommandName() {
		return "autoquit";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/autoquit";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (ChatMonitor.AutoLeave == false) {
			ChatMonitor.AutoLeave = true;
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "AutoQuit: " + EnumChatFormatting.GREEN + "ON"));
		} else {
			ChatMonitor.AutoLeave = false;
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "AutoQuit: " + EnumChatFormatting.RED + "OFF"));
		}
		
	}

}
