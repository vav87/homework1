package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarFile;

import static com.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader{
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = "jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {

        List<Class<? extends PluginInterface>> plugins = new ArrayList<>(2);
        List<String> classNames = new ArrayList<>();

        File pluginsDir = new File(System.getProperty("user.dir") +"/"+ pluginDirName);
        //System.out.println("pluginsDir: "+pluginsDir.toString());
        File[] jars = pluginsDir.listFiles((dir, name) -> name.endsWith("."+PLUGIN_EXT));
        for (File jar: jars) {
            //System.out.println("jar.getName(): "+jar.getName());
            try {
                JarFile jarf=new JarFile(jar);
                jarf.stream().forEach(jarEntry -> {
                    if(jarEntry.getName().endsWith(".class")) {
                        //System.out.println("jarEntry.getName(): "+jarEntry.getName().replaceAll("/",".").replace(".class", ""));
                        classNames.add(jarEntry.getName().replaceAll("/",".").replace(".class", ""));
                    }
                });
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        URL[] urls = new URL[jars.length];
        try {
            for (int i = 0; i < urls.length; i++) {
                urls[i] = jars[i].toURI().toURL();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLClassLoader urlClassLoader=new URLClassLoader(urls);
        for(String className: classNames) {
            try {
                Class<? extends PluginInterface> pluginClass = (Class<? extends PluginInterface>) urlClassLoader.loadClass(className);
                plugins.add(pluginClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return plugins;
    }
}
