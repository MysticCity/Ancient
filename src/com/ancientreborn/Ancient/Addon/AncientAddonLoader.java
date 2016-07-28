package com.ancientreborn.Ancient.Addon;

import com.ancientreborn.Ancient.Ancient;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class AncientAddonLoader 
{

    private final File AddonDir = new File( Ancient.getInstance() + "/addons" );
    
    public AncientAddonLoader()
    {
        
        //Setup requirements
        SetupAddonSupport();
        
        //Load addons from addons-directory
        LoadAddons();

    }
    
    private void SetupAddonSupport()
    {
        
        //Create addon-dir if not exist       
        if ( !AddonDir.exists() )
        {
            AddonDir.mkdir();
        }
        
        
    }
    
    private void LoadAddons()
    {
        
        for ( File PotentialAddon : AddonDir.listFiles() )
        {
            if ( PotentialAddon.getName().endsWith( ".jar" ) )
            {
                
                try
                {
                   
                    JarFile AddonFile = new JarFile( PotentialAddon );
                    Manifest AddonManifest = AddonFile.getManifest();
                    Attributes AddonManifestAttributes = AddonManifest.getMainAttributes();
                    
                    String AddonMainClass = AddonManifestAttributes.getValue( Attributes.Name.MAIN_CLASS );
                    String AddonVersion = AddonManifestAttributes.getValue( Attributes.Name.MANIFEST_VERSION );

                    try 
                    {
                        
                        System.out.println( Ancient.ConsoleBrand + "Loading " + PotentialAddon.getName() + " v" + AddonVersion );
                        
                        Class AddonMainClassToLoad = new URLClassLoader( new URL[]{  PotentialAddon.toURL() } ).loadClass( AddonMainClass );
                        
                        AncientAddon Addon = (AncientAddon) AddonMainClassToLoad.newInstance();
                        
                        Addon.OnStartup();
                        
                    } catch ( Exception ex ) {
                    
                        System.out.println( Ancient.ConsoleBrand + "Error while loading " + PotentialAddon.getName() + " v" + AddonVersion );
                        ex.printStackTrace();
                        
                    }
                                        
                } 
                catch ( Exception ex )
                {
                    
                    System.out.println( Ancient.ConsoleBrand + "Error in AddonHandler" );
                    ex.printStackTrace();
                    
                }
                
            }
        }
        
    }
    
}
