package org.alfresco.example.util;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Jamal Kaabi-Mofrad
 */
public class PropertyUtil
{
    private static Properties properties;
    static
    {
        properties = loadProperties();
    }

    public static Properties getProperties()
    {
        return properties;
    }

    public static String getProperty(String key)
    {
        // Get System property first
        String value = System.getProperty(key);
        if (value == null)
        {
            value = properties.getProperty(key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue)
    {
        String value = getProperty(key);
        return (value == null) ? defaultValue : value;
    }

    private static Properties loadProperties()
    {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream("application.properties"))
        {
            properties.load(inputStream);
        }
        catch (IOException ex)
        {
            fail("Couldn't load 'application.properties' file. " + ex.getMessage());
        }
        return properties;
    }
}
