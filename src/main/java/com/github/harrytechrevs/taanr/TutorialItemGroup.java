package com.github.harrytechrevs.taanr;

import com.github.harrytechrevs.taanr.init.TutorialItems;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TutorialItemGroup extends ItemGroup
{
	public TutorialItemGroup() 
	{
		super("tutorial");
	}

	@Override
	public ItemStack createIcon() 
	{
		return new ItemStack(TutorialItems.tutorial_item);
	}
}
