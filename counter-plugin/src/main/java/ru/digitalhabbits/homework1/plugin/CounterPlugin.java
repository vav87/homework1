package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        String textToAply = text.replaceAll("\\\\n", "\n").toLowerCase();
        int lines = 1; //text.split("[\n|\r]").length;
        int words = textToAply.split("[\\s]").length;
//        int letters = 0;
        for ( int i = 0; i < textToAply.length(); i++ ) {
            char ch=  textToAply.charAt(i);
            if (ch == '\n' || ch == '\r') {
                lines++;
            }
//            if (String.valueOf(ch).matches("[a-zA-Z]")) {
//                letters++;
//            }
        }
        String res = String.valueOf(lines)+";"+String.valueOf(words)+";"+String.valueOf(textToAply.length());
//        System.out.println("Countr plugin res: "+res);
        return res;
    }
}
