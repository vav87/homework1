package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        String textToApply = text.replaceAll("\\\\n", "\n").toLowerCase();

        List<String> words = new ArrayList<String>();
        // regex с которым количество слов совпадает с интеграционным тестом \b[a-zA-Z][a-zA-Z.0\/-]*\b
        Matcher m = Pattern.compile("\\b[a-zA-Z][a-zA-Z.0-9]*\\b").matcher(textToApply);
        while (m.find()) {
            words.add(m.group());
        }

        Map<String, Integer> map = new TreeMap<>();
        for(String word: words) {
            if(map.containsKey(word)) {
                map.put(word, map.get(word)+1);
            } else {
                map.put(word, 1);
            }
        }
        String res = map.keySet()
                .stream()
                .map(word -> (word +" "+map.get(word)))
                .collect(Collectors.joining("\n"));
        //System.out.println("res: "+res);
        return res;
    }
}
