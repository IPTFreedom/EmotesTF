package me.mafrans.emotestf.util;

import java.io.*;

public class FileUtils {
    public static String readFile(final File file) throws IOException {
        final String var6;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            final StringBuilder sb = new StringBuilder();

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            var6 = sb.toString();
        }

        return var6;
    }
}
