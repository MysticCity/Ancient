package com.ancientshores.AncientRPG.Util;

import com.ancientshores.AncientRPG.AncientRPG;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FlatFileConnector {
    final AncientRPG plugin;

    public FlatFileConnector(AncientRPG plugin) {
        this.plugin = plugin;
    }

    public HashMap<String, Integer[]> vars = new HashMap<String, Integer[]>();

    public HashMap<String, Integer[]> getValues(File f) {
        try {
            HashMap<String, Integer[]> map = new HashMap<String, Integer[]>();
            BufferedReader bf;
            bf = new BufferedReader(new FileReader(f));
            ArrayList<String[]> lines = new ArrayList<String[]>();
            String line = bf.readLine();
            while (line != null) {
                lines.add(removeSpaces(line.split(" ")));
                line = bf.readLine();
            }
            bf.close();
            if (lines.size() > 1) {
                ArrayList<String> varnames = new ArrayList<String>();
                for (int i = 1; i < lines.get(0).length; i++) {
                    varnames.add(lines.get(0)[i].trim());
                }
                for (String s : varnames) {
                    map.put(s.trim(), new Integer[lines.size() - 1]);
                }
                int rowcount = lines.size() - 1;
                if (rowcount > 0) {
                    for (int r = 0; r < rowcount; r++) {
                        for (int x = 1; x <= varnames.size(); x++) {
                            map.get(varnames.get(x - 1))[r] = Integer.parseInt(lines.get(r + 1)[x]);
                        }
                    }
                }
                return map;
            }
        } catch (FileNotFoundException e) {
            plugin.log.warning("AncientRPG: couldn't read flatfile, does the folder exist?");
        } catch (IOException e) {
            plugin.log.warning("AncientRPG: error by parsing the file, please recreate or fix it.");
        }
        return new HashMap<String, Integer[]>();
    }

    public int getIntOfFile(String level, String rowName, File f) {
        YamlConfiguration yc = new YamlConfiguration();
        try {
            yc.load(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return yc.getInt(f.getName().substring(0, f.getName().lastIndexOf('.')) + ".variables." + rowName + ".level" + level);
    }


    public String[] removeSpaces(String[] s) {
        int count = 0;
        for (String value1 : s) {
            if (!value1.equalsIgnoreCase("") && !value1.equalsIgnoreCase(" ") && !value1.equalsIgnoreCase("\t")) {
                count++;
            }
        }
        String[] st = new String[count];
        int anzahl = 0;
        for (String value : s) {
            if (!value.equalsIgnoreCase("") && !value.equalsIgnoreCase(" ") && !value.equalsIgnoreCase("\t")) {
                st[anzahl] = value;
                anzahl++;
            }
        }
        return st;
    }
}