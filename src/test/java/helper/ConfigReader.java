package helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigReader {
    static Logger logger = Logger.getLogger(ConfigReader.class.getName());
    static Properties prop = new Properties();

    public ConfigReader(){
        readConfig();
    }

    public static void readConfig(){
        String localDir = System.getProperty("user.dir");
        String fileName = localDir+"/src/test/resources/config/execution.config";
        InputStream stream = null;
        try {
            stream = new FileInputStream(fileName);
        }catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE,"couldn't read property file",ex);
        }
        try {
            prop.load(stream);
        } catch (IOException ex) {
            logger.log(Level.SEVERE,"IO Exception",ex);
        }

    }

    public static String getProperty(String propName){
        return prop.getProperty(propName);
    }
}
