package net.fumyatan.cgmpx.Manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fumyatan.cgmpx.Tools.PrefixAdder;

public class TeleportManager implements CommandExecutor {

	public static void safeTeleport(Player target, Location to){
		if (target.getGameMode().equals(GameMode.SURVIVAL)){
			boolean safe = false;
			int y = to.getBlockY();
			Block block = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y, to.getBlockZ());
			Block block2 = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y+2, to.getBlockZ());
			Block block3 = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y+3, to.getBlockZ());

			if (y >= 0 && block.getType().equals(Material.AIR)){
				// target.sendMessage(ChatColor.LIGHT_PURPLE + "Safeteleport Sequence Phase1");
				while (!safe) {
					block = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y, to.getBlockZ());
					block2 = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y+2, to.getBlockZ());
					block3 = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y+3, to.getBlockZ());
					if (!block.getType().equals(Material.AIR)){
						if (block2.getType().equals(Material.AIR) & block3.getType().equals(Material.AIR)){
							teleportPlayer(target, to.getWorld(), to.getBlockX(), y+1, to.getBlockZ());
							PrefixAdder.sendMessage(target, "Teleport!");
							safe = true;
							return;
						}
						y--;
					} else {
						if (y == 0){
							safe = true;
							continue;
						}
						// target.sendMessage("Search Block: " + y + block.getType());
						y--;
					}
				}

				safe = false;

				// target.sendMessage(ChatColor.LIGHT_PURPLE + "Safeteleport Sequence Phase2");
				while (!safe){
					block = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y, to.getBlockZ());
					block2 = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y+2, to.getBlockZ());
					block3 = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y+3, to.getBlockZ());
					if (!block.getType().equals(Material.AIR)){
						if (block2.getType().equals(Material.AIR) & block3.getType().equals(Material.AIR)){
							teleportPlayer(target, to.getWorld(), to.getBlockX(), y+1, to.getBlockZ());
							PrefixAdder.sendMessage(target, "Teleport!");
							safe = true;
							return;
						}
						y++;
					} else {
						if (y == 256){
							safe = true;
							continue;
						}
						// target.sendMessage("Search Block: " + y + block.getType());
						y++;
					}
				}
			} else {
				// target.sendMessage(ChatColor.LIGHT_PURPLE + "Safeteleport Sequence Phase3");
				while (!safe){
					block = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y, to.getBlockZ());
					block2 = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y+2, to.getBlockZ());
					block3 = Bukkit.getWorld(to.getWorld().getName()).getBlockAt(to.getBlockX(), y+3, to.getBlockZ());
					if (!block.getType().equals(Material.AIR)){
						if (block2.getType().equals(Material.AIR) & block3.getType().equals(Material.AIR)){
							teleportPlayer(target, to.getWorld(), to.getBlockX(), y+1, to.getBlockZ());
							PrefixAdder.sendMessage(target, "Teleport!");
							safe = true;
							return;
						}
						y++;
					} else {
						if (y == 256){
							safe = true;
							continue;
						}
						// target.sendMessage("Search Block: " + y + block.getType());
						y++;
					}
				}
			}
		} else {
			target.teleport(to);
		}
	}

	public static void teleportPlayer(Player target, World world, int x, int y, int z){
		Location loc = new Location(world, x, y, z);
		target.teleport(loc);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equals("tp")){
			/*if (args.length == 4){
				Player target = Bukkit.getPlayer(args[0]);
				int x = Integer.parseInt(args[1]);
				int y = Integer.parseInt(args[2]);
				int z = Integer.parseInt(args[3]);

			}
			*/
		} else {
			if (sender.hasPermission("cgmp.tp.here")){
				Player Sender = (Player) sender;
				Player target = Bukkit.getPlayer(args[0]);

				if (target != null){
					safeTeleport(target, Sender.getLocation());
				} else {
					PrefixAdder.sendMessage(sender, ChatColor.RED, "Player Not Found.");
				}
			} else {
				PrefixAdder.sendMessage(sender, ChatColor.RED, "You don't have Permission.");
			}
		}
		return false;
	}

}
