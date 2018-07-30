package me.virustotal.dynamicgui.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.objects.CLocation;
import me.virustotal.dynamicgui.objects.ModeEnum;
import me.virustotal.dynamicgui.objects.SoundWrapper;

public class GuiBuilder  {
	
	private String name;
	private String title;
	private int rows;
	private String permission;
	private String pMessage;
	private Boolean close;
	private ModeEnum modeEnum;
	private List<Integer> npcIds = new ArrayList<Integer>();
	private List<Slot> slots = new ArrayList<Slot>();
	private List<CLocation> locs = new ArrayList<CLocation>();
	private List<SoundWrapper> openingSounds = new ArrayList<SoundWrapper>();
	
	public GuiBuilder setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public GuiBuilder setTitle(String title)
	{
		this.title = title;
		return this;
	}
	
	public GuiBuilder setRows(int rows)
	{
		this.rows = rows;
		return this;
	}
	
	public GuiBuilder setPermission(String permission)
	{
		this.permission = permission;
		return this;
	}
	
	public GuiBuilder setPermissionMessage(String pMessage)
	{
		this.pMessage = pMessage;
		return this;
	}
	
	public GuiBuilder setClose(Boolean close)
	{
		this.close = close;
		return this;
	}
	public GuiBuilder setModeEnum(String mode)
	{
		this.setModeEnum(ModeEnum.valueOf(mode));
		return this;
	}
	
	public GuiBuilder setModeEnum(ModeEnum modeEnum)
	{
		this.modeEnum = modeEnum;
		return this;
	}
	
	public GuiBuilder addNpcId(Integer id)
	{
		this.npcIds.add(id);
		return this;
	}
	
	public GuiBuilder addNpcId(Integer[] npcIds)
	{
		for(Integer id : npcIds)
		{
			this.npcIds.add(id);
		}
		return this;
	}
	
	public GuiBuilder addNpcId(ArrayList<Integer> npcIds)
	{
		for(Integer id : npcIds)
		{
			this.npcIds.add(id);
		}
		return this;
	}
	
	public GuiBuilder addSlot(Slot slot)
	{
		this.slots.add(slot);
		return this;
	}
	
	public GuiBuilder addLocation(Location loc)
	{
		this.locs.add(new CLocation(loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ()));
		return this;
	}
	
	public GUI build()
	{
		return new GUI(this.name, this.title, this.rows, this.permission, this.pMessage, this.close, this.modeEnum, this.npcIds, this.slots, this.locs, this.openingSounds);
	}
}