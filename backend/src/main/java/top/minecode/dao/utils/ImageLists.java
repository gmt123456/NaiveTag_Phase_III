package top.minecode.dao.utils;

import org.jetbrains.annotations.NotNull;
import top.minecode.service.util.PathUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 2018/5/27.
 * Description:
 * @author Liao
 */
public class ImageLists implements Iterable<ImageLists.ImageList> {

    private static final int IMAGE_LIST_SIZE = 30;

    private List<String> images;
    private String rootFileName;

    public ImageLists(String imageDirPath, String rootFileName) {
        this.rootFileName = rootFileName;
        try (Stream<Path> paths = Files.walk(Paths.get(imageDirPath))){
             images = paths.filter(this::isImageFile)
                     .map(this::getRelativePath).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isImageFile(Path file) {
        String mimeType = new MimetypesFileTypeMap().getContentType(file.toFile());
        return mimeType.substring(0, 5).equalsIgnoreCase("image");
    }

    private String getRelativePath(Path path) {
        File file = path.toFile();
        String filePath = file.getPath();
        int index = filePath.lastIndexOf(rootFileName);
        return filePath.substring(index);
    }

    @NotNull
    @Override
    public Iterator<ImageList> iterator() {
        return new Iterator<ImageList>() {
            private int i = 0;
            private int j = IMAGE_LIST_SIZE;

            @Override
            public boolean hasNext() {
                return i < images.size();
            }

            @Override
            public ImageList next() {
                ImageList next;
                if (j < images.size()) {
                    next = new ImageList(i, j);
                } else
                    next = new ImageList(i, images.size());
                i += IMAGE_LIST_SIZE;
                j += IMAGE_LIST_SIZE;
                return next;
            }
        };
    }

    public class ImageList {

        private List<String> subImages;

        private ImageList(int i, int j) {
            this.subImages = images.subList(i, j);
        }

        public List<String> getImages() {
            return subImages;
        }

        @Override
        public String toString() {
            return "ImageList{" +
                    "subImages=" + subImages +
                    '}';
        }
    }
}
