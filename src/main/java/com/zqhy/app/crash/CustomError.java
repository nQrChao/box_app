package com.zqhy.app.crash;

import com.box.other.blankj.utilcode.util.Logs;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * [功能说明]
 *
 * @author 韩国桐
 * @version [0.1, 2020/7/21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CustomError {
    private static final String NET_ERROR="NET_ERROR";
    /**
     * 上传日志-无视错误
     * **/
    public static void log(Throwable ex) {
        try{
            StringBuilder sb = new StringBuilder();
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();

            String result = writer.toString();
            sb.append(result);
            Logs.e("error",sb.toString());
//            UMCrash.generateCustomLog(ex,NET_ERROR);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
