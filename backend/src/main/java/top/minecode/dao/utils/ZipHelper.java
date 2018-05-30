package top.minecode.dao.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created on 2018/5/27.
 * Description:
 * @author Liao
 */
public class ZipHelper {

    public static int countEntriesInZipFile(final ZipFile zipFile) {
        int count = 0;
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            if (!entries.nextElement().isDirectory()) {
                count++;
            }
        }

        return count;
    }

    public static void unZipDataSet(String datasetPath, String destPath) throws IOException {
        // Create destination directory
        File parentFile = new File(destPath);
        //noinspection ResultOfMethodCallIgnored
        parentFile.mkdirs();

        // Begin unzipping
        try (ZipFile zipFile = new ZipFile(datasetPath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                // Only image will be unzipped todo add image checking
                String entryName = new File(entry.getName()).getName();
                File target = new File(destPath, entryName);

                if (!entry.isDirectory()) {
                    try (InputStream in = zipFile.getInputStream(entry);
                         OutputStream out = new FileOutputStream(target)){
                        IOUtils.copy(in, out);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
