package cc.isotopestudio.privilege;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Privilege extends JavaPlugin {

    private static final String pluginName = "Privilege";
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("权限").append("]").append(ChatColor.RED).toString();

    static String levellore;
    static String classlore;
    static Map<String, String> classNameMap = new HashMap<>();

    static Privilege plugin;

    static PluginFile config;
//    public static PluginFile playerData;

    @Override
    public void onEnable() {
        plugin = this;
        config = new PluginFile(this, "config.yml", "config.yml");
        config.setEditable(false);

        levellore = config.getString("levellore");
        classlore = config.getString("classlore");
        if (config.isConfigurationSection("class")) {
            config.getConfigurationSection("class").getKeys(false).forEach(s -> {
                classNameMap.put(config.getString("class." + s), s);
            });
        }
        System.out.println("权限系统: [" + levellore + "], [" + classlore + "]");
        System.out.println(classNameMap);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        new CheckEquipTask().runTaskTimer(this, 20, 25);

        getLogger().info(pluginName + "成功加载!");
        getLogger().info(pluginName + "由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }

    public void onReload() {
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "成功卸载!");
    }

}
