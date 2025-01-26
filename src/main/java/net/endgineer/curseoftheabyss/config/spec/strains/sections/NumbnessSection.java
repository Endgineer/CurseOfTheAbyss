package net.endgineer.curseoftheabyss.config.spec.strains.sections;

import net.minecraftforge.common.ForgeConfigSpec;
import java.util.regex.Pattern;

public class NumbnessSection {
    public final String DEFAULT_LAYER1_RANGE = "[0, 0]";
    public final ForgeConfigSpec.ConfigValue<String> LAYER1_RANGE;

    public final String DEFAULT_LAYER2_RANGE = "[0, 0]";
    public final ForgeConfigSpec.ConfigValue<String> LAYER2_RANGE;

    public final String DEFAULT_LAYER3_RANGE = "[0, 0]";
    public final ForgeConfigSpec.ConfigValue<String> LAYER3_RANGE;

    public final String DEFAULT_LAYER4_RANGE = "[0, 0]";
    public final ForgeConfigSpec.ConfigValue<String> LAYER4_RANGE;

    public final String DEFAULT_LAYER5_RANGE = "[0, 0]";
    public final ForgeConfigSpec.ConfigValue<String> LAYER5_RANGE;

    public final String DEFAULT_LAYER6_RANGE = "[0, 0]";
    public final ForgeConfigSpec.ConfigValue<String> LAYER6_RANGE;

    public final String DEFAULT_LAYER7_RANGE = "[0, 0]";
    public final ForgeConfigSpec.ConfigValue<String> LAYER7_RANGE;

    public NumbnessSection(ForgeConfigSpec.Builder builder) {
        builder.push("NUMBNESS");

        LAYER1_RANGE = builder.comment(
            "The range which defines the approximate amount of numbness sustained every tick per block ascended in the 1st layer.\n"+
            "Values: {\"[m, M]\" | 0 <= m <= M <= "+Double.MAX_VALUE+"}\n"+
            "Default: \""+DEFAULT_LAYER1_RANGE+"\"")
            .define("LAYER1_RANGE", DEFAULT_LAYER1_RANGE, value -> value != null && Pattern.matches("\\[\\s*(\\d+(\\.(\\d)+){0,1}),\\s*(\\d+(\\.(\\d)+){0,1})\\s*\\]", (String) value) && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) >= 0 && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) <= Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[1]));
        
        LAYER2_RANGE = builder.comment(
            "The range which defines the approximate amount of numbness sustained every tick per block ascended in the 2nd layer.\n"+
            "Values: {\"[m, M]\" | 0 <= m <= M <= "+Double.MAX_VALUE+"}\n"+
            "Default: \""+DEFAULT_LAYER2_RANGE+"\"")
            .define("LAYER2_RANGE", DEFAULT_LAYER2_RANGE, value -> value != null && Pattern.matches("\\[\\s*(\\d+(\\.(\\d)+){0,1}),\\s*(\\d+(\\.(\\d)+){0,1})\\s*\\]", (String) value) && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) >= 0 && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) <= Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[1]));
        
        LAYER3_RANGE = builder.comment(
            "The range which defines the approximate amount of numbness sustained every tick per block ascended in the 3rd layer.\n"+
            "Values: {\"[m, M]\" | 0 <= m <= M <= "+Double.MAX_VALUE+"}\n"+
            "Default: \""+DEFAULT_LAYER3_RANGE+"\"")
            .define("LAYER3_RANGE", DEFAULT_LAYER3_RANGE, value -> value != null && Pattern.matches("\\[\\s*(\\d+(\\.(\\d)+){0,1}),\\s*(\\d+(\\.(\\d)+){0,1})\\s*\\]", (String) value) && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) >= 0 && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) <= Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[1]));
        
        LAYER4_RANGE = builder.comment(
            "The range which defines the approximate amount of numbness sustained every tick per block ascended in the 4th layer.\n"+
            "Values: {\"[m, M]\" | 0 <= m <= M <= "+Double.MAX_VALUE+"}\n"+
            "Default: \""+DEFAULT_LAYER4_RANGE+"\"")
            .define("LAYER4_RANGE", DEFAULT_LAYER4_RANGE, value -> value != null && Pattern.matches("\\[\\s*(\\d+(\\.(\\d)+){0,1}),\\s*(\\d+(\\.(\\d)+){0,1})\\s*\\]", (String) value) && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) >= 0 && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) <= Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[1]));
        
        LAYER5_RANGE = builder.comment(
            "The range which defines the approximate amount of numbness sustained every tick per block ascended in the 5th layer.\n"+
            "Values: {\"[m, M]\" | 0 <= m <= M <= "+Double.MAX_VALUE+"}\n"+
            "Default: \""+DEFAULT_LAYER5_RANGE+"\"")
            .define("LAYER5_RANGE", DEFAULT_LAYER5_RANGE, value -> value != null && Pattern.matches("\\[\\s*(\\d+(\\.(\\d)+){0,1}),\\s*(\\d+(\\.(\\d)+){0,1})\\s*\\]", (String) value) && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) >= 0 && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) <= Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[1]));
        
        LAYER6_RANGE = builder.comment(
            "The range which defines the approximate amount of numbness sustained every tick per block ascended in the 6th layer.\n"+
            "Values: {\"[m, M]\" | 0 <= m <= M <= "+Double.MAX_VALUE+"}\n"+
            "Default: \""+DEFAULT_LAYER6_RANGE+"\"")
            .define("LAYER6_RANGE", DEFAULT_LAYER6_RANGE, value -> value != null && Pattern.matches("\\[\\s*(\\d+(\\.(\\d)+){0,1}),\\s*(\\d+(\\.(\\d)+){0,1})\\s*\\]", (String) value) && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) >= 0 && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) <= Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[1]));
        
        LAYER7_RANGE = builder.comment(
            "The range which defines the approximate amount of numbness sustained every tick per block ascended in the 7th layer.\n"+
            "Values: {\"[m, M]\" | 0 <= m <= M <= "+Double.MAX_VALUE+"}\n"+
            "Default: \""+DEFAULT_LAYER7_RANGE+"\"")
            .define("LAYER7_RANGE", DEFAULT_LAYER7_RANGE, value -> value != null && Pattern.matches("\\[\\s*(\\d+(\\.(\\d)+){0,1}),\\s*(\\d+(\\.(\\d)+){0,1})\\s*\\]", (String) value) && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) >= 0 && Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[0]) <= Double.parseDouble(((String) value).replaceAll("\\s|\\[|\\]", "").split(",")[1]));

        builder.pop();
    }
}
