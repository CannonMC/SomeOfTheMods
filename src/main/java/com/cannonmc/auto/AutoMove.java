package com.cannonmc.auto;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.cannonmc.auto.command.AutoQuitCommand;
import com.cannonmc.auto.command.Command;
import com.cannonmc.auto.command.DetectTeamCommand;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = "auto", version = "1.0", acceptedMinecraftVersions = "1.8")
public class AutoMove {
	public static boolean active;
	public KeyBinding toggle;
	public Minecraft mc;
	private EntityPlayerSP thePlayer;
	private boolean prevState;
	
	public static int keyToggle;
	
	public static GoalPicker picker = new GoalPicker();
	static Random rand = new Random();


	private int autotimer = 0;
	public int jumptries = 0;
	public int cooldown = 10;

	public static int finishX = 0;
	public static int finishZ = 0;
	
	public static String bridgeMode; //FOURTEAM, TWOTEAM

	public AutoMove() {
		this.prevState = false;
	}
	
	@Mod.EventHandler
	public void postinit(final FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register((Object) this);
		ClientRegistry.registerKeyBinding(this.toggle = new KeyBinding("Toggle automovement", 19, "key.categories.movement"));
		this.mc = Minecraft.getMinecraft();
		
		ClientCommandHandler.instance.registerCommand(new Command());
		ClientCommandHandler.instance.registerCommand(new DetectTeamCommand());
		ClientCommandHandler.instance.registerCommand(new AutoQuitCommand());
		
		MinecraftForge.EVENT_BUS.register((Object)new ChatMonitor());
	}
	
	//#### START OF ONTICK ####
	@SubscribeEvent
	public void onTick(final TickEvent.ClientTickEvent e) {
		// The movements
		final int keySprint = this.mc.gameSettings.keyBindSprint.getKeyCode();
		final int keyJump = this.mc.gameSettings.keyBindJump.getKeyCode();
		final int keyForward = this.mc.gameSettings.keyBindForward.getKeyCode();
		final int keyBackwards = this.mc.gameSettings.keyBindBack.getKeyCode();
		final int keyAttack = this.mc.gameSettings.keyBindAttack.getKeyCode();
		
		keyToggle = this.toggle.getKeyCode();
		
		//Toggle key
		if (this.toggle.isPressed()) {
			AutoMove.active = !AutoMove.active;
			if (!AutoMove.active && keySprint > 0) {
				final KeyBinding toggle = this.toggle;
				KeyBinding.setKeyBindState(keySprint, Keyboard.isKeyDown(keySprint));
				KeyBinding.setKeyBindState(keyJump, Keyboard.isKeyDown(keyJump));
				KeyBinding.setKeyBindState(keyForward, false);
				KeyBinding.setKeyBindState(keyBackwards, false);
				KeyBinding.setKeyBindState(keyAttack, false);
			}
		}
		
		
		if (AutoMove.active) {
			final KeyBinding toggle2 = this.toggle;
			int playerRotation = MathHelper.floor_double((double) (mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			double currentX = round(mc.thePlayer.posX, 1);
			double currentZ = round(mc.thePlayer.posZ, 1);
			int posAcu = 1;

			BlockPos posStart = new BlockPos(mc.thePlayer.getPositionVector());
			KeyBinding.setKeyBindState(keySprint, true);

			// +Z = 0 SOUTH
			// -X = 1 WEST
			// -Z = 2 NORTH
			// +X = 3 EAST
			
			if(bridgeMode == "FOURTEAM") {
				if (playerRotation == 0) {
					if (currentZ < finishZ - 0.1) { //Works as it should
						KeyBinding.setKeyBindState(keyForward, true);
						
					} else if (currentZ > finishZ + 0.1){
						KeyBinding.setKeyBindState(keyBackwards, true);
					} else {
						KeyBinding.setKeyBindState(keyForward, false);
						KeyBinding.setKeyBindState(keyBackwards, false);
						if (currentX < finishX) {
							mc.thePlayer.setAngles(-600, 0);	
						}else if (currentX > finishX) {
							mc.thePlayer.setAngles(600, 0);	
						}
					}
				}else if(playerRotation == 3) {
					if (currentX < finishX - 0.1) {
						KeyBinding.setKeyBindState(keyForward, true);
					} else if (currentX > finishX + 0.1){
						KeyBinding.setKeyBindState(keyBackwards, true);
					} else {
						KeyBinding.setKeyBindState(keyForward, false);
						KeyBinding.setKeyBindState(keyBackwards, false);
						if (currentZ > finishZ) {
							mc.thePlayer.setAngles(-600, 0);			
						}else if (currentZ < finishZ) {
							mc.thePlayer.setAngles(600, 0);	
						}
					}
				}else if(playerRotation == 1) { 
					if (currentX > finishX + 0.9) { 
						KeyBinding.setKeyBindState(keyForward, true);
					} else if (currentX < finishX - 0.9){
						KeyBinding.setKeyBindState(keyBackwards, true);
					} else {
						KeyBinding.setKeyBindState(keyForward, false);
						KeyBinding.setKeyBindState(keyBackwards, false);
						if (currentZ < finishZ) {
							mc.thePlayer.setAngles(-600, 0);			
						}else if (currentZ > finishZ) {
							mc.thePlayer.setAngles(600, 0);	
						}
					}
				}else if(playerRotation == 2) {
					if (currentZ > finishZ + 0.9) {
						KeyBinding.setKeyBindState(keyForward, true);
					} else if (currentZ < finishZ - 0.9){
						KeyBinding.setKeyBindState(keyBackwards, true);
					} else {
						KeyBinding.setKeyBindState(keyForward, false);
						KeyBinding.setKeyBindState(keyBackwards, false);
						if (currentX > finishX) {
							mc.thePlayer.setAngles(-600, 0);			
						}else if (currentX < finishX) {
							mc.thePlayer.setAngles(600, 0);	
						}
					}
				}
			} else if (bridgeMode == "TWOTEAM") {
				KeyBinding.setKeyBindState(keyForward, true);
			}
			
			
			//This is for jump over gaps
			if (cooldown != 0) {
				cooldown -= 1;
			}
			if (playerRotation == 0) {
				if (mc.theWorld.getBlockState(posStart.down().south()).getBlock() == Blocks.air) {
					if (cooldown == 0) {
						KeyBinding.setKeyBindState(keyJump, true);
						cooldown = 30;
					} else if (cooldown == 25) {
						KeyBinding.setKeyBindState(keyJump, false);
					}
				}

			} else if (playerRotation == 1) {
				if (mc.theWorld.getBlockState(posStart.down().west()).getBlock() == Blocks.air) {
					if (cooldown == 0) {
						KeyBinding.setKeyBindState(keyJump, true);
						cooldown = 30;
					} else if (cooldown == 25) {
						KeyBinding.setKeyBindState(keyJump, false);
					}
				}
			} else if (playerRotation == 2) {
				if (mc.theWorld.getBlockState(posStart.down().north()).getBlock() == Blocks.air) {
					if (cooldown == 0) {
						KeyBinding.setKeyBindState(keyJump, true);
						cooldown = 30;
					} else if (cooldown == 25) {
						KeyBinding.setKeyBindState(keyJump, false);
					}
				}

			} else if (playerRotation == 3) {
				if (mc.theWorld.getBlockState(posStart.down().east()).getBlock() == Blocks.air) {
					if (cooldown == 0) {
						KeyBinding.setKeyBindState(keyJump, true);
						cooldown = 30;
					} else if (cooldown == 25) {
						KeyBinding.setKeyBindState(keyJump, false);
					}
				}
			}
			
			//Detect if the player has fallen
			if (mc.thePlayer.posY < 70) {
				System.out.println("Player has fallen....");
				KeyBinding.setKeyBindState(keyBackwards, false);
				KeyBinding.setKeyBindState(keyAttack, false);
				if(bridgeMode == "FOURTEAM") {
					randomTeamPicker();
				}

			}
			
			//Jump over block or break block if in the way 
			if (round(mc.thePlayer.posX, posAcu) == round(mc.thePlayer.lastTickPosX, posAcu)
					&& round(mc.thePlayer.posZ, posAcu) == round(mc.thePlayer.lastTickPosZ, posAcu)) {
				if (jumptries < 30) {
					KeyBinding.setKeyBindState(keyJump, true);
					jumptries += 1;
				} else {
					if (jumptries == 31) {
						KeyBinding.setKeyBindState(keyJump, false);
					}
					KeyBinding.setKeyBindState(keyAttack, true);

					jumptries += 1;
					if (jumptries == 80) {
						jumptries = 0;
					}
				}

			} else {

				jumptries = 0;
				KeyBinding.setKeyBindState(keyAttack, false);
			}

		}
		
	}
	//#### END OF ONTICK ####
	
	//Used to pick a team to target in the game
	public static void randomTeamPicker() {
		int number = rand.nextInt(4) + 1;
		
		finishX = picker.getX(number);
		finishZ = picker.getZ(number);
		if(finishX == 808) { //Checks for error code
			System.out.println("Team matches target, retrying...");
			randomTeamPicker();
		}
	}

	//Function to round doubles to a given decimal place
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	static {
		AutoMove.active = false;
	}
}
