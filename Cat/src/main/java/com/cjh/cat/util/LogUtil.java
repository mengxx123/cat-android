package com.cjh.cat.util;

import java.util.Hashtable;

import android.util.Log;

/**
 * 日志工具类
 * 
 * @author 陈建杭
 * @date 2015-11-5 19:04:24
 */
public class LogUtil {
	
	public static final int VERBOSE = Log.VERBOSE;
	public static final int DEBUG = Log.DEBUG;
	public static final int INFO = Log.INFO;
	public static final int WARN = Log.WARN;
	public static final int ERROR = Log.ERROR;

	// 控制某个对象的日志输出
	private boolean isLoggable = true; // 日志开关，默认打印日志
	private int minLogLevel = Log.VERBOSE;
	
	// 控制整个应用的Log输入
	private static boolean globalIsLoggable = true; // 全局日志开关，默认打印日志
	private static int globalMinLevel = Log.VERBOSE; 
	
	private static Hashtable<String, LogUtil> cache = new Hashtable<String, LogUtil>();

	private String tag;
	
	private LogUtil() {}

	public static LogUtil getLogger(Class<?> cls) { // TODO
		LogUtil logger = (LogUtil) cache.get(cls.getName());
		if (logger == null) {
			logger = new LogUtil();
			logger.tag = cls.getSimpleName() + "（" + cls.getName() + "）"; // 以类名作为TAG
			cache.put(cls.getName(), logger);
		}
		return logger;
	}
	
	/**
	 * 获取默认tag（类名）
	 * @return
	 */
	public static String getDefaultTag() {
		StackTraceElement st = getCurStackTraceElement();
		String fileName = st.getFileName();
		String stringArray[] = fileName.split("\\.");
		String tag = stringArray[0];
		return tag;
	}
	
	private static StackTraceElement getCurStackTraceElement() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(LogUtil.class.getName())) { // this.getClass().getName()
				continue;
			}
			return st;
		}
		return null;
	}
	/**
	 * 获取当前执行函数的信息（当前线程名、类所在文件、行数、函数名）
	 * 
	 * @return 信息，例如：[ ClassName.java - methodName - 14 - main 190 - ClassName ]
	 */
	public static String getCurFunctionInfo() {
		StackTraceElement stackTraceElement = getCurStackTraceElement();
		StringBuilder logInfoStringBuilder = new StringBuilder("[ ")
				.append(stackTraceElement.getFileName()) // 文件名
				.append(" - ").append(stackTraceElement.getMethodName()) // 方法名称
				.append(" - ").append(stackTraceElement.getLineNumber()) // 行数
				.append(" - ").append(Thread.currentThread().getName()) // 线程名
				.append(" ").append(Thread.currentThread().getId()) // 线程ID
				.append(" - ").append(stackTraceElement.getClassName()) // 包名+类名
				.append(" ]");

		return logInfoStringBuilder.toString();
	}

	private static void log2(String tag, Object msg, int logLevel) {
		if (globalIsLoggable && logLevel >= globalMinLevel) {
			String info = getCurFunctionInfo();
			
			String msgText = msg == null ? "null" : msg.toString();
			String result = info == null ? msgText : (msgText + " - " + info);

			switch (logLevel) {
				case Log.VERBOSE:
					Log.v(tag, result);
					break;
				case Log.DEBUG:
					Log.d(tag, result);
					break;
				case Log.INFO:
					Log.i(tag, result);
					break;
				case Log.WARN:
					Log.w(tag, result);
					break;
				case Log.ERROR:
					Log.e(tag, result);
					break;
				default:
					break;
			}
		}
		
	}
	
	private void log(String tag, Object msg, int logLevel) {
		if (isLoggable && logLevel >= minLogLevel) {
			log2(tag, msg, logLevel);
		}
	}

	public void verbose(Object msg) {
		verbose(tag, msg);
	}

	public void verbose(String msg, Throwable tr) {
		verbose(msg + '\n' + Log.getStackTraceString(tr));
	}
	
	public void verbose(String tag, Object msg) {
		log(tag, msg, Log.VERBOSE);
	}

	public void debug(Object msg) {
		debug(tag, msg);
	}

	public void debug(String msg, Throwable tr) {
		debug(msg + '\n' + Log.getStackTraceString(tr));
	}

	public void debug(String tag, Object msg) {
		log(tag, msg, Log.DEBUG);
	}
	
	public void info(Object msg) {
		info(tag, msg);
	}

	public void info(String msg, Throwable tr) {
		info(msg + '\n' + Log.getStackTraceString(tr));
	}

	public void info(String tag, Object msg) {
		log(tag, msg, Log.INFO);
	}
	
	public void warn(Object msg) {
		warn(tag, msg);
	}

	public void warn(String msg, Throwable tr) {
		warn(msg + '\n' + Log.getStackTraceString(tr));
	}

	public void warn(String tag, Object msg) {
		log(tag, msg, Log.WARN);
	}
	
	public void error(Object msg) {
		error(tag, msg);
	}

	public void error(String msg, Throwable tr) {
		error(msg + '\n' + Log.getStackTraceString(tr));
	}

	public void error(String tag, Object msg) {
		log(tag, msg, Log.ERROR);
	}
	
	/* 静态方法，方便调用 */

	public static void v(Object msg) {
		v(getDefaultTag(), msg);
	}

	public static void v(String msg, Throwable tr) {
		v(msg + '\n' + Log.getStackTraceString(tr));
	}

	public static void v(String tag, Object msg) {
		log2(tag, msg, Log.VERBOSE);
	}
	
	public static void d(Object msg) {
		d(getDefaultTag(), msg);
	}

	public static void d(String msg, Throwable tr) {
		d(msg + '\n' + Log.getStackTraceString(tr));
	}
	
	public static void d(String tag, Object msg) {
		log2(tag, msg, Log.DEBUG);
	}

	public static void i(Object msg) {
		i(getDefaultTag(), msg);
	}

	public static void i(String msg, Throwable tr) {
		i(msg + '\n' + Log.getStackTraceString(tr));
	}

	public static void i(String tag, Object msg) {
		log2(tag, msg, Log.INFO);
	}
	
	public static void w(Object msg) {
		w(getDefaultTag(), msg);
	}

	public static void w(String msg, Throwable tr) {
		w(msg + '\n' + Log.getStackTraceString(tr));
	}

	public static void w(String tag, Object msg) {
		log2(tag, msg, Log.WARN);
	}
	
	public static void e(Object msg) {
		e(getDefaultTag(), msg);
	}

	public static void e(String msg, Throwable tr) {
		e(msg + '\n' + Log.getStackTraceString(tr));
	}
	
	public static void e(String tag, Object msg) {
		log2(tag, msg, Log.ERROR);
	}
	
	/* 格式化 */
	
	public void formatVerbose(Object msg, Object... args) {
		if (msg == null) {
			throw new IllegalArgumentException("参数msg不能为空");
		}
		verbose(String.format(msg.toString(), args));
	}
	
	public void formatInfo(Object msg, Object... args) {
		if (msg == null) {
			throw new IllegalArgumentException("参数msg不能为空");
		}
		info(String.format(msg.toString(), args));
	}
	
	public void formatWarn(Object msg, Object... args) {
		if (msg == null) {
			throw new IllegalArgumentException("参数msg不能为空");
		}
		warn(String.format(msg.toString(), args));
	}

	public void formatError(Object msg, Object... args) {
		if (msg == null) {
			throw new IllegalArgumentException("参数msg不能为空");
		}
		error(String.format(msg.toString(), args));
	}
	
	/* */
	
	public boolean isLoggable() {
		return isLoggable;
	}

	public void setLoggable(boolean isLoggable) {
		this.isLoggable = isLoggable;
	}

	public int getMinLogLevel() {
		return minLogLevel;
	}

	public void setMinLogLevel(int minLogLevel) {
		this.minLogLevel = minLogLevel;
	}

	public static boolean isGlobalIsLoggable() {
		return globalIsLoggable;
	}

	public static void setGlobalIsLoggable(boolean globalIsLoggable) {
		LogUtil.globalIsLoggable = globalIsLoggable;
	}

	public static int getGlobalMinLevel() {
		return globalMinLevel;
	}

	public static void setGlobalMinLevel(int globalMinLevel) {
		LogUtil.globalMinLevel = globalMinLevel;
	}
}
