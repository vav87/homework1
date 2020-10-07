package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        String textToAply = text.replaceAll("\\\\n", "\n").toLowerCase();
        String[] words = textToAply.toLowerCase().split("[^a-zA-Z]+");
        System.out.println("words:");
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
                .collect(Collectors.joining(" "));
        //System.out.println(res);
        return res;
    }
}
