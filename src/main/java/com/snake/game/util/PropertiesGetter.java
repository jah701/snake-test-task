package com.snake.game.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesGetter {
    /**
     * Here we will have a name of our resources file
     */
    private static final String PROP_FILE_NAME = "app.properties";

    /**
     * String value which will be used as result value
     */
    private String result = "";

    /**
     * Stream to read properties via ClassLoader
     */
    private InputStream inputStream;

    /**
     * Method to get a property from a properties file
     * @param propName - name of parameter
     * @return value represents value of parameter
     * @throws IOException if not able to close InputStream
     */
    public String getPropValues(final String propName) throws IOException {
        try {
            Properties prop = new Properties();
            inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream(PROP_FILE_NAME);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '"
                        + PROP_FILE_NAME
                        + "' not found in the classpath");
            }
            result = prop.getProperty(propName);
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }
}
