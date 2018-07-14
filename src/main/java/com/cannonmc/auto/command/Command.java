package com.cannonmc.auto.command;

import com.cannonmc.auto.AutoMove;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Command extends CommandBase{
	
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
		if(args.length == 2) {
			AutoMove.finishX = Integer.parseInt(args[0]);
			AutoMove.finishZ = Integer.parseInt(args[1]);
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Coordinates set!"));
		}else{
			AutoMove.randomTeamPicker();
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "New Team"));
		}
	}
}
