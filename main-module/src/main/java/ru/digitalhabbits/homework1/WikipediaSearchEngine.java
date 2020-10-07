package ru.digitalhabbits.homework1;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;
import ru.digitalhabbits.homework1.service.FileEngine;
import ru.digitalhabbits.homework1.service.PluginEngine;
import ru.digitalhabbits.homework1.service.PluginLoader;
import ru.digitalhabbits.homework1.service.WikipediaClient;

import javax.annotation.Nonnull;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class WikipediaSearchEngine {
    private static final Logger logger = getLogger(WikipediaSearchEngine.class);

    private static final String PLUGIN_DIR = "plugins";

    public void search(@Nonnull String searchString) {
        logger.info("Searching '{}' on wikipedia", searchString);

        // 1. сделать запрос в wikipedia, получить результат в формате json.
        final WikipediaClient client = new WikipediaClient();
        final String text;
        text = client.search(searchString);

        // 2. очистить папку с результатами
        final FileEngine fileEngine = new FileEngine();
        fileEngine.cleanResultDir();

        // 3. найти и загрузить все плагины
        final PluginLoader pluginLoader = new PluginLoader();
        final List<Class<? extends PluginInterface>> plugins =
                pluginLoader.loadPlugins(PLUGIN_DIR);

        // 4. выполнить действия над результатом и записать в файл
        final PluginEngine pluginEngine = new PluginEngine();
        for (Class<? extends PluginInterface> plugin: plugins) {
            final String result = pluginEngine.applyPlugin(plugin, text);

            final String pluginName = plugin.getSimpleName();
            logger.info("Apply '{}' plugin get result {}", pluginName, result);

            fileEngine.writeToFile(result, pluginName);
        }
    }
}
