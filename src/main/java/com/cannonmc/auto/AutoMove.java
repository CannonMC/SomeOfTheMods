package com.cannonmc.auto;

import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
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
		ClientRegistry
				.registerKeyBinding(this.toggle = new KeyBinding("Toggle automovement", 19, "key.categories.movement"));
		this.mc = Minecraft.getMinecraft();
	}

	@Mod.EventHandler
	public static void init(FMLServerStartingEvent event) {
		event.registerServerCommand(new Command());
	}

	@SubscribeEvent
	public void onTick(final TickEvent.ClientTickEvent e) {

		// The movements
		final int keySprint = this.mc.gameSettings.keyBindSprint.getKeyCode();
		final int keyJump = this.mc.gameSettings.keyBindJump.getKeyCode();
		final int keyForward = this.mc.gameSettings.keyBindForward.getKeyCode();
		final int keyBackwards = this.mc.gameSettings.keyBindBack.getKeyCode();
		final int keyAttack = this.mc.gameSettings.keyBindAttack.getKeyCode();
		final int keyLeft = this.mc.gameSettings.keyBindLeft.getKeyCode();
		final int keyRight = this.mc.gameSettings.keyBindRight.getKeyCode();

		if (this.toggle.isPressed()) {
			AutoMove.active = !AutoMove.active;
			if (!AutoMove.active && keySprint > 0) {
				final KeyBinding toggle = this.toggle;
				KeyBinding.setKeyBindState(keySprint, Keyboard.isKeyDown(keySprint));
				KeyBinding.setKeyBindState(keyJump, Keyboard.isKeyDown(keyJump));
				KeyBinding.setKeyBindState(keyForward, false);
				KeyBinding.setKeyBindState(keyLeft, false);
				KeyBinding.setKeyBindState(keyRight, false);
				KeyBinding.setKeyBindState(keyBackwards, false);
				KeyBinding.setKeyBindState(keyAttack, false);

			}
		}
		if (AutoMove.active) {
			final KeyBinding toggle2 = this.toggle;
			int playerRotation = MathHelper.floor_double((double) (mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D)
					& 3;

			mc.thePlayer
					.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + Integer.toString(playerRotation)));
			double currentX = round(mc.thePlayer.posX, 0);
			double currentZ = round(mc.thePlayer.posZ, 0);
			int posAcu = 1;

			BlockPos posStart = new BlockPos(mc.thePlayer.getPositionVector());
			KeyBinding.setKeyBindState(keySprint, true);
			// +Z = 0 SOUTH
			// -X = 1 WEST
			// -Z = 2 NORTH
			// +X = 3 EAST

			// System.out.println(posStart);
			
			

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

			if (currentZ != finishZ || currentX != finishX) {
				if (playerRotation == 0 || playerRotation == 2) {
					// Handles the Z axis
					if (currentZ != finishZ) {
						if (playerRotation == 0) {
							if (currentZ < finishZ) {
								KeyBinding.setKeyBindState(keyForward, true);
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyBackwards, false);
								KeyBinding.setKeyBindState(keyRight, false);
							} else {
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyBackwards, true);
								KeyBinding.setKeyBindState(keyRight, false);
							}

						} else if (playerRotation == 2) {
							if (currentZ < finishZ) {
								KeyBinding.setKeyBindState(keyBackwards, true);
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyRight, false);
							} else {
								KeyBinding.setKeyBindState(keyBackwards, false);
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyForward, true);
								KeyBinding.setKeyBindState(keyRight, false);
							}

						}
						// Handles the X axis
					} else if (currentX != finishX) {
						if (playerRotation == 0) {
							if (currentX < finishX) {
								KeyBinding.setKeyBindState(keyLeft, true);
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyBackwards, false);
								KeyBinding.setKeyBindState(keyRight, false);
							} else {
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyBackwards, false);
								KeyBinding.setKeyBindState(keyRight, true);
							}

						} else if (playerRotation == 2) {
							if (currentX < finishX) {
								KeyBinding.setKeyBindState(keyRight, true);
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyBackwards, false);
							} else {
								KeyBinding.setKeyBindState(keyRight, false);
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyLeft, true);
								KeyBinding.setKeyBindState(keyBackwards, false);
							}

						}
					}

				} else {
					if (currentX != finishX) {
						if (playerRotation == 1) {
							if (currentX > finishX) {
								KeyBinding.setKeyBindState(keyForward, true);
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyBackwards, false);
								KeyBinding.setKeyBindState(keyRight, false);
							} else {
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyBackwards, true);
								KeyBinding.setKeyBindState(keyRight, false);
							}

						} else if (playerRotation == 3) {
							if (currentX > finishX) {
								KeyBinding.setKeyBindState(keyBackwards, true);
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyRight, false);
							} else {
								KeyBinding.setKeyBindState(keyBackwards, false);
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyForward, true);
								KeyBinding.setKeyBindState(keyRight, false);
							}

						}
					}else if (currentZ != finishZ) {
						if (playerRotation == 1) {
							if (currentZ < finishZ) {
								KeyBinding.setKeyBindState(keyLeft, true);
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyBackwards, false);
								KeyBinding.setKeyBindState(keyRight, false);
							} else {
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyBackwards, false);
								KeyBinding.setKeyBindState(keyRight, true);
							}

						} else if (playerRotation == 3) { ////
							if (currentZ < finishZ) {
								KeyBinding.setKeyBindState(keyRight, true);
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyLeft, false);
								KeyBinding.setKeyBindState(keyBackwards, false);
							} else {
								KeyBinding.setKeyBindState(keyRight, false);
								KeyBinding.setKeyBindState(keyForward, false);
								KeyBinding.setKeyBindState(keyLeft, true);
								KeyBinding.setKeyBindState(keyBackwards, false);
							}
						}
					}

				}

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
					// KeyBinding.setKeyBindState(keyJump, false);
					KeyBinding.setKeyBindState(keyAttack, false);
				}

			} else {

				KeyBinding.setKeyBindState(keyRight, false);
				KeyBinding.setKeyBindState(keyForward, false);
				KeyBinding.setKeyBindState(keyLeft, false);
				KeyBinding.setKeyBindState(keyBackwards, false);
			}
		}
	}

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
