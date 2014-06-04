/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author 谢金光
 * @param <I>
 */
public class Utils {

    public Utils() {
        
    }
    
    public List complete(List is ,String property  ){
        return null;
    }
    public static int copy(InputStream ins , OutputStream outs) throws IOException{
        int size =0;
        int read;
        byte[] b = new byte[1024*1024];
        while((read =ins.read(b))>-1){
            outs.write(b, 0, read);
            outs.flush();
            size = size +read;
        };
        return size;
    }
}
