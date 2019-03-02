package com.example.blusmart.utils;

import android.util.Log;

import com.example.blusmart.BuildConfig;
import com.example.blusmart.data.model.Duty;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import okhttp3.RequestBody;
import okio.Buffer;

public class Commons {

    public static String checkSum(@NonNull String path, String body) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BuildConfig.privateString);

        if (body != null) {
            try {
                JSONObject json = new JSONObject(body);
                List<String> list = Lists.newArrayList(json.keys());
                Collections.sort(list);
                for (String value :list){
                    stringBuilder.append(",");
                    stringBuilder.append(json.getString(value));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.append(",");
        stringBuilder.append(path);
        String content = stringBuilder.toString();
        String data = BCrypt.hashpw(content, BCrypt.gensalt(12));
        Log.e("CheckSum", data + "  | content :" + content);

        return data;
    }

    public static String bodyToString(RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return null;
            return buffer.readUtf8();
        } catch (final IOException e) {
            return null;
        }
    }


    public static String getDate(long timeStamp) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "";
        }
    }
}
