package com.ancientshores.Ancient.Quests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class AncientQuest
{
  public final HashSet<Objective> objectives = new HashSet();
  public String name;
  public int xp;
  public String description;
  public boolean fulfilled;
  String line;
  
  public AncientQuest(File f)
  {
    parseFile(f);
  }
  
  public void parseFile(File f)
  {
    try
    {
      BufferedReader br = new BufferedReader(new FileReader(f));
      this.line = br.readLine();
      while (this.line != null) {
        if (this.line.toLowerCase().startsWith("name:")) {
          parseName(br);
        } else if (this.line.toLowerCase().startsWith("description:")) {
          parseDescription(br);
        } else if (this.line.toLowerCase().startsWith("xp:")) {
          parseXp(br);
        } else if (this.line.toLowerCase().startsWith("objectives:")) {
          parseObjectives(br);
        } else {
          this.line = br.readLine();
        }
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  void parseName(BufferedReader br)
    throws IOException
  {
    String name = "";
    this.line = br.readLine();
    while ((this.line != null) && 
      (!this.line.toLowerCase().startsWith("description:")) && (!this.line.toLowerCase().startsWith("xp:")) && (!this.line.toLowerCase().startsWith("objectives:")))
    {
      name = name + this.line + "\n";
      this.line = br.readLine();
    }
    this.name = name;
  }
  
  void parseDescription(BufferedReader br)
    throws IOException
  {
    String desc = "";
    this.line = br.readLine();
    while ((this.line != null) && 
      (!this.line.toLowerCase().startsWith("name:")) && (!this.line.toLowerCase().startsWith("xp:")) && (!this.line.toLowerCase().startsWith("objectives:")))
    {
      desc = desc + this.line + "\n";
      this.line = br.readLine();
    }
    this.description = desc;
  }
  
  void parseXp(BufferedReader br)
    throws IOException
  {
    this.line = br.readLine();
    if (this.line == null) {
      return;
    }
    try
    {
      this.xp = Integer.parseInt(this.line);
    }
    catch (Exception ignored) {}
  }
  
  void parseObjectives(BufferedReader br)
    throws IOException
  {
    this.line = br.readLine();
    while ((this.line != null) && 
      (!this.line.toLowerCase().startsWith("name:")) && (!this.line.toLowerCase().startsWith("xp:")) && (!this.line.toLowerCase().startsWith("objectives:")))
    {
      Objective o = new Objective(this.line);
      this.objectives.add(o);
      this.line = br.readLine();
    }
  }
  
  public void checkObjectives()
  {
    int fulfilledCount = 0;
    for (Objective o : this.objectives) {
      if (o.fulfilled) {
        fulfilledCount++;
      } else {
        o.checkObjective();
      }
    }
    if (fulfilledCount == this.objectives.size()) {
      this.fulfilled = true;
    }
  }
}
