package org.mcsg.worldgen;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcsg.worldgen.gen.ColorHillsGenerator;
/*
 *  Custom World gen by Double0negative. Source is free to use as you like, however
 *  credit, whether in source for open source projects or somewhere on the map or 
 *  description for closed source projects is appreciated. 
 *
 */
public class DoubleGen extends JavaPlugin{

  
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new ColorHillsGenerator();
	}
	
	@Override
	public void onEnable() {
		
	}
}
