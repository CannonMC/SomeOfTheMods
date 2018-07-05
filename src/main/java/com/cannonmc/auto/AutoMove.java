package com.cannonmc.auto;

import org.lwjgl.input.Keyboard;

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

@Mod(modid = "am", version = "1.0", acceptedMinecraftVersions = "1.8")
public class AutoMove {
	private static boolean active;
	private KeyBinding toggle;
	public Minecraft mc;
	private EntityPlayerSP thePlayer;
	private boolean prevState;

	private int autotimer = 0;
	public int jumptries = 0;
	public int cooldown = 10;

	public static double finishX = 0;
	public static double finishZ = 0;

	public AutoMove() {
		this.prevState = false;
	}

	@Mod.EventHandler
	public void postinit(final FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register((Object) this);
		ClientRegistry.registerKeyBinding(this.toggle = new KeyBinding("Toggle automovement", 19, "key.categories.movement"));this.mc = Minecraft.getMinecraft();
		ClientCommandHandler.instance.registerCommand(new Command());
		MinecraftForge.EVENT_BUS.register((Object)new ChatMonitor());
	}

	@SubscribeEvent
	public void onTick(final TickEvent.ClientTickEvent e) {
		
		// The movements
		final int keySprint = this.mc.gameSettings.keyBindSprint.getKeyCode();
		final int keyJump = this.mc.gameSettings.keyBindJump.getKeyCode();
		final int keyForward = this.mc.gameSettings.keyBindForward.getKeyCode();
		final int keyBackwards = this.mc.gameSettings.keyBindBack.getKeyCode();
		final int keyAttack = this.mc.gameSettings.keyBindAttack.getKeyCode();

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
			
			double currentX = round(mc.thePlayer.posX, 0);
			double currentZ = round(mc.thePlayer.posZ, 0);
			int posAcu = 1;

			BlockPos posStart = new BlockPos(mc.thePlayer.getPositionVector());
			KeyBinding.setKeyBindState(keySprint, true);
			// +Z = 0 SOUTH
			// -X = 1 WEST
			// -Z = 2 NORTH
			// +X = 3 EAST
			
			//WIP direction
			int playerRotationTight = (int)mc.thePlayer.rotationYaw;
			if (playerRotationTight < 0) playerRotationTight += 360;
			playerRotationTight+=22;
			playerRotationTight%=360;
			int facing = playerRotationTight/ 45;
			
			//Prints out player rotation to chat
			mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + Integer.toString(playerRotation)));
			
			
			//For moving to a set point
			if (playerRotation == 0) {
				if (currentZ < finishZ) {
					KeyBinding.setKeyBindState(keyForward, true);
				} else if (currentZ > finishZ){
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
				if (currentX < finishX) {
					KeyBinding.setKeyBindState(keyForward, true);
				} else if (currentX > finishX){
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
				if (currentX > finishX) {
					KeyBinding.setKeyBindState(keyForward, true);
				} else if (currentX < finishX){
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
				if (currentZ > finishZ) {
					KeyBinding.setKeyBindState(keyForward, true);
				} else if (currentZ < finishZ){
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
			

			//Jump over air
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

	//Function to round doubles
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
