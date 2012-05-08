package com.mtihc.minecraft.core1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class YamlFile {

	protected JavaPlugin plugin;
	private String name;
	private YamlConfiguration config = null;
	private File file;

	/**
	 * Constructor
	 * @param plugin The plugin
	 * @param name the filename without .yml extension
	 */
	public YamlFile(JavaPlugin plugin, String name) {
		if (plugin == null) {
			throw new NullPointerException("Parameter plugin must be non-null.");
		}
		if (name == null) {
			throw new NullPointerException("Parameter name must be non-null.");
		}
		this.plugin = plugin;
		this.name = name.trim();
		
		File dataFolder = plugin.getDataFolder();
		file = new File(dataFolder, this.name + ".yml");
	}
	
	/**
	 * Returns the name of the file
	 * @return The name of the file
	 */
	public String getName() { return name; }

	/**
	 * Returns the currently loaded configuration
	 * @return currently loaded config
	 */
	public YamlConfiguration getConfig() {
		if (config == null) {
			reload();
		}
		return config;
	}

	/**
	 * Loads the yml file
	 */
	public void reload() {
		try {
			config = YamlConfiguration.loadConfiguration(file);
			config.options().copyDefaults(true);
			
			InputStream defConfigStream = plugin.getResource(this.name + ".yml");
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
				config.setDefaults(defConfig);
				save();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the yml file
	 */
	public void save() {
		try {
			config.save(file);
		} catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE,
					"Could not save config to " + file, ex);
		}
	}
}
