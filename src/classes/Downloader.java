package classes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;

public class Downloader {
    private final Supporter supporter;
    private URLConnection urlConnection;

    private Downloader() {
        supporter = new Supporter();
    }

    public static void start() {
        new Downloader().go();
    }

    private void go() {
        String url = supporter.read("url");
        try {
            urlConnection = new URL(url).openConnection();
        } catch (IOException e) {
            System.out.println("Exception in Downloader.go()");
            e.printStackTrace();
        }
        String[] split = url.split("/");
        String stringPath = supporter.read("path") + "/" + split[split.length - 1];
        Path path = Path.of(stringPath);
        download(path.toFile());
    }

    private void download(File file) {
        try (BufferedInputStream inStream = new BufferedInputStream(urlConnection.getInputStream());
        FileOutputStream outStream = new FileOutputStream(file)) {
            int bytesRead;
            int downloadBytes = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inStream.read(buffer, 0, buffer.length)) != -1) {
                outStream.write(buffer, 0, bytesRead);
                downloadBytes += bytesRead;
                if (downloadBytes % 1024000 == 0) {
                    System.out.printf("Download %d megabytes\n", downloadBytes / 1024000);
                }
            }
            System.out.println("Download done");
        } catch (IOException e) {
            System.out.println("IOException in Downloader.download()");
            e.printStackTrace();
        }
    }

}
