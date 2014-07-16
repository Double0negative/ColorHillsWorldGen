package org.mcsg.worldgen.gen;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class ColorPopulator extends BlockPopulator{

  private static final HashMap<Integer, int[]> b_map = new HashMap<>();

  static {
    b_map.put(1, new int [] { Material.STAINED_CLAY.getId(),  1});
    b_map.put(2, new int [] { Material.STAINED_CLAY.getId(),  3});
    b_map.put(3, new int [] { Material.STAINED_CLAY.getId(),  14});
    b_map.put(4, new int [] { Material.STAINED_CLAY.getId(),  5});
    b_map.put(5, new int [] { Material.STAINED_CLAY.getId(),  6});
    b_map.put(6, new int [] { Material.STAINED_CLAY.getId(),  4});


  }
  @SuppressWarnings("deprecation")
  @Override
  public void populate(World world, Random random, Chunk chunk) {		
    for(int x = 0; x < 16; x++){
      for(int y = 0; y < world.getMaxHeight(); y++){
        for(int z = 0; z < 16; z++){
          Block block = chunk.getBlock(x, y, z);
          if(block.getTypeId() != 0){
            int[] temp = b_map.get(block.getTypeId());
            block.setTypeId(temp[0]);
            block.setData((byte) temp[1]);
          }
        }
      }
    }

  }

}
