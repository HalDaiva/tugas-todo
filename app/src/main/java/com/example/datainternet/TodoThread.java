package com.example.datainternet;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class TodoThread extends Thread {


    private final Handler h;

    TodoThread(Handler h) {
        this.h = h;
    }

    @Override
    public void run() {
        super.run();
        try {
            URL url = new URL("https://mgm.ub.ac.id/todo.php");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "To do");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String dataToSend = "";

            OutputStream os = conn.getOutputStream();
            os.write(dataToSend.getBytes());
            os.flush();
            os.close();


            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();

                StringBuffer sb = new StringBuffer();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is)
                );
                String line = "";
                while ((line = br.readLine()) != null)
                    sb.append(line);
                br.close();
                String hasilJson = sb.toString();

                Message message = h.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("hasil", hasilJson);
                message.setData(bundle);
                h.sendMessage(message);

            } else {

            }

        } catch (Exception e) {

        }
    }
}
