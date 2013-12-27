package com.ancientshores.AncientRPG.Quests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class AncientRPGQuest
{
	public final HashSet<Objective> objectives = new HashSet<Objective>();
	public String name;
	public int xp;
	public String description;
	public boolean fulfilled;

	public AncientRPGQuest(File f)
	{
		parseFile(f);
	}

	String line;
	public void parseFile(File f)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(f));
			line  = br.readLine();
			while(line != null)
			{
				if(line.toLowerCase().startsWith("name:"))
				{
					parseName(br);
				}
				else if(line.toLowerCase().startsWith("description:"))
				{
					parseDescription(br);
				}
				else if(line.toLowerCase().startsWith("xp:"))
				{
					parseXp(br);
				}
				else if(line.toLowerCase().startsWith("objectives:"))
				{
					parseObjectives(br);
				}
				else
				{
					line = br.readLine();
				}
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void parseName(BufferedReader br) throws IOException
	{
		String name = "";
		line = br.readLine();
		while(line != null)
		{
			if(line.toLowerCase().startsWith("description:") || line.toLowerCase().startsWith("xp:") || line.toLowerCase().startsWith("objectives:"))
				break;
			name += line + "\n";
			line = br.readLine();
		}
		this.name = name;
	}
	
	void parseDescription(BufferedReader br) throws IOException
	{
		String desc = "";
		line = br.readLine();
		while(line != null)
		{
			if(line.toLowerCase().startsWith("name:") || line.toLowerCase().startsWith("xp:") || line.toLowerCase().startsWith("objectives:"))
				break;
			desc += line + "\n";
			line = br.readLine();
		}
		this.description = desc;
	}
	
	void parseXp(BufferedReader br) throws IOException
	{
		line = br.readLine();
		if(line == null)
			return;
		try
		{
			this.xp = Integer.parseInt(line);
		}
		catch(Exception ignored)
		{
			
		}
	}
	
	void parseObjectives(BufferedReader br) throws IOException
	{
		line = br.readLine();
		while(line != null)
		{
			if(line.toLowerCase().startsWith("name:") || line.toLowerCase().startsWith("xp:") || line.toLowerCase().startsWith("objectives:"))
				break;
			Objective o = new Objective(line);
			this.objectives.add(o);
			line = br.readLine();
		}
	}

	public void checkObjectives()
	{
		int fulfilledCount = 0;
		for (Objective o : objectives)
		{
			if (o.fulfilled)
			{
				fulfilledCount++;
				continue;
			}
			o.checkObjective();
		}
		if(fulfilledCount == objectives.size())
		{
			fulfilled = true;
		}
	}

}
