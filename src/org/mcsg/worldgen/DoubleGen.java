package org.mcsg.worldgen;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcsg.worldgen.gen.ColorHillsGenerator;

public class DoubleGen extends JavaPlugin{

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new ColorHillsGenerator();
	}
	
	@Override
	public void onEnable() {
		
	}
}
