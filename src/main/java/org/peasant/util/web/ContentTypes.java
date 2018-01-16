/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.net.FileNameMap;

/**
 *
 * @author 谢金光
 */
public class ContentTypes {

    public static final String MULTIPART = "multipart/form-data";
    public static final String APP_URLENCODED ="application/x-www-form-urlencoded";
    public static final String OCTET_STREAM = "application/octet-stream";
    public static final String JSON = "application/json";
    public static final String XML = "application/xml";
    public static final String TEXT_XML = "text/xml";

    /**
     * Loads filename map (a mimetable) from a data file. It will first try to
     * load the user-specific table, defined by "content.types.user.table"
     * property. If that fails, it tries to load the default built-in table.
     *
     * @return the FileNameMap
     */
    public static FileNameMap getFileNameMap() {
        return java.net.URLConnection.getFileNameMap();
    }

}
