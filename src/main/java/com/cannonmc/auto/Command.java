package com.cannonmc.auto;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Command extends CommandBase{
	
	private EntityPlayerSP thePlayer;
	//Test
	@Override
	public String getCommandName() {
		return "automove";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/Automove <X> <Z>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 2) {
			AutoMove.finishX = Integer.parseInt(args[0]);
			AutoMove.finishZ = Integer.parseInt(args[1]);
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Coordinates set!"));
		}else {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "You need to give an X and Y coordinate,"));
		}
		
		 
	}
		

}
