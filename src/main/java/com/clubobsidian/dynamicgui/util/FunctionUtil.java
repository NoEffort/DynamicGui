/*
   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.util;

import java.util.List;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.Click;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.manager.dynamicgui.FunctionManager;

public class FunctionUtil {
	
	public static boolean tryGuiFunctions(FunctionOwner owner, PlayerWrapper<?> playerWrapper)
	{
		FunctionResponse result = null;
		if(owner.getFunctions() != null)
		{
			result = tryFunctions(playerWrapper, owner.getFunctions(), owner);
			if(!result.result)
			{
				List<Function> failFunctions = owner.getFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(playerWrapper, failFunctions, owner);
				}
				return false;
			}
		}
		return true;
	}
	
	public static void tryLoadFunctions(PlayerWrapper<?> playerWrapper, Gui clonedGui) 
	{
		for(Slot slot : clonedGui.getSlots())
		{
			FunctionResponse result = FunctionUtil.tryFunctions(playerWrapper, slot.getLoadFunctions(), slot);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getLoadFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(playerWrapper, failFunctions, slot);
				}
			}
		}
		
	}

	public static void tryFunctions(Slot slot, Click inventoryClick, PlayerWrapper<?> playerWrapper)
	{
		FunctionResponse result = null;
		if(slot.getFunctions() != null)
		{
			result = tryFunctions(playerWrapper, slot.getFunctions(), slot);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(playerWrapper, failFunctions, slot);
				}
			}
		}
		if(inventoryClick == Click.LEFT && slot.getLeftClickFunctions() != null)
		{
			result = tryFunctions(playerWrapper, slot.getLeftClickFunctions(), slot);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getLeftClickFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(playerWrapper, failFunctions, slot);
				}

				return;
			}
		}
		else if(inventoryClick == Click.RIGHT && slot.getRightClickFunctions() != null)
		{
			result = tryFunctions(playerWrapper, slot.getRightClickFunctions(), slot);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getRightClickFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(playerWrapper, failFunctions, slot);
				}

				return;
			}
		}
		else if(inventoryClick == Click.MIDDLE && slot.getMiddleClickFunctions() != null)
		{
			result = tryFunctions(playerWrapper, slot.getMiddleClickFunctions(), slot);
			if(!result.result)
			{
				List<Function> failFunctions = slot.getMiddleClickFailFunctions(result.failedFunction);
				if(failFunctions != null)
				{
					tryFunctions(playerWrapper, failFunctions, slot);
				}

				return;
			}
		}
	}
	
	private static FunctionResponse tryFunctions(PlayerWrapper<?> playerWrapper, List<Function> functions, FunctionOwner owner)
	{
		FunctionResponse response = new FunctionResponse(true);
		for(int i = 0; i < functions.size(); i++)
		{
			Function func = functions.get(i);
			Function myFunc = null;
			try 
			{
				if(FunctionManager.get().getFunctionByName(func.getName()) == null)
				{
					DynamicGui.get().getLogger().info("Cannot find " + func.getName() + " continuing!");
					continue;
				}

				myFunc = FunctionManager.get().getFunctionByName(func.getName()).clone();
				myFunc.setData(func.getData());
				myFunc.setOwner(owner);
				myFunc.setIndex(i);
			} 
			catch (IllegalArgumentException | SecurityException ex) 
			{
				ex.printStackTrace();
			}
			
			//Runs the function
			boolean result = myFunc.function(playerWrapper);
			if(!result)
			{
				//If the function fails return a failed function response
				response = new FunctionResponse(false, func.getName());
				break;
			}
		}
		return response;
	}
	
	private static class FunctionResponse 
	{	
		private boolean result;
		private String failedFunction;
		
		public FunctionResponse(boolean result)
		{
			this(result, null);
		}
		
		public FunctionResponse(boolean result, String failedFunction)
		{
			this.result = result;
			this.failedFunction = failedFunction;
		}
	}
}
