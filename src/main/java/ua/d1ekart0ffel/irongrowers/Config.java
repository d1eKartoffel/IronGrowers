package ua.d1ekart0ffel.irongrowers;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static ModConfigSpec COMMON_CONFIG;

    //COMMON

    //Growth Chances
    public static final ModConfigSpec.IntValue GROWTH_CHANCE_COPPER;
    public static final ModConfigSpec.IntValue GROWTH_CHANCE_IRON;
    public static final ModConfigSpec.IntValue GROWTH_CHANCE_GOLD;
    public static final ModConfigSpec.IntValue GROWTH_CHANCE_DIAMOND;
    public static final ModConfigSpec.IntValue GROWTH_CHANCE_NETHERITE;

    //Growth Interval
    public static final ModConfigSpec.DoubleValue GROWTH_INTERVAL_COPPER;
    public static final ModConfigSpec.DoubleValue GROWTH_INTERVAL_IRON;
    public static final ModConfigSpec.DoubleValue GROWTH_INTERVAL_GOLD;
    public static final ModConfigSpec.DoubleValue GROWTH_INTERVAL_DIAMOND;
    public static final ModConfigSpec.DoubleValue GROWTH_INTERVAL_NETHERITE;

    //Radius
    public static final ModConfigSpec.IntValue RADIUS_COPPER;
    public static final ModConfigSpec.IntValue RADIUS_IRON;
    public static final ModConfigSpec.IntValue RADIUS_GOLD;
    public static final ModConfigSpec.IntValue RADIUS_DIAMOND;
    public static final ModConfigSpec.IntValue RADIUS_NETHERITE;

    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

        GROWTH_CHANCE_COPPER = COMMON_BUILDER.comment("Chance of crop growing for Copper Grower")
                .defineInRange("growthChanceCopper", 10,0,100);
        GROWTH_CHANCE_IRON = COMMON_BUILDER.comment("Chance of crop growing for Iron Grower")
                .defineInRange("growthChanceIron", 20,0,100);
        GROWTH_CHANCE_GOLD = COMMON_BUILDER.comment("Chance of crop growing for Gold Grower")
                .defineInRange("growthChanceGold", 40,0,100);
        GROWTH_CHANCE_DIAMOND = COMMON_BUILDER.comment("Chance of crop growing for Diamond Grower")
                .defineInRange("growthChanceDiamond", 60,0,100);
        GROWTH_CHANCE_NETHERITE = COMMON_BUILDER.comment("Chance of crop growing for Netherite Grower")
                .defineInRange("growthChanceNetherite", 80,0,100);

        GROWTH_INTERVAL_COPPER = COMMON_BUILDER.comment("Time in seconds needed for next crop growing for Copper Grower")
                .defineInRange("growthIntervalCopper", 4,0,Double.MAX_VALUE);
        GROWTH_INTERVAL_IRON = COMMON_BUILDER.comment("Time in seconds needed for next crop growing for Iron Grower")
                .defineInRange("growthIntervalIron", 3,0,Double.MAX_VALUE);
        GROWTH_INTERVAL_GOLD = COMMON_BUILDER.comment("Time in seconds needed for next crop growing for Gold Grower")
                .defineInRange("growthIntervalGold", 2.5,0,Double.MAX_VALUE);
        GROWTH_INTERVAL_DIAMOND = COMMON_BUILDER.comment("Time in seconds needed for next crop growing for Diamond Grower")
                .defineInRange("growthIntervalDiamond", 1.5,0,Double.MAX_VALUE);
        GROWTH_INTERVAL_NETHERITE = COMMON_BUILDER.comment("Time in seconds needed for next crop growing for Netherite Grower")
                .defineInRange("growthIntervalNetherite", 1,0,Double.MAX_VALUE);

        RADIUS_COPPER = COMMON_BUILDER.comment("Radius of crop growing for Copper Grower")
                .defineInRange("growthRadiusCopper", 4,0,Integer.MAX_VALUE);
        RADIUS_IRON = COMMON_BUILDER.comment("Radius of crop growing for Iron Grower")
                .defineInRange("growthRadiusIron", 4,0,Integer.MAX_VALUE);
        RADIUS_GOLD = COMMON_BUILDER.comment("Radius of crop growing for Gold Grower")
                .defineInRange("growthRadiusGold", 4,0,Integer.MAX_VALUE);
        RADIUS_DIAMOND = COMMON_BUILDER.comment("Radius of crop growing for Diamond Grower")
                .defineInRange("growthRadiusDiamond", 4,0,Integer.MAX_VALUE);
        RADIUS_NETHERITE = COMMON_BUILDER.comment("Radius of crop growing for Netherite Grower")
                .defineInRange("growthRadiusNetherite", 4,0,Integer.MAX_VALUE);


        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}