package ru.digitalhabbits.homework1.service;

import javax.annotation.Nonnull;
import java.io.*;

import static java.util.Arrays.stream;

public class FileEngine {
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {
        String outputFileName = System.getProperty("user.dir")+"/"+RESULT_DIR+"/"+String.format(RESULT_FILE_PATTERN, pluginName);
        //System.out.println("outputFileName: "+outputFileName);
        try (BufferedWriter outputFile = new BufferedWriter(new FileWriter(outputFileName))){
            outputFile.write(text);
        } catch (FileNotFoundException e) {
            System.err.println("Cannot open the file " + e.getMessage());
        } catch(IOException e) {
            System.err.println("Error when processing file " + e.getMessage());
        }
        return true;
    }

    public void cleanResultDir() {
        final String currentDir = System.getProperty("user.dir");
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);
        resultDir.mkdir();
        stream(resultDir.list((dir, name) -> name.endsWith(RESULT_EXT)))
                .forEach(fileName -> new File(resultDir + "/" + fileName).delete());
    }
}
