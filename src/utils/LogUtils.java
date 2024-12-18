package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogUtils {
    
    private static final String LOG_FILE = "errors.log";

    public static void logError(Exception e) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println("Error: " + e.getMessage());
            e.printStackTrace(pw);
        } catch (IOException ex) {
            System.out.println("Failed to log error: " + ex.getMessage());
        }
    }
}
