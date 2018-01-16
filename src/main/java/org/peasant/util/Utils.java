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
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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

    /**
     * 使用JAXB从xml恢复javaBean, javaBean必须拥有无参构造函数
     *
     * @param <T>
     * @param c
     * @param xml
     * @return
     */
    public static <T extends Object> T xml2Bean(Class<T> c, String xml) {
        try {
            JAXBContext jc = JAXBContext.newInstance(c);
            Unmarshaller us = jc.createUnmarshaller();
            return (T) us.unmarshal(new StringReader(xml));
        } catch (JAXBException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * 使用JAXB将javaBean转换成xml
     *
     * @param <T>
     * @param bean
     * @return xml,若转换失败或 {@code bean}为null时，返回空字符串
     */
    public static <T extends Object> String bean2xml(T bean) {
        if (null == bean) {
            return "";
        }
        try {
            JAXBContext jc = JAXBContext.newInstance(bean.getClass());

            Marshaller ms = jc.createMarshaller();
            StringWriter sw = new StringWriter();
            ms.marshal(bean, sw);
            return sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
