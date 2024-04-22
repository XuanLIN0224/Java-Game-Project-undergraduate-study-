import java.io.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * Class that contains methods to read a CSV file and a properties file.
 * You may edit this as you wish.
 */
public class IOUtils {

    /***
     * Method that reads a CSV file and return a 2D String array
     * @param csvFile: the path to the CSV file
     * @return 2D String array
     */
    public static String[][] readCsv(String csvFile) {
        int lines = countLines(csvFile);
        String[][] data = new String[lines][3];
        int lineIndex = 0;

        try {
            // Read data from the CSV file
            Scanner scanner = new Scanner(new File(csvFile));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String type = parts[0].trim();
                String x = parts[1].trim();
                String y = parts[2].trim();
                data[lineIndex][0] = type;
                data[lineIndex][1] = x;
                data[lineIndex][2] = y;
                lineIndex++;
                }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public static int countLines(String csvFile){
        int lines = 0;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    /***
     * Method that reads a properties file and return a Properties object
     * @param configFile: the path to the properties file
     * @return Properties object
     */
    public static Properties readPropertiesFile(String configFile) {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(configFile));
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        return appProps;
    }
}