package com.example.user.cinemaapplication.Adds;

import android.content.Context;

import com.example.user.cinemaapplication.Activites.AuditChoosingActivity;
import com.example.user.cinemaapplication.Activites.QRScanActivity;
import com.example.user.cinemaapplication.Activites.TabActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.Telephony.Mms.Part.FILENAME;

public class FileAdapter {

    private static Context context = AuditChoosingActivity.getStaticAuditChoosingActivity().getContext();


    private static File myFilewrite = new File(context.getFilesDir() + "/" + "history.txt");
    private static File myFileread = new File(context.getFilesDir() + "/" + "history.txt");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    public static void writeFile(String s,Context context) {
        try {
            
            String lineseparator = System.getProperty("line.separator");
            BufferedWriter writer = new BufferedWriter(new FileWriter(myFilewrite,true));  // После чего создаем поток для записи
            writer.write((s + "---" + dateFormat.format(Calendar.getInstance().getTime()) + lineseparator));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String readFile(Context context) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream inputStream = new FileInputStream(myFileread);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("~@~");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void deleteFile(Context context){
        File file = new File(context.getFilesDir() + "/" + "history.txt");
        boolean deleted = file.delete();

        if(deleted){
            System.out.println("Deleted file " + context.getFilesDir() + "/" + "history.txt");
        }
    }


    public static String readFromFile(Context context){

        BufferedReader br = null;
        FileReader fr = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            //br = new BufferedReader(new FileReader(FILENAME));

                fr = new FileReader(context.getFilesDir() + "/" + "history.txt");
                br = new BufferedReader(fr);


            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
               stringBuilder.append(sCurrentLine);
               stringBuilder.append("\n");
            }
        } catch (IOException e) {

            e.printStackTrace();

        } catch (NullPointerException e){

            e.printStackTrace();

        }finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

        return stringBuilder.toString();
    }
}
