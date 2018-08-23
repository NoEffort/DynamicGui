package com.clubobsidian.dynamicgui.plugin;

import java.io.File;
import java.util.List;

import com.clubobsidian.dynamicgui.economy.Economy;
import com.clubobsidian.dynamicgui.npc.NPCRegistry;

public interface DynamicGUIPlugin extends NPCRegistry {

	public void start();
	public void stop();
	public Economy getEconomy();
	public List<NPCRegistry> getNPCRegistries();
	public File getDataFolder();
	public File getConfigFile();
	public File getGuiFolder();
	public void createCommand(String guiName, String alias);
	
}