package top.minecode.dao.utils;

import org.apache.commons.io.IOUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created on 2018/5/27.
 * Description:
 * @author Liao
 */
public class ZipHelper {

    private static MimetypesFileTypeMap typeMap;

    static {
        typeMap = new MimetypesFileTypeMap();
        typeMap.addMimeTypes("image png tif jpg jpeg bmp");
    }

    public static int countEntriesInZipFile(final ZipFile zipFile) throws IOException {
        int count = 0;
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            if (!entries.nextElement().isDirectory()) {
                count++;
            }
        }

        zipFile.close();
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

                // Only image will be unzipped
                String entryName = new File(entry.getName()).getName();
                if (!isImageFile(entryName) || isHiddenFile(entryName))
                    continue;
                File target = new File(destPath, entryName);

                if (!entry.isDirectory()) {
                    try (InputStream in = zipFile.getInputStream(entry);
                         OutputStream out = new FileOutputStream(target)) {
                        IOUtils.copy(in, out);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static boolean isImageFile(String name) {
        String mimeType = typeMap.getContentType(name);
        return mimeType.substring(0, 5).equalsIgnoreCase("image");
    }

    // Avoid unzip mac's hidden file
    private static boolean isHiddenFile(String name) {
        return name.startsWith("._");
    }
}
