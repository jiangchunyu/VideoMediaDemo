package com.edu.videomediademo.logger;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.edu.videomediademo.logger.jlog.BaseLog;
import com.edu.videomediademo.logger.jlog.FileLog;
import com.edu.videomediademo.logger.jlog.JsonLog;
import com.edu.videomediademo.logger.jlog.XmlLog;

import java.io.File;

/**
 * @desc:
 * @author: jiangcy
 * @date: 16-8-23
 * @time: 上午10:09
 */
public class JLog {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NULL_TIPS = "Log with null object";

    private static final String DEFAULT_MESSAGE = "execute";
    private static final String PARAM = "Param";
    private static final String NULL = "null";
    private static final String TAG_DEFAULT = "jcy-";
    private static final String SUFFIX = ".java";

    public static final int JSON_INDENT = 4;
    public static final int V = 0x1;

    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;
    public static final int A = 0x6;

    private static final int JSON = 0x7;
    private static final int XML = 0x8;

    private static final int STACK_TRACE_INDEX = 5;

    private static String mGlobalTag;
    private static boolean mIsGlobalTagEmpty = true;
    private static boolean IS_SHOW_LOG = true;
    private static int methodCount = 2;

    public static void init(boolean isShowLog) {
        IS_SHOW_LOG = isShowLog;
    }

    public static void init(boolean isShowLog, @Nullable String tag) {
        IS_SHOW_LOG = isShowLog;
        mGlobalTag = tag;
        mIsGlobalTagEmpty = TextUtils.isEmpty(mGlobalTag);
    }

    public static void v() {
        printLog(V, null, DEFAULT_MESSAGE);
    }

    public static void v(Object msg) {
        printLog(V, null, null, msg);
    }

    public static void v(String tag, Object... objects) {
        printLog(V, tag, null, objects);
    }

    //public static void v(String tag, String src, Object... objects) {
    //  printLog(V, tag, src, objects);
    //}

    public static void d() {
        printLog(D, null, null, DEFAULT_MESSAGE);
    }

    public static void d(Object msg) {
        printLog(D, null, null, msg);
    }

    public static void d(String tag, Object... objects) {
        printLog(D, tag, null, objects);
    }

    //public static void d(String tag, String src, Object... objects) {
    //  printLog(D, tag, src, objects);
    //}

    public static void i() {
        printLog(I, null, null, DEFAULT_MESSAGE);
    }

    public static void i(Object msg) {
        printLog(I, null, null, msg);
    }

    public static void i(String tag, Object... objects) {
        printLog(I, tag, null, objects);
    }

    //public static void i(String tag, String src, Object... objects) {
    //  printLog(I, tag, src, objects);
    //}

    public static void w() {
        printLog(W, null, null, DEFAULT_MESSAGE);
    }

    public static void w(Object msg) {
        printLog(W, null, null, msg);
    }

    public static void w(String tag, Object... objects) {
        printLog(W, tag, null, objects);
    }

    //public static void w(String tag, String src, Object... objects) {
    //  printLog(W, tag, src, objects);
    //}

    public static void e() {
        printLog(E, null, null, DEFAULT_MESSAGE);
    }

    public static void e(Object msg) {
        printLog(E, null, null, msg);
    }

    public static void e(String tag, Object... objects) {
        printLog(E, tag, null, objects);
    }

    //public static void e(String tag, String src, Object... objects) {
    //  printLog(E, tag, src, objects);
    //}

    public static void a() {
        printLog(A, null, null, DEFAULT_MESSAGE);
    }

    public static void a(Object msg) {
        printLog(A, null, null, msg);
    }

    public static void a(String tag, Object... objects) {
        printLog(A, tag, null, objects);
    }

    //public static void a(String tag, String src, Object... objects) {
    //  printLog(A, tag, src, objects);
    //}

    public static void json(String jsonFormat) {
        printLog(JSON, null, null, jsonFormat);
    }

    public static void json(String tag, String jsonFormat) {
        printLog(JSON, tag, null, jsonFormat);
    }

    public static void json(String tag, String src, String jsonFormat) {
        printLog(JSON, tag, src, jsonFormat);
    }

    public static void xml(String xml) {
        printLog(XML, null, null, xml);
    }

    public static void xml(String tag, String xml) {
        printLog(XML, tag, null, xml);
    }

    public static void file(File targetDirectory, Object msg) {
        printFile(null, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, Object msg) {
        printFile(tag, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, String fileName, Object msg) {
        printFile(tag, targetDirectory, fileName, msg);
    }

    private static void printLog(int type, String tagStr, String src, Object... objects) {

        if (!IS_SHOW_LOG) {
            return;
        }
        String[] contents = wrapperContent(tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case A:
                BaseLog.printDefault(type, tag, src, headString, msg);
                break;
            case JSON:
                if (src != null) headString = src + " ==>  " + headString;
                JsonLog.printJson(tag, msg, headString);
                break;
            case XML:
                XmlLog.printXml(tag, msg, headString);
                break;
        }
    }

    private static void printFile(String tagStr, File targetDirectory, String fileName, Object objectMsg) {

        if (!IS_SHOW_LOG) {
            return;
        }

        String[] contents = wrapperContent(tagStr, objectMsg);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        FileLog.printFile(tag, targetDirectory, fileName, headString, msg);
    }


    private static int getStackOffset(@NonNull StackTraceElement[] trace) {

        for (int i = STACK_TRACE_INDEX; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(JLog.class.getName()) && !name.equals(JLog.class.getName())) {
                return --i;
            }
        }
        return -1;
    }


    private static String[] wrapperContent(String tagStr, Object... objects) {

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();

        int stackOffset = getStackOffset(trace);
        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }
        String className = "";
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 1; i <= methodCount; i++) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            if (i == 1) {
                builder.append("(")
                        .append(trace[stackIndex].getFileName())
                        .append(":")
                        .append(trace[stackIndex].getLineNumber())
                        .append(")#")
                        .append(trace[stackIndex].getMethodName());
                className = trace[stackIndex].getClassName();
            } else {
                builder.append("(")
                        .append(trace[stackIndex].getFileName())
                        .append(":")
                        .append(trace[stackIndex].getLineNumber())
                        .append(")");

            }


            if (i != methodCount) {
                builder.append(" < ");
            }


        }
        builder.append("(Thread id:")
                .append(Thread.currentThread().getId())
                .append(" ,name:")
                .append(Thread.currentThread().getName())
                .append(")");
        builder.append("]");
//        Log.d("THEONE-jiangcy-main", "builder " + builder.toString());
        String tag = (tagStr == null ? className : tagStr);
        if (mIsGlobalTagEmpty) {
            if (TextUtils.isEmpty(tag)) {
                tag = TAG_DEFAULT;
            } else {
                tag = TAG_DEFAULT + "-" + tag;
            }
        } else if (!mIsGlobalTagEmpty) {
            if (TextUtils.isEmpty(tag)) {
                tag = mGlobalTag;
            } else {
                tag = mGlobalTag + "-" + tag;
            }
        }

        String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
        String headString = builder.toString();

        return new String[]{tag, msg, headString};
    }

    private static String getObjectsString(Object... objects) {

        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n");
                } else {
                    stringBuilder.append(PARAM)
                            .append("[")
                            .append(i)
                            .append("]")
                            .append(" = ")
                            .append(object.toString())
                            .append("\n");
                }
            }
            return stringBuilder.toString();
        } else {
            Object object = objects[0];
            return object == null ? NULL : object.toString();
        }
    }

    public static String printStackTrace(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString() + "\n");
        StackTraceElement[] stack = e.getStackTrace();
        StackTraceElement[] duplicates = stack;
        int currentStack = stack.length;

        for (int throwable = 0; throwable < currentStack; ++throwable) {
            StackTraceElement parentStack = duplicates[throwable];
            sb.append("\tat " + parentStack + "\n");
        }

        StackTraceElement[] var8 = stack;

        for (Throwable var9 = e.getCause(); var9 != null; var9 = var9.getCause()) {
            sb.append("Caused by: ");
            sb.append(var9 + "\n");
            StackTraceElement[] var10 = var9.getStackTrace();
            int var11 = countDuplicates(var10, var8);

            for (int i = 0; i < var10.length - var11; ++i) {
                sb.append("\tat " + var10[i] + "\n");
            }

            if (var11 > 0) {
                sb.append("\t... " + var11 + " more" + "\n");
            }

            var8 = var10;
        }

        return sb.toString();
    }

    private static int countDuplicates(StackTraceElement[] currentStack, StackTraceElement[] parentStack) {
        int duplicates = 0;
        int parentIndex = parentStack.length;
        int i = currentStack.length;

        while (true) {
            --i;
            if (i < 0) {
                break;
            }

            --parentIndex;
            if (parentIndex < 0) {
                break;
            }

            StackTraceElement parentFrame = parentStack[parentIndex];
            if (!parentFrame.equals(currentStack[i])) {
                break;
            }

            ++duplicates;
        }

        return duplicates;
    }
}
