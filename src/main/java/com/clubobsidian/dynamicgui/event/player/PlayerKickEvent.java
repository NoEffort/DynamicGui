package com.clubobsidian.dynamicgui.event.player;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.PlayerEvent;

public class PlayerKickEvent extends PlayerEvent {

	public PlayerKickEvent(PlayerWrapper<?> playerWrapper) 
	{
		super(playerWrapper);
	}
}