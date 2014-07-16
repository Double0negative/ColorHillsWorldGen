package org.mcsg.worldgen.gen;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.mcsg.worldgen.gen.noise.SimplexNoise;

public class ColorHillsGenerator extends ChunkGenerator{

  private static SimplexNoise noise_h;
  private static final int RAND_HILL_SIZE = 500;
  private static final int BASE_HILL_SIZE = 500;
  private static final double PERSISTANCE_MAX = 0.7;

  private static HashMap<Integer, SimplexNoise> noise_map = new HashMap<>();
  private static Random rand = new Random();
  static {
    noise_h = new SimplexNoise(rand.nextInt(RAND_HILL_SIZE) + BASE_HILL_SIZE, rand.nextDouble() * PERSISTANCE_MAX, rand.nextInt());
    noise_map.put(1,  new SimplexNoise(rand.nextInt(RAND_HILL_SIZE) + BASE_HILL_SIZE, rand.nextDouble() * PERSISTANCE_MAX, rand.nextInt()));
    noise_map.put(2,  new SimplexNoise(rand.nextInt(RAND_HILL_SIZE) + BASE_HILL_SIZE, rand.nextDouble() * PERSISTANCE_MAX, rand.nextInt()));
    noise_map.put(3,  new SimplexNoise(rand.nextInt(RAND_HILL_SIZE) + BASE_HILL_SIZE, rand.nextDouble() * PERSISTANCE_MAX, rand.nextInt()));
    noise_map.put(4,  new SimplexNoise(rand.nextInt(RAND_HILL_SIZE) + BASE_HILL_SIZE, rand.nextDouble() * PERSISTANCE_MAX, rand.nextInt()));
    //noise_map.put(5,  new SimplexNoise(rand.nextInt(45) + 100, rand.nextDouble() * 0.3, rand.nextInt()));
    //noise_map.put(6,  new SimplexNoise(rand.nextInt(45) + 100, rand.nextDouble() * 0.3, rand.nextInt())); //6 is a bit to much, causes much lag

  }


  public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid)
  {
    byte[][] result = new byte[world.getMaxHeight() / 16][]; //world height / chunk part height (=16, look above)
    int hmax = world.getMaxHeight();
    HashMap<Integer, double[][]> values = new HashMap<>();

    for(Entry<Integer, SimplexNoise> entry : noise_map.entrySet()){
      values.put(entry.getKey(), createNoise(16, 16, chunkX * 16, chunkZ * 16,chunkX * 16 + 16, chunkZ * 16 + 16,  entry.getValue()));
    }

    double[][] topd = createNoise(16, 16, chunkX * 16, chunkZ * 16,chunkX * 16 + 16, chunkZ * 16 + 16,  noise_h);

    for(int x = 0; x < 16; x++){
      for(int z = 0; z < 16; z++){
        TreeMap<Integer, Integer> sort = new TreeMap<>();
        int top = limit((int) (hmax * topd[x][z]), hmax, 2);

        for(Entry<Integer, SimplexNoise> entry : noise_map.entrySet()){
          int h = limit((int) (hmax * values.get(entry.getKey())[x][z]), hmax, 2);
          sort.put(h, entry.getKey());
        }

        int cur = sort.firstEntry().getValue();
        for(int y = 0; y < top; y++){
          if(sort.containsKey(y)){
            cur = sort.get(y);
          }
          setBlock(result, x, y, z, (byte) cur);
        }
        while(top <= 15){ setBlock(result, x, top, z, (byte) 9); top++;}
      }
    }

    return result;
  }

  @Override
  public List<BlockPopulator> getDefaultPopulators(World world) {
    return Arrays.asList(new ColorPopulator());
  }
  void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
    if (result[y >> 4] == null) {
      result[y >> 4] = new byte[4096];
    }
    result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
  }

  public static int limit (int val, int max, int min){
    return (val > max) ? max : val < min ? min : val;
  }

  public static double[][] createNoise(int width, int height, int xStart, int yStart, int endx, int endy, SimplexNoise noise){
    double[][] result=new double[width][height];


    for(int i=0;i<width;i++){
      for(int j=0;j<height;j++){
        int x=(int)(xStart+i*((endx-xStart)/width));
        int y=(int)(yStart+j*((endy-yStart)/height));
        result[i][j]=1*(noise.getNoise(x,y));
      }
    }
    return result;
  }


}
