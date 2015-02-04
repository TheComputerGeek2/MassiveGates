package com.massivecraft.massivegates.cmd;

import java.util.List;

import com.massivecraft.massivecore.cmd.MassiveCommandException;
import com.massivecraft.massivecore.cmd.req.ReqHasPerm;
import com.massivecraft.massivecore.util.PermUtil;
import com.massivecraft.massivegates.Gate;
import com.massivecraft.massivegates.GateCommand;
import com.massivecraft.massivegates.Permission;
import com.massivecraft.massivegates.cmdarg.ARAction;
import com.massivecraft.massivegates.cmdarg.ARTrigger;
import com.massivecraft.massivegates.cmdreq.ReqGateSelected;
import com.massivecraft.massivegates.ta.Action;
import com.massivecraft.massivegates.ta.Trigger;

public class CmdGateTaAdd extends GateCommand
{
	public CmdGateTaAdd()
	{
		this.addAliases("add");
		this.addRequiredArg("trigger");
		this.addRequiredArg("action");
		this.addOptionalArg("argument", "");
		this.setErrorOnToManyArgs(false);
		
		this.addRequirements(ReqGateSelected.get());
		this.addRequirements(new ReqHasPerm(Permission.TA_ADD.node));
	}
	
	@Override
	public void perform() throws MassiveCommandException
	{
		Gate gate = gme.getSelectedGate();
		
		// Fetch Trigger
		Trigger trigger = this.arg(0, ARTrigger.get());
		
		// Fetch the Action
		Action action = this.arg(1, ARAction.get());
				
		// Fetch the arg
		String arg = this.argConcatFrom(2);
		
		// Check the arg
		List<String> errors = action.checkArg(arg);
		if (errors != null && errors.size() > 0)
		{
			gme.msg(errors);
			return;
		}
		
		// TODO: merge this into the arg-check?
		// Do you have the permission to add this action?
		String perm = "massivegates.action."+action.getId();
		System.out.println(perm);
		System.out.println(sender);
		if ( ! sender.hasPermission(perm))
		{
			this.msg(PermUtil.getDeniedMessage(perm));
			return;
		}
		
		// Cool :) All is OK. Lets add it ^^
		gate.addAction(trigger, action, arg);
		this.msg("<i>Gate "+gate.getIdNameStringShort()+" <i>added: <lime>trigger <k>action <v>arg");
		this.msg("<lime>"+trigger.getName()+" <k>"+action.getName()+" <v>"+(arg==null?"*none*":arg));
	}
}