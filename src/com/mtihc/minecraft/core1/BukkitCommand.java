package com.mtihc.minecraft.core1;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;

public abstract class BukkitCommand extends Command {

	private List<String> descriptionLong;
	private SimpleCommandMap nested;
	private BukkitCommand parent;
	
	private String argumentSyntax;
	
	protected BukkitCommand(String name, String shortDescription, String argumentSyntax, List<String> aliases)
	{
		super(name);
		description = shortDescription;
		if(aliases != null) {
			setAliases(aliases);
		}
		this.argumentSyntax = argumentSyntax;
	}

	/* (non-Javadoc)
	 * @see org.bukkit.command.Command#execute(org.bukkit.command.CommandSender, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		boolean help;
		try {
			help = (args[0].equalsIgnoreCase("help") || args[0].equals("?"));
		} catch(NullPointerException e) {
			help = false;
		} catch(IndexOutOfBoundsException e) {
			help = false;
		}
		
		if(help)
		{
			sender.sendMessage(ChatColor.GREEN + "Command usage: ");
			sender.sendMessage(ChatColor.GRAY + getUsage().replace(getName() + " ", ChatColor.WHITE + getName() + " " + ChatColor.GRAY));
			List<String> aliases = getAliases();
			if(aliases != null && !aliases.isEmpty()) {
				String aliasesString = "";
				
				for (String alias : aliases) {
					aliasesString += ", " + alias;
				}
				if(!aliasesString.isEmpty()) {
					aliasesString = aliasesString.substring(2);
				}
				sender.sendMessage(ChatColor.GREEN + "Aliases: " + ChatColor.WHITE + aliasesString);
			}
			
			
			if(hasLongDescription()) {
				sender.sendMessage(ChatColor.GREEN + "Description: ");
				List<String> desc = getLongDescription();
				for (String line : desc) {
					sender.sendMessage(line);
				}
			}
			else {
				sender.sendMessage(ChatColor.GREEN + "Description: " + ChatColor.WHITE + description);
			}
			return true;
		}
		else {
			if(nested == null) {
				return false;
			}
			return nested.dispatch(sender, joinArguments(args));
		}
		
	}
	
	private String joinArguments(String[] args) {
		String result = "";
		for (String element : args) {
			result += " " + element;
		}
		if(result.isEmpty()) {
			return result;
		}
		else {
			return result.substring(1);
		}
		
	}
	
	public void addNested(BukkitCommand command, Server server) {
		if(nested == null) {
			nested = new SimpleCommandMap(server);
		}
		nested.register(command.getName(), "", command);
		command.setParent(this);
	}
	
	
	
	public BukkitCommand getParent()
	{
		return parent;
	}
	
	private void setParent(BukkitCommand command) {
		this.parent = command;
	}
	
	@Override
	public String getUsage() {
		return "/" + getUniqueName() + " " + argumentSyntax;
	}
	
	public String getArgumentSyntax()
	{
		return argumentSyntax;
	}
	
	public String getUniqueName()
	{
		if(parent != null) {
			return parent.getUniqueName() + " " + this.getName();
		}
		else {
			return this.getName();
		}
	}
	
	public boolean hasLongDescription()
	{
		return descriptionLong != null;
	}
		
	
	public List<String> getLongDescription()
	{
		return descriptionLong;
	}
	
	public void setLongDescription(List<String> list)
	{
		descriptionLong = list;
	}
}
