package com.gsh.springcloud.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
public class ExceptionUtil {

  public ExceptionUtil() {
  }

  public static String getStackTraceInfo(Exception e) {
    StringWriter sw = null;
    PrintWriter pw = null;

    String var4;
    try {
      sw = new StringWriter();
      pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      pw.flush();
      sw.flush();
      String var3 = sw.toString();
      return var3;
    } catch (Exception var14) {
      log.warn("error happened when resolving stack trace of exception ", var14);
      var4 = "发生错误";
    } finally {
      if (sw != null) {
        try {
          sw.close();
        } catch (IOException var13) {
          log.warn("error happened when close string writer", var13);
        }
      }

      if (pw != null) {
        pw.close();
      }

    }

    return var4;
  }
}
