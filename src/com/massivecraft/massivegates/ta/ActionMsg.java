package com.massivecraft.massivegates.ta;

import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.Txt;
import com.massivecraft.massivegates.entity.Gate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import java.util.Collections;
import java.util.List;

public class ActionMsg extends BaseAction
{
	// -------------------------------------------- //
	// INTANCE AND CONSTRUCT
	// -------------------------------------------- //
	
	protected static ActionMsg i = new ActionMsg();
	public static ActionMsg get() { return i; }
	
	protected ActionMsg()
	{
		super("mgcore_msg", "Msg", "msg:message to the player.");
	}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public final static List<String> errors = Collections.singletonList("<b>You must enter a message to be sent!");
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform(String arg, Gate gate, Entity entity, Cancellable cancellable)
	{
		// Only for players
		if ( ! (entity instanceof Player)) return;
		Player player = (Player) entity;
		
		String message = Txt.parse(arg);
		MixinMessage.get().messageOne(player, message);
	}
	
	@Override
	public List<String> checkArg(String arg)
	{
		if (arg == null || arg.trim().length() == 0)
		{
			return errors;
		}
		return null;
	}
	
}
