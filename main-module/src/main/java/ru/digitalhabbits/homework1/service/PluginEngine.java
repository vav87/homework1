package ru.digitalhabbits.homework1.service;

import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PluginEngine {

    @Nonnull
    public  <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {

        Constructor constructor = null;
        String res = "";
        try {
            constructor = cls.getConstructor();
            Object pluginObject = constructor.newInstance();
            Method method = cls.getMethod("apply", String.class);
            res = (String) method.invoke(pluginObject, text);
            //System.out.println("res: "+res);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return res;
    }
}
