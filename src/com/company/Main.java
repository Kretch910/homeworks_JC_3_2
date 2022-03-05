package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static List<String> seveList = new ArrayList<>();

    public static void saveProgres(String file, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        seveList.add(file);
    }

    public static void zipFiles(String dir) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(dir))) {
            for (String file : seveList) {
                FileInputStream fis = new FileInputStream(file);
                String[] parts = file.split("/");
                ZipEntry entry = new ZipEntry(parts[parts.length - 1]);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
                File fileForDelete = new File(file);
                try {
                    fileForDelete.delete();
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        seveList.clear();
    }

    public static void main(String[] args) {

        GameProgress game1 = new GameProgress(50, 3, 9, 209.58);
        GameProgress game2 = new GameProgress(520, 78, 98, 54896.97);
        GameProgress game3 = new GameProgress(5, 1, 3, 12.3);

        saveProgres("H://Games/savegames/progress1.sav", game1);
        saveProgres("H://Games/savegames/progress2.sav", game2);
        saveProgres("H://Games/savegames/progress3.sav", game3);

        zipFiles("H://Games/savegames/save.zip");
    }
}


