package com.cafape.nutriplan.support;

import java.io.FileOutputStream;
import java.io.*;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.*;

import static com.cafape.nutriplan.support.Utils.myprint;

public class Unzipper
{
    public static int unzip(File inputZip, File outputFolder) throws IOException {
        FileInputStream source = new FileInputStream(inputZip);
        return unzip(source, outputFolder);
    }

    public static int unzip(FileInputStream fis, File outputFolder) throws IOException {
        int count=0;
        ZipArchiveInputStream zis = null;
        FileOutputStream fos = null;
        try {
            byte[] buffer = new byte[8192];
            zis = new ZipArchiveInputStream(fis, "Cp1252", true); // this supports non-USACII names
            ArchiveEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                myprint(entry.getName());
                File file = new File(outputFolder, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    count++;
                    file.getParentFile().mkdirs();
                    fos = new FileOutputStream(file);
                    int read;
                    while ((read = zis.read(buffer,0,buffer.length)) != -1)
                        fos.write(buffer,0,read);
                    fos.close();
                    fos = null;
                }
            }
        } finally {
            try { zis.close(); } catch (Exception e) {
                e.printStackTrace();
            }
            try { fis.close(); } catch (Exception e) {
                e.printStackTrace();
            }
            try { if (fos!=null) fos.close(); } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }
}
