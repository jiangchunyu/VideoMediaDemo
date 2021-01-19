package com.edu.videomediademo.logger;

import android.text.TextUtils;
import android.util.Log;

/**
 * @desc:
 * @author: jiangcy
 * @date: 16-8-23
 * @time: 上午10:09
 */
public class JLogUtil {
  public static boolean isEmpty(String line) {
    return TextUtils.isEmpty(line) || line.equals("\n") || line.equals("\t") || TextUtils.isEmpty(line.trim());
  }

  public static void printLine(String tag, boolean isTop) {
    if (isTop) {
      Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
    } else {
      Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
    }
  }
}
