package com.ancientshores.Ancient.Language;

import com.ancientshores.Ancient.Ancient;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;


public class LanguageFile {
    
    protected CustomYml langFile;
    protected FileConfiguration lang;
    protected LanguageCode langCode;
    
    //Construct this class
    public LanguageFile(Plugin plugin, LanguageCode langCode)
    {
        try{
            
            this.langFile = new CustomYml(plugin, "/languages/" + langCode.name().toLowerCase() + ".yml"); //Get the language-file by LanguageCode
            this.lang = langFile.getConfig();
            this.langCode = langCode;
            
            setupLangFile(langCode);
            
        } catch (Exception ex) {
            
            ex.printStackTrace(); //Send error-log to console
            
        }
    }
    
    //Get a text from lang-file
    public String getText(String textPath)
    {
        try{
            
            return lang.getString(textPath.toLowerCase());
            
        }catch (Exception ex){
            
            System.out.println( "[" + Ancient.ConsoleBrand + "] " + "Text " + textPath + " does not exist.");
            return "Text not found :(";
   
        }
    }
    
    //Set a text in lang-file
    public void setText(String textPath, String text)
    {
        try{
            
            lang.set(textPath.toLowerCase() , text);
            langFile.saveConfig();
            
        }catch (Exception ex){
            
            ex.printStackTrace();
   
        }
    }
    
    //Set a text only once
    public void setTextOnce(String textPath, String text){
        
       try{
           
           if ( lang.getString(textPath.toLowerCase()) == null ) {
               
               lang.set(textPath.toLowerCase() , text);
               langFile.saveConfig();
               System.out.println(Ancient.ConsoleBrand + "Created lang-text " + textPath);
               
           }
           
       } catch (Exception ex) {
           
           System.out.println(Ancient.ConsoleBrand + "Error while creating lang-file:");
           System.out.println(ex.getMessage());
           
       }
        
    }
    
    //Setup an new language-file
    private void setupLangFile(LanguageCode langCode)
    {
        
        lang.options().header("Ancient language-file \n Locale: " + langCode.name());
        langFile.saveConfig();
        
    }
    
}
