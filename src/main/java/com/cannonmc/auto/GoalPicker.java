package com.cannonmc.auto;



import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class GoalPicker {
	
	public static int generalGoal = 35; // (X, 0) (Red team)
	
	public static int[] redGoal = {generalGoal, 0};
	public static int[] yellowGoal = {0, generalGoal};
	public static int[] blueGoal = {-generalGoal, 0};
	public static int[] greenGoal = {0, -generalGoal};
	
	public static String playersTeam;
	
	public static int getX(int teamChoice) {
		System.out.println(teamChoice);
		if(teamChoice == 1) {
			if (playersTeam == "red") {
				return 808; //Code for when target matches the team the player is on
			}
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "Red"));
			return redGoal[0];
			
		}else if (teamChoice == 2) {
			if (playersTeam == "yellow") {
				return 808; 
			}
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "Yellow"));
			return yellowGoal[0];
			
		}else if (teamChoice == 3) {
			if (playersTeam == "blue") {
				return 808; 
			}
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "Blue"));
			return blueGoal[0];
			
		}else if (teamChoice == 4) {
			if (playersTeam == "green") {
				return 808; 
			}
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "Green"));
			return greenGoal[0];
			
		}else {
			return 0;	
		}

	}
	
	public static int getZ(int teamChoice) {
		if(teamChoice == 1) {
			return redGoal[1];
			
		}else if (teamChoice == 2) {
			return yellowGoal[1];
			
		}else if (teamChoice == 3) {
			return blueGoal[1];
			
		}else if (teamChoice == 4) {
			return greenGoal[1];
			
		} else {
			return 0;
		}

	}

	//Detects which team the player is on
	public static void detectPlayersTeam() {
		double playerX = Minecraft.getMinecraft().thePlayer.posX;
		double playerZ = Minecraft.getMinecraft().thePlayer.posZ;
		
		if(playerX > -3 && playerX < 3) { //Green or Yellow
			if (playerZ < 0) {
				playersTeam= "green";
				
			}else {
				playersTeam= "yellow";
				
			}
		}else { //Red or Blue
			if(playerX > 0) {
				playersTeam = "red";
			}else {
				playersTeam= "blue";
			}
		}
		
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "You team has been detected as: " + playersTeam));
	}
	
}
