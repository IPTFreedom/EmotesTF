package me.mafrans.emotestf.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

public class FileUtils {
   public static String readFile(File file) throws IOException {
      String var6;
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
         StringBuilder sb = new StringBuilder();

         for(String line = br.readLine(); line != null; line = br.readLine()) {
            sb.append(line);
            sb.append(System.lineSeparator());
         }

         String everything = sb.toString();
         var6 = everything;
      }

      return var6;
   }

   @Deprecated
   public static String readStream(InputStream stream) throws IOException {
      BufferedInputStream in = new BufferedInputStream(stream);
      byte[] contents = new byte[1024];
      int bytesRead = 0;
      String strFileContents = null;

      while((bytesRead = in.read(contents)) != 0) {
         strFileContents = strFileContents + new String(contents, 0, bytesRead);
      }

      return strFileContents;
   }

   public static void writeToFile(File file, String text) throws FileNotFoundException {
      PrintWriter out = new PrintWriter(file);
      out.println(text);
      out.close();
   }

   public static void copyFile(File origin, File file) throws IOException {
      org.apache.commons.io.FileUtils.copyFile(origin, file);
   }

   public static void copyFile(URL origin, File file) throws IOException {
      org.apache.commons.io.FileUtils.copyURLToFile(origin, file);
   }
}
