package me.mafrans.emotestf.util;

import java.io.*;
import java.net.URL;

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

    @Deprecated
    public static String readStream(final InputStream stream) throws IOException {
        final BufferedInputStream in = new BufferedInputStream(stream);
        final byte[] contents = new byte[1024];
        int bytesRead;
        final StringBuilder strFileContents = new StringBuilder();

        while ((bytesRead = in.read(contents)) != 0) {
            strFileContents.append(new String(contents, 0, bytesRead));
        }

        return strFileContents.toString();
    }

    public static void copyFile(final URL origin, final File file) throws IOException {
        org.apache.commons.io.FileUtils.copyURLToFile(origin, file);
    }
}
