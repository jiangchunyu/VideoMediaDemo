package com.edu.videomediademo.logger.jlog;

import android.util.Log;

import com.edu.videomediademo.logger.JLog;

/**
 * @desc:
 * @author: jiangcy
 * @date: 16-8-23
 * @time: 上午10:09
 */
public class BaseLog {
  public static void printDefault(int type, String tag, String src, String headString, String msg) {

    int index = 0;
    int maxLength = 1000;
    int countOfSub = msg.length() / maxLength;
//    Log.d("printDefault", "printDefault: msg.length() "+msg.length()+"   countOfSub "+countOfSub+"  msg "+msg);
    if(src!=null)
    {
      printSub(type,tag,"╔═══════════════════════════════════════════════════════════════════════════════════════");
      printSub(type, tag, "║ " +src+headString);
    }
    if (countOfSub > 0) {
      for (int i = 0; i < countOfSub; i++) {
        String sub = msg.substring(index, index + maxLength);
        printSub(type, tag, "║ " +  sub);
        index += maxLength;
      }
      printSub(type, tag, "║ " +msg.substring(index, msg.length())+headString);
    } else {
      printSub(type, tag, "║ " +msg+headString);
    }
    printSub(type,tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
  }

  private static void printSub(int type, String tag, String sub) {
    switch (type) {
      case JLog.V:
        Log.v(tag, sub);
        break;
      case JLog.D:
        Log.d(tag, sub);
        break;
      case JLog.I:
        Log.i(tag, sub);
        break;
      case JLog.W:
        Log.w(tag, sub);
        break;
      case JLog.E:
        Log.e(tag, sub);
        break;
      case JLog.A:
        Log.wtf(tag, sub);
        break;
    }
  }

}
