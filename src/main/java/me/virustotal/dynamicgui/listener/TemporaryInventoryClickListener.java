package me.virustotal.dynamicgui.listener;

import java.util.ArrayList;
import java.util.UUID;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.nbt.NBTItem;
import me.virustotal.dynamicgui.objects.Function;
import me.virustotal.dynamicgui.util.FunctionUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TemporaryInventoryClickListener implements Listener {

	private DynamicGUI plugin;
	private static String tag = "DynamicGuiSlot";
	public TemporaryInventoryClickListener(DynamicGUI plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void invClick(final InventoryClickEvent e)
	{
		if(e.isCancelled())
			return;
		if(e.getClickedInventory() == null)
			return;
		if(e.getWhoClicked().getOpenInventory() == null)
			return;
		if(GuiApi.getTemporaryGuiForPlayer(e.getWhoClicked()) == null)
			return;

		e.setCancelled(true);
		
		ItemStack item = e.getClickedInventory().getItem(e.getSlot());
		if(item == null)
			return;

		Player player = (Player) e.getWhoClicked();

		GUI gui = GuiApi.getTemporaryGuiForPlayer(e.getWhoClicked());

		if(gui == null)
			return;

		Slot slot = null;
		for(int j = 0; j < gui.getSlots().size(); j++)
		{
			if(gui.getSlots().get(j) != null)
			{
				try
				{
					NBTItem nbtItem = new NBTItem(item);
					if(nbtItem.hasKey(tag))
					{
						UUID uuid = UUID.fromString(nbtItem.getString(tag));
						if(gui.getSlots().get(j) != null)
						{
							if(gui.getSlots().get(j).getUUID() != null)
							{
								if(gui.getSlots().get(j).getUUID().equals(uuid))
								{
									slot = gui.getSlots().get(j);
									break;
								}
							}
						}
					}
				}
				catch(SecurityException | IllegalArgumentException ex)
				{
					ex.printStackTrace();
				}
			}
		}

		if(slot == null)
			return;

		ArrayList<Function> functions = slot.getFunctions();
		ArrayList<Function> leftClickFunctions = slot.getLeftClickFunctions();
		ArrayList<Function> rightClickFunctions = slot.getRightClickFunctions();
		ArrayList<Function> middleClickFunctions = slot.getMiddleClickFunctions();
		
		if(functions == null && leftClickFunctions == null && rightClickFunctions == null && middleClickFunctions != null)
			return;


		Boolean close = null;
		if(slot.getClose() != null)
			close = slot.getClose();
		else if(gui.getClose() != null)
			close = gui.getClose();
		else
			close = true;

		if(close)
			player.closeInventory();
		
		
		FunctionUtil.tryFunctions(slot, e.getClick(), player);
	}
}