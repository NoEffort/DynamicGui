package com.clubobsidian.dynamicgui.event.player;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.PlayerEvent;

public class PlayerQuitEvent extends PlayerEvent {

	public PlayerQuitEvent(PlayerWrapper<?> playerWrapper) 
	{
		super(playerWrapper);
	}
}