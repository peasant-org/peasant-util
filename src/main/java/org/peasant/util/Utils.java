/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 * @author 谢金光
 */
public class Utils {

    public static String generateUniqueKey() {
        return java.util.UUID.randomUUID().toString();
    }

    public static int copy(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024000];
        int size = 0;
        int s = 0;

        while ((s = is.read(buffer)) > -1) {
            size += s;
            os.write(buffer, 0, s);
        }
        os.flush();
        return size;

    }

    public static String getStreamString(InputStream tInputStream, String charset) {

        if (tInputStream != null) {
            try {
                BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream, charset));
                StringBuilder tStringBuffer = new StringBuilder();
                String sTempOneLine = new String("");
                while ((sTempOneLine = tBufferedReader.readLine()) != null) {
                    tStringBuffer.append(sTempOneLine);
                }
                return tStringBuffer.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        return null;

    }

    /**
     * 默认使用UTF-8字符集解码
     *
     * @param tInputStream
     * @return
     */
    public static String getStreamString(InputStream tInputStream) {
        return getStreamString(tInputStream, "utf-8");
    }
}
