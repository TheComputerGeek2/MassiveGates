package com.massivecraft.massivegates.cmd;

import java.util.ArrayList;
import java.util.List;

import com.massivecraft.massivecore.cmd.MassiveCommandException;
import com.massivecraft.massivecore.cmd.arg.ARInteger;
import com.massivecraft.massivecore.cmd.req.ReqHasPerm;
import com.massivecraft.massivecore.util.Txt;
import com.massivecraft.massivegates.GateCommand;
import com.massivecraft.massivegates.GateColl;
import com.massivecraft.massivegates.Permission;
import com.massivecraft.massivegates.ta.Action;
import com.massivecraft.massivegates.ta.Trigger;

public class CmdGateTaAlt extends GateCommand
{
	public CmdGateTaAlt()
	{
		this.addAliases("alt");
		this.addRequiredArg("trigger|action");
		this.addOptionalArg("page", "1");
		this.addRequirements(new ReqHasPerm(Permission.TA_ALT.node));
	}
	
	@Override
	public void perform() throws MassiveCommandException
	{
		Integer pageHumanBased = this.arg(1, ARInteger.get(), 1);
		
		List<String> lines = new ArrayList<String>();
		
		String title = null;
		
		if (this.arg(0).charAt(0) == 't')
		{
			title = "Trigger Alternatives";
			for(Trigger trigger : GateColl.i.getTriggers())
			{
				lines.add("<lime>"+trigger.getName()+" <i>"+trigger.getDesc());
			}
		}
		else
		{
			title = "Action Alternatives";
			for(Action action : GateColl.i.getActions())
			{
				lines.add("<k>"+action.getName()+" <i>"+action.getDesc());
			}
		}
		
		lines = Txt.parseWrap(lines);
		this.sendMessage(Txt.getPage(lines, pageHumanBased, title, sender));
	}
}