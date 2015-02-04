package com.massivecraft.massivegates.cmd;

import java.util.ArrayList;
import java.util.List;

import com.massivecraft.massivecore.cmd.MassiveCommandException;
import com.massivecraft.massivecore.cmd.arg.ARInteger;
import com.massivecraft.massivecore.cmd.req.ReqHasPerm;
import com.massivecraft.massivecore.util.Txt;
import com.massivecraft.massivegates.GateCommand;
import com.massivecraft.massivegates.Permission;
import com.massivecraft.massivegates.util.Fx;

public class CmdGateFxAlt extends GateCommand
{
	public CmdGateFxAlt()
	{
		this.addAliases("alt");
		this.addOptionalArg("page", "1");
		this.addRequirements(new ReqHasPerm(Permission.FX_ALT.node));
	}
	
	@Override
	public void perform() throws MassiveCommandException
	{
		Integer pageHumanBased = this.arg(0, ARInteger.get(), 1);
		
		List<String> lines = new ArrayList<String>();
		lines.add("<a># <i>There is one FX per line in this list.");
		lines.add("<a># <i>S = Sound, V = Visual, D = Data");
		
		for(Fx fx: Fx.values())
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append(fx.getHasVisual() ? "<g>" : "<b>");
			sb.append("V ");
			
			sb.append(fx.getHasSound() ? "<g>" : "<b>");
			sb.append("S ");
			
			sb.append(fx.getTakesData() ? "<g>" : "<b>");
			sb.append("D ");
			
			sb.append("<h>");
			sb.append(fx.getName());
			
			sb.append(" <i>");
			sb.append(fx.getDesc());
			lines.add(sb.toString());
		}
		
		lines = Txt.parseWrap(lines);
		this.sendMessage(Txt.getPage(lines, pageHumanBased, "Available Special FX", sender));
	}
}