package com.example.user.cinemaapplication.Adds;

import android.content.Context;

import com.example.user.cinemaapplication.Activites.QRScanActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.Telephony.Mms.Part.FILENAME;

public class FileAdapter {

    private static StringBuilder stringBuilder = new StringBuilder();

    public static void writeFile(String s,Context context) {
        try {
            File myFilewrite = new File(context.getFilesDir() + "/" + "history.txt");
            FileOutputStream outputStream = new FileOutputStream(myFilewrite);   // После чего создаем поток для записи
            outputStream.write((s + "\n").getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String readFile(Context context) {

        File myFileread = new File(context.getFilesDir() + "/" + "history.txt");
        try {
            FileInputStream inputStream = new FileInputStream(myFileread);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("-@-");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
