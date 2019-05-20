package com.rafaellroca.moviedb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SampleDataHelper {
    private static final String TEST_RESOURCES_PATH = "../app/src/test/java/com/rafaellroca/moviedb/data/";

    public static String loadSampleDataFromLocalJSON(String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(TEST_RESOURCES_PATH + filename)));
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }
        return stringBuilder.toString();
    }

}
