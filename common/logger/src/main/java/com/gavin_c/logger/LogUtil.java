package com.gavin_c.logger;

import android.util.Log;

import junit.framework.Assert;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gavin on 2016/7/22.
 */

public class LogUtil {

    private static final String TAB = "    ";
    private static PrintLog mPrintLog = new PrintLog();

    private static String mTagPre = "";
    private static Boolean mEnable = true;
    private static int callStackIndex = 3;
    private static int methodCount = 1;
    private static boolean showMethod = true;
    private static boolean showThread = true;

    public static void setTagPre(String mTagPre) {
        LogUtil.mTagPre = mTagPre;
    }

    public static void setEnable(boolean enable) {
        mEnable = enable;
    }

    public static void setCallStackIndex(int callStackIndex) {
        LogUtil.callStackIndex = callStackIndex;
    }

    public static void setMethodCount(int methodCount) {
        LogUtil.methodCount = methodCount;
    }

    public static void setShowThread(boolean showThread) {
        LogUtil.showThread = showThread;
    }

    public static void setShowMethod(boolean showMethod) {
        LogUtil.showMethod = showMethod;
    }

    public static PrintLog t(String tag) {
        if(tag == null || tag.trim().isEmpty())
            return mPrintLog;
        return mPrintLog.tag(tag);
    }

    public static void v(String message, Object... args) {
        if (!mEnable)
            return;
        mPrintLog.v(message, args);
    }

    public static void v(Throwable tr, String message, Object... args) {
        if (!mEnable)
            return;
        mPrintLog.v(tr, message, args);
    }

    public static void i(String message, Object... args) {
        if (!mEnable)
            return;
        mPrintLog.i(message, args);
    }

    public static void i(Throwable tr, String message, Object... args) {
        if (!mEnable)
            return;
        mPrintLog.i(tr, message, args);
    }

    public static void d(String message, Object... args) {
        if (!mEnable)
            return;
        mPrintLog.d(message, args);
    }

    public static void d(Throwable tr, String message, Object... args) {
        if (!mEnable)
            return;
        mPrintLog.d(tr, message, args);
    }

    public static void w(String message, Object... args) {
        mPrintLog.w(message, args);
    }

    public static void w(Throwable tr, String message, Object... args) {
        mPrintLog.w(tr, message, args);
    }

    public static void w(Throwable tr) {
        mPrintLog.w(tr);
    }

    public static void e(String message, Object... args) {
        mPrintLog.e(message, args);
    }

    public static void e(Throwable tr, String message, Object... args) {
        mPrintLog.e(tr, message, args);
    }

    public static void e(Throwable tr) {
        mPrintLog.e(tr);
    }

    public static void wtf(String message, Object... args) {
        mPrintLog.wtf(message, args);
    }

    public static void wtf(Throwable tr, String message, Object... args) {
        mPrintLog.wtf(tr, message, args);
    }

    public static class PrintLog {

        private final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

        private final String SINGLE_DIVIDER = "────────────────────────────────────────────────────────────────────────────────────────\r\n";

        private final ThreadLocal<String> localTag = new ThreadLocal<>();

        public PrintLog tag(String tag) {
            if (tag != null) {
                localTag.set(tag);
            }
            return this;
        }

        public void v(String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.v(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, null, message, args));
        }

        public void v(Throwable tr, String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.v(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, tr, message, args));
        }

        public void i(String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.i(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, null, message, args));
        }

        public void i(Throwable tr, String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.i(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, tr, message, args));
        }

        public void d(String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.d(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, null, message, args));
        }

        public void d(Throwable tr, String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.d(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, tr, message, args));
        }

        public void w(String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.w(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, null, message, args));
        }

        public void w(Throwable tr) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.w(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, tr, null));
        }


        public void w(Throwable tr, String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.w(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, tr, message, args));
        }

        public void e(Throwable tr) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.e(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, tr, null));
        }

        public void e(String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.e(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, null, message, args));
        }

        public void e(Throwable tr, String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.e(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, tr, message, args));
        }

        public void wtf(String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.wtf(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, null, message, args));
        }

        public void wtf(Throwable tr, String message, Object... args) {
            LogPreparedBean logPreparedBean = getLogPreparedBean();
            Log.wtf(logPreparedBean.tag, formatLogMessage(logPreparedBean.method, tr, message, args));
        }

        private String getStackTraceString(Throwable t) {
            // Don't replace this with Log.getStackTraceString() - it hides
            // UnknownHostException, which is not what we want.
            StringWriter sw = new StringWriter(256);
            PrintWriter pw = new PrintWriter(sw, false);
            t.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        }

        private String getClazzSimpleName(StackTraceElement element) {
            String tag = element.getClassName();
            Matcher m = ANONYMOUS_CLASS.matcher(tag);
            if (m.find()) {
                tag = m.replaceAll("");
            }
            return tag.substring(tag.lastIndexOf('.') + 1);
        }

        private StackTraceElement getStackTraceElement(int index) {
            // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
            // because Robolectric runs them on the JVM but on Android the elements are different.
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            if (stackTrace.length <= index) {
                throw new IllegalStateException(
                        "Synthetic stacktrace didn't have enough elements: are you using proguard?");
            }
            return stackTrace[index];
        }

        private StackTraceElement[] getStackTraceElement(int index, int count) {
            if (count < 1) {
                count = 1;
            }
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            int resultSize = 1;
            if (stackTrace.length - index > count) {
                resultSize = count;
            } else {
                resultSize = stackTrace.length - index;
            }

            StackTraceElement[] result = new StackTraceElement[resultSize];
            for (int i = 0; i < result.length; i++) {
                result[i] = stackTrace[i + index];
            }
            return result;
        }

        private String formatLogMessage(String method, Throwable tr, String message, Object... args) {

            StringBuilder builder = new StringBuilder();
            builder.append(SINGLE_DIVIDER);

            if (showThread) {
                builder.append("├ thread: ")
                        .append(Thread.currentThread().getName())
                        .append("\r\n");
            }

            if (showMethod) {
                method = method.replace("\n", "\n│");
                builder.append("├ method: ")
                        .append(method)
                        .append("\r\n");
            }

            if (message != null) {
                if (args.length > 0) {
                    message = String.format(message, args);
                }
                message = jsonFormat(message);
                message = message.replace("\n", "\n│");
                builder.append("│")
                        .append(message)
                        .append("\r\n");
            }

            if (tr != null) {
                String throwable = getStackTraceString(tr);

                throwable = throwable.replace("\n", "\n│");
                builder.append("│")
                        .append(throwable);
            }

            builder.append(SINGLE_DIVIDER);

            return builder.toString();
        }


        /**
         * @return the appropriate tag based on local or global
         */
        private String getTag() {
            String tag = localTag.get();
            if (tag != null) {
                localTag.remove();
                return tag;
            }
            return null;
        }


        private LogPreparedBean getLogPreparedBean() {
            String tag = getTag();
            int callStackIndex = LogUtil.callStackIndex;

            if (tag == null) {
                callStackIndex = callStackIndex + 1;
            }

            StringBuffer method = new StringBuffer();
            StringBuffer level = new StringBuffer(TAB);
            StackTraceElement[] elements = getStackTraceElement(callStackIndex, methodCount);
            for (int i = elements.length - 1; i >= 0; i--) {
                method.append(elements[i].toString());
                if (i != 0) {
                    method.append("\r\n");
                    method.append(level);
                    level.append(TAB);
                }
            }
            String clazzName = getClazzSimpleName(elements[0]);
            if (tag != null) {
                clazzName = tag;
            }

            StringBuilder builder = new StringBuilder();
            builder.append(mTagPre);
            builder.append(clazzName);

            return new LogPreparedBean(builder.toString(), method.toString());
        }

        class LogPreparedBean {

            public LogPreparedBean(String tag, String method) {
                this.tag = tag;
                this.method = method;
            }

            String tag;
            String method;
        }


        /**
         * 得到格式化json数据  退格用\t 换行用\r
         */
        public String jsonFormat(String jsonStr) {
            int level = 0;
            StringBuffer jsonForMatStr = new StringBuffer();
            for (int i = 0; i < jsonStr.length(); i++) {
                char c = jsonStr.charAt(i);
                if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                    jsonForMatStr.append(getLevelStr(level));
                }
                switch (c) {
                    case '{':
                    case '[':
                        jsonForMatStr.append(c + "\n");
                        level++;
                        break;
                    case ',':
                        jsonForMatStr.append(c + "\n");
                        break;
                    case '}':
                    case ']':
                        jsonForMatStr.append("\n");
                        level--;
                        jsonForMatStr.append(getLevelStr(level));
                        jsonForMatStr.append(c);
                        break;
                    default:
                        jsonForMatStr.append(c);
                        break;
                }
            }

            return jsonForMatStr.toString();

        }

        private String getLevelStr(int level) {
            StringBuffer levelStr = new StringBuffer();
            for (int levelI = 0; levelI < level; levelI++) {
                levelStr.append("\t");
            }
            return levelStr.toString();
        }
    }
}
