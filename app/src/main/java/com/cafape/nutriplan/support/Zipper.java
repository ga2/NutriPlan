package com.cafape.nutriplan.support;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by IPM_WS_2 on 31/03/2017.
 */

public class Zipper
{
/*
    String []s=new String[2]; //declare an array for storing the files i.e the path of your source files
    s[0]="/mnt/sdcard/Wallpaper/pic.jpg";    //Type the path of the files in here
    s[1]="/mnt/sdcard/Wallpaper/Final.pdf"; // path of the second file

    zip((s,"/mnt/sdcard/MyZipFolder.zip");    //call the zip function
*/
    static final int BUFFER = 1024;

    public static boolean zip(ArrayList<File> files_source, String zipFile_destination_path) {
        //
        // tring[] filePaths = (String[])(Arrays.stream(files_source.map(File::new).filter(File::isFile).map(File::getAbsolutePath).toArray(String[]::new)));
        String[] filePaths = new String[files_source.size()];
        int i = -1;
        for(File f: files_source) {
            filePaths[i++] = f.getAbsolutePath();
        }
        try {
            FileOutputStream dest = new FileOutputStream(zipFile_destination_path);
            return zip(filePaths, dest);
        } catch (FileNotFoundException fnfe) {
            return false;
        }
    }

    public static boolean zip(String[] files_source_path, String _zipFile) {
        try {
            FileOutputStream dest = new FileOutputStream(_zipFile);
            return zip(files_source_path, dest);
        } catch (FileNotFoundException fnfe) {
            return false;
        }
    }

    public static boolean zip(String[] files_source_path, FileOutputStream dest)
    {
        boolean res = false;
        String[] _files = files_source_path;
        //String _zipFile = zipFile_destination_path;

        try
        {
            BufferedInputStream origin = null;


            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER];

            for(int i = 0; i < _files.length; i++)
            {
                FileInputStream fi = new FileInputStream(_files[i]);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1)
                {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
            res = true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }
}
