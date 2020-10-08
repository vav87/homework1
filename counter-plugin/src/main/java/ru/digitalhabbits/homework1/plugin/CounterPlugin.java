package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        String textToAply = text.replaceAll("\\\\n", "\n").toLowerCase();
        int linesCount = 1; //text.split("[\n|\r]").length;
        for ( int i = 0; i < textToAply.length(); i++ ) {
            char ch=  textToAply.charAt(i);
            if (ch == '\n' || ch == '\r') {
                linesCount++;
            }
        }

        List<String> words = new ArrayList<>();
        // regex с которым количество слов совпадает с интеграционным тестом \b[a-zA-Z][a-zA-Z.0\/-]*\b
        Matcher m = Pattern.compile("\\b[a-zA-Z][a-zA-Z.0-9]*\\b").matcher(textToAply);
        while (m.find()) {
            words.add(m.group());
        }
        int wordsCount = words.size();//textToAply.split("\\s+").length;

        String res = String.valueOf(linesCount)+";"+String.valueOf(wordsCount)+";"+String.valueOf(textToAply.length());
        return res;
    }
}
