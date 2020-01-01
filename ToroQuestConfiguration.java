// ---

package net.torocraft.toroquest.config;

import java.io.File;
import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.torocraft.toroquest.ToroQuest;
import net.torocraft.toroquest.civilization.CivilizationType;
import net.torocraft.toroquest.civilization.Province;

public class ToroQuestConfiguration {

	private static final String CATEGORY = "ToroQuest Settings";
	private static final String CATEGORY_TRADES = "trades";
	private static Configuration config;

	public static int structureSpawnChance = 1500;
	public static int structureMinDistance = 500;
	public static int structureMinDistanceBetweenSame = 4000;
	public static float toroHealthMultiplier = 1;
	public static float toroAttackDamageMultiplier = 1;
	public static int toroSpawnChance = 16;
	public static float bossHealthMultiplier = 1;
	public static float bossAttackDamageMultiplier = 1;
	public static boolean cropsAffectRep = true;
	public static boolean animalsAffectRep = true;
	public static boolean specificEntityNames = true;
	public static int maxBandits = 16;
	public static int banditSpawnRate = 16;
	
	public static String[] tradeList = new String[]{};
	public static ArrayList<Trade> trades = new ArrayList<Trade>();

	public static void init(File configFile)
	{
		if (config == null)
		{
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}

	private static void loadConfiguration()
	{
		try {
			
			structureSpawnChance = config.getInt("structureSpawnChance", CATEGORY, 1500, 100, 100000,
					"One out of X chance to spawn a special structure");

			structureMinDistance = config.getInt("structureMinDistance", CATEGORY, 500, 100, 100000,
					"The minimum distance allowed between to special structures");

			structureMinDistanceBetweenSame = config.getInt("structureMinDistanceBetweenSame", CATEGORY, 4000, 100, 100000,
					"The minimum distance allowed between to of the same special structures");

			toroHealthMultiplier = config.getFloat("toroHealthMultiplier", CATEGORY, 1, 0.01f, 100f, "Toro health multipler");

			toroAttackDamageMultiplier = config.getFloat("toroAttackDamageMultiplier", CATEGORY, 1, 0.01f, 100f, "Toro damage multipler");
			
			toroSpawnChance = config.getInt("toroSpawnChance", CATEGORY, 16, 0, 100, "Chance for a Toro to spawn when a cow is spawned, setting to 0 will disable spawning");
			
			maxBandits = config.getInt("maxBandits", CATEGORY, 16, 0, 100, "max number of bandits allowed to be spawned per village, setting to 0 WILL disable spawning");
			
			banditSpawnRate = config.getInt("banditSpawnRate", CATEGORY, 16, 0, 100, "every 500 ticks, the chance of a random village **with a player visiting** to spawn bandits, out of 100."
					+ " This chance increased by 2 per player online, up to a max of 20. Setting to 0 will NOT disable spawning!");

			bossHealthMultiplier = config.getFloat("bossHealthMultiplier", CATEGORY, 1, 0.01f, 100f, "ToroQuest boss health multipler");

			bossAttackDamageMultiplier = config.getFloat("bossAttackDamageMultiplier", CATEGORY, 1, 0.01f, 100f, "ToroQuest boss damage multipler");

			cropsAffectRep = config.getBoolean("cropsAffectRep", CATEGORY, true, "Gain rep when planting crops and lose it when harvesting");

			animalsAffectRep = config.getBoolean("animalsAffectRep", CATEGORY, true, "Gain rep when breeding animals and lose it when killing them");

			specificEntityNames = config.getBoolean("specificEntityNames", CATEGORY, true,
					"Use specific entity names to improve mod compatabilty, CHANGING THIS WILL REMOVE CURRENT TQ ENTITIES IN YOUR WORLD!");

			
			
			
			tradeList = config.getStringList("tradeList", CATEGORY_TRADES,
					
		    new String[]
		    {
		    	"minecraft:wheat,16,x,minecraft:emerald,1,x,-300,farmer",
		    	"minecraft:emerald,5,x,minecraft:potion,1,x,-300,farmer,minecraft:healing~minecraft:splash_potion",
		    	"minecraft:wheat,16,x,minecraft:emerald,1,x,-300,farmer",
		    	"minecraft:wheat,10,x,minecraft:emerald,1,EARTH,0,farmer",
		    	"minecraft:wheat,10,x,minecraft:emerald,1,x,0,farmer",
		    	"minecraft:ender_pearl,6,minecraft:stick,minecraft:emerald,2,ANY,0,farmer",
		    	"minecraft:emerald,56,x,minecraft:enchanted_book,1,x,50,farmer,minecraft:smite~5~minecraft:fire_aspect~2",
		    	"minecraft:emerald,16,x,minecraft:enchanted_book,1,x,50,farmer,minecraft:smite~5~minecraft:fire_aspect~2",
		    	"minecraft:emerald,16,x,minecraft:golden_sword,1,x,100,farmer,Sword of Righteousness~minecraft:smite~5~minecraft:fire_aspect~5",
		    },
					
			"A list of trades for villagers. Follow the format to add trades or remove them if you'd like:"
			+ "\n\nitemToSell,amount,optionalItemToSell,itemToBuy,amount,minRepRequired,province,job\n\n"
			+ "\n\nFor enchanted items, use the following format: \n\n" 
			+ "\n\nitemToSell,amount,secondItemToSell,itemToBuy,amount,minRepRequired,province,job,enchantedItemName~enchantment~power\n\n" 
			+ "You can add as many additional enchantments as you's like. For enchanted books, do:"
			+ "\n\nitemToSell,amount,secondItemToSell,minecraft:enchanted_book,amount,minRepRequired,province,job~enchantment~power\n\n" 
			+ "make sure there are at least total of 7 entries, separated with commas and no spaces! You can add items and"
			+ "blocks from other mods too! Be sure to just put x for secondItemToSell if you do not want to have a second item in the trade."
			+ " \n \n List of province options are: x, EARTH, WIND, FIRE, WATER, SUN, MOON\n\n"
			+ "List of jobs are:"
			+ "\n\nminer, builder");
							
			for ( String s : tradeList )
			{
				try
				{
					String[] list = s.split(",");
					Trade trade = new Trade();
					trade.sellName = list[0];
					trade.sellAmount = list[1];
					trade.sellOptional = list[2];
					trade.buyName = list[3];
					trade.buyAmount = list[4];
					trade.province = list[5];
					trade.minimunRepRequired = list[6];
					trade.job = list[7];
					try
					{
						trade.enchantment = list[8];
					}
					catch(Exception e)
					{
						trade.enchantment = null;
					}
					trades.add(trade);
				}
				catch ( Exception e )
				{
					System.out.print("Trades config incorrect format! It follows a strict format. Error:" + e );
				}
			}
			
			config.save();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equalsIgnoreCase(ToroQuest.MODID))
		{
			loadConfiguration();
		}
	}
	
	@Nullable
	public static class Trade
	{
		public String sellName = "";
		public String sellAmount = "";
		public String sellOptional = "";
		public String buyName = "";
		public String buyAmount = "";
		public String minimunRepRequired = "";
		public String province = "";
		public String job = "";
		public String enchantment = null;
	}
	
	
	
//
//String someInputThingy = config.get(CATEGORY_TRADES, "Material List", "Herp, Derp, HerpDerp, DerpHerp").getString();
//	String[] toStringArray = someInputThingy.split(",");
//	for(int i = 0; i < toStringArray.length; i++) System.out.println(toStringArray[i]);
//	
	
	
	
}
