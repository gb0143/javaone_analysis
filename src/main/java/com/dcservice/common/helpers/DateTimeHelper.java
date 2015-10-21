package com.dcservice.common.helpers;

import static com.dcservice.common.helpers.ValidationHelper.isNullOrEmpty;
import static com.dcservice.common.helpers.ValidationHelper.log;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.dcservice.all.base.BaseBaseClass;
import com.dcservice.common.exceptions.PersistenceBeanException;
import com.dcservice.persistence.models.base.BaseModel;

public class DateTimeHelper extends BaseBaseClass {

  public static String datePattern = "MM/dd/yyyy";

  private static String datePatternNoYear = "dd MMM";

  private static String timePattern = "HH:mm";

  private static String timePatternWithSuffix = "HH:mm a";

  private static String timePatternWithSeconds = "HH:mm:ss";

  private static String mySQLDatePattern = "yyyy-MM-dd";

  private static String mySQLDateTimePattern = "yyyy-MM-dd HH:mm:ss";

  private static String pathDatePattern = "MM-dd-yyyy";

  private static String pathDateTimePattern = "MM-dd-yyyy HH:mm";

  private static String pathDateTimeWithSecondsPattern = "MM-dd-yyyy HH:mm";

  private static String dotsDatePattern = "dd.MM.yyyy";

  private static String datePatternWithMinutes = "MM/dd/yyyy HH:mm";

  private static String datePatternWithSeconds = "MM/dd/yyyy HH:mm:ss";

  private static String datePatternWithMinutesAndSuffix = "MM/dd/yyyy HH:mm a";

  private static String datePatternWithTimezone = "dd MMM yyyy HH:mm Z";

  public static String snapshotDatePattern = "dd-MMM-yyyy HH:mm Z";

  private static String datePatternSlashWithTimezone = "dd/MM/yyyy HH:mm Z";

  private static String datePatternDotsWithTimezone = "dd.MM.yyyy HH:mm Z";

  private static String appointmentDatePattern = "MM/dd/yyyy hh:mm a";

  private static String appointmentTimePattern = "hh:mm a";

  private static String postgresTimePattern = "EEE MMM dd HH:mm:ss zzz YYYY";

  private static DateFormat fromCalendar = new SimpleDateFormat("EEE dd MMM HH:mm:ss z yyyy",
      Locale.US);

  private static DateFormat dfm = new SimpleDateFormat(DateTimeHelper.getDatePattern());

  private static DateFormat dotsFormatter = new SimpleDateFormat(dotsDatePattern, Locale.US);

  private static DateFormat posgresFormatter = new SimpleDateFormat(postgresTimePattern);

  private static DateFormat mySqlFormatter = new SimpleDateFormat(mySQLDateTimePattern);

  public static Date getNow() {
    Date date = new Date();

    return date;
  }

  public static Date getDayStart(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    getDayStart(c);
    return c.getTime();
  }

  public static Calendar getDayStart(Calendar c) {
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    return c;
  }

  public static Date getDayEnd(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.HOUR_OF_DAY, 23);
    c.set(Calendar.MINUTE, 59);
    c.set(Calendar.SECOND, 59);
    c.set(Calendar.MILLISECOND, 999);

    return c.getTime();
  }

  public static Calendar getDayEnd(Calendar c) {
    c.set(Calendar.HOUR_OF_DAY, 23);
    c.set(Calendar.MINUTE, 59);
    c.set(Calendar.SECOND, 59);
    c.set(Calendar.MILLISECOND, 999);

    return c;
  }

  public static Date getDate(Date time) {
    long millisInDay = 60 * 60 * 24 * 1000;
    long currentTime = time.getTime();
    long dateOnly = (currentTime / millisInDay) * millisInDay;
    Calendar c = Calendar.getInstance();
    c.setTimeZone(TimeZone.getTimeZone("GMT"));
    Date clearDate = new Date(dateOnly);
    return clearDate;
  }

  @SuppressWarnings("deprecation")
  public static boolean DateLessThenMaxDate(Date date, Date maxDate) {
    Date today = maxDate;
    if (date.getYear() < today.getYear()) {
      return true;
    } else if (date.getYear() == today.getYear() && date.getMonth() < today.getMonth()) {
      return true;
    } else if (date.getYear() == today.getYear() && date.getMonth() == today.getMonth()
	&& date.getDay() <= today.getDay()) {
      return true;
    } else {
      return false;
    }

  }

  public static Date fromString(String str, String format) {
    return fromString(str, format, null);

  }

  public static Date fromString(String str, String format, Locale locale) {
    if (!ValidationHelper.isNullOrEmpty(str)) {
      DateFormat dateFormat = null;
      if (locale != null) {
	dateFormat = new SimpleDateFormat(format, locale);
      } else {
	dateFormat = new SimpleDateFormat(format);
      }
      try {
	return dateFormat.parse(str);
      } catch (ParseException e) {
	e.printStackTrace();
      }

    }

    return null;

  }

  @SuppressWarnings("deprecation")
  public static Date fromAMPMString(String str) {
    return new Date(str);
  }

  public static synchronized Date fromPostgresString(String str) {
    if (!ValidationHelper.isNullOrEmpty(str)) {
      try {
	return posgresFormatter.parse(str);
      } catch (ParseException e2) {
	return null;
      }

    } else {
      return null;
    }
  }

  public static synchronized Date fromMySqlString(String str) {
    if (!ValidationHelper.isNullOrEmpty(str)) {
      try {
	return mySqlFormatter.parse(str);
      } catch (ParseException e2) {
	return null;
      }

    } else {
      return null;
    }
  }

  public static synchronized Date fromString(String str) {
    if (!ValidationHelper.isNullOrEmpty(str)) {
      try {
	return dfm.parse(str);
      } catch (ParseException e2) {
	// log.warn("DateTime.FromString(dfm1.parse(str)) : " + e2);
	try {
	  return dotsFormatter.parse(str);
	} catch (ParseException e) {
	  // log.warn("DateTime.FromString(dfm1.parse(str)) : " + e2);
	}

      }
      try {
	return fromCalendar.parse(str);
      } catch (ParseException e1) {
	// log.warn("DateTime.FromString(fromCalendar.parse(str)) : "+
	// e1);
	return null;
      }

    } else {
      return null;
    }

  }

  public static String toStringTime(Date value) {
    return toFormatedString(value, getTimePattern());
  }

  public static String toFileNameString(Date value) {
    return toFormatedString(value, getPathDateTimePattern());
  }

  public static String toString(Date value) {
    return toFormatedString(value, getDatePattern());
  }

  public static String toStringDateWithDots(Date value) {
    return toFormatedString(value, getDotsDatePattern());
  }

  public static String toStringWithMinutes(Date value) {
    return toFormatedString(value, getDatePatternWithMinutes());
  }

  public static String ToStringWithSeconds(Date value) {
    return toFormatedString(value, getDatePatternWithSeconds());
  }

  public static String ToPathString(Date value) {
    return toFormatedString(value, getPathDatePattern());
  }

  public static String toFormatedString(Date value, String format) {
    return toFormatedString(value, format, null, null);
  }

  public static String ToStringTimeWithSeconds(Date value) {
    return toFormatedString(value, getTimePatternWithSeconds());
  }

  public static String ToDatePatternWithMinutesAndSuffix(Date value) {
    return toFormatedString(value, getDatePatternWithMinutesAndSuffix());
  }

  public static String ToTimeWithSuffix(Date value) {
    return toFormatedString(value, getTimePatternWithSuffix());
  }

  public static String toFormatedString(Date value, String format, Locale local) {
    return toFormatedString(value, format, local, null);
  }

  public static String toFormatedString(Date value, String format, Locale local,
      TimeZone timeZone) {
    SimpleDateFormat df = null;
    String returnValue = "";

    if (value == null) {
      log.error("aDate is null!");
    } else {
      if (local == null) {
	df = new SimpleDateFormat(format);
      } else {
	df = new SimpleDateFormat(format, local);
      }

      if (timeZone != null) {
	df.setTimeZone(timeZone);
      }

      returnValue = df.format(value);
    }

    return returnValue;
  }

  public static String ToMySqlString(Date value) {
    return toFormatedString(value, getMySQLDatePattern());
  }

  public static String ToMySqlStringWithSeconds(Date value) {
    return toFormatedString(value, getMySQLDateTimePattern());
  }

  public static String toPathDateTimeWithSeconds(Date value) {
    return toFormatedString(value, getPathDateTimeWithSecondsPattern());
  }

  public static void setDatePattern(String datePattern) {
    DateTimeHelper.datePattern = datePattern;
  }

  public static String getDatePattern() {
    return datePattern;
  }

  public static void setMySQLDatePattern(String mySQLDatePattern) {
    DateTimeHelper.mySQLDatePattern = mySQLDatePattern;
  }

  public static String getMySQLDatePattern() {
    return mySQLDatePattern;
  }

  public static void setPathDatePattern(String pathDatePattern) {
    DateTimeHelper.pathDatePattern = pathDatePattern;
  }

  public static String getPathDatePattern() {
    return pathDatePattern;
  }

  public static void setDatePatternWithMinutes(String datePatternWithMinutes) {
    DateTimeHelper.datePatternWithMinutes = datePatternWithMinutes;
  }

  public static String getDatePatternWithMinutes() {
    return datePatternWithMinutes;
  }

  public static void setDatePatternWithSeconds(String datePatternWithSeconds) {
    DateTimeHelper.datePatternWithSeconds = datePatternWithSeconds;
  }

  public static String getDatePatternWithSeconds() {
    return datePatternWithSeconds;
  }

  public static void setPathDateTimePattern(String pathDateTimePattern) {
    DateTimeHelper.pathDateTimePattern = pathDateTimePattern;
  }

  public static String getPathDateTimePattern() {
    return pathDateTimePattern;
  }

  public static void setMySQLDateTimePattern(String mySQLDateTimePattern) {
    DateTimeHelper.mySQLDateTimePattern = mySQLDateTimePattern;
  }

  public static String getMySQLDateTimePattern() {
    return mySQLDateTimePattern;
  }

  public static String getTimePattern() {
    return timePattern;
  }

  public static void setTimePattern(String timePattern) {
    DateTimeHelper.timePattern = timePattern;
  }

  public static String getTimeString(Calendar c) {
    return String.format("%s:%s", String.valueOf(c.get(Calendar.HOUR_OF_DAY)),
	String.valueOf(c.get(Calendar.MINUTE)));
  }

  public static String getTimePatternWithSeconds() {
    return timePatternWithSeconds;
  }

  public static void setTimePatternWithSeconds(String timePatternWithSeconds) {
    DateTimeHelper.timePatternWithSeconds = timePatternWithSeconds;
  }

  public static String getDotsDatePattern() {
    return dotsDatePattern;
  }

  public static void setDotsDatePattern(String dotsDatePattern) {
    DateTimeHelper.dotsDatePattern = dotsDatePattern;
  }

  public static String getDatePatternWithMinutesAndSuffix() {
    return datePatternWithMinutesAndSuffix;
  }

  public static void setDatePatternWithMinutesAndSuffix(String datePatternWithMinutesAndSuffix) {
    DateTimeHelper.datePatternWithMinutesAndSuffix = datePatternWithMinutesAndSuffix;
  }

  public static String getDatePatternWithTimezone() {
    return datePatternWithTimezone;
  }

  public static String getDatePatternWithTimezone(Locale locale) {
    StringBuilder sb = new StringBuilder();
    sb.append(
	((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, locale)).toPattern());
    sb.append(" ");
    sb.append(
	((SimpleDateFormat) DateFormat.getTimeInstance(DateFormat.SHORT, locale)).toPattern());
    sb.append(" Z");
    return sb.toString();
  }

  public static void setDatePatternWithTimezone(String datePatternWithTimezone) {
    DateTimeHelper.datePatternWithTimezone = datePatternWithTimezone;
  }

  public static Date convertToGMT(Date date) {
    SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
    dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

    // Local time zone
    SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    // Time in GMT
    Date gmtDate = null;
    try {
      gmtDate = dateFormatLocal.parse(dateFormatGmt.format(date));
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return gmtDate;
  }

  public static Date convertTimeZones(Date date, TimeZone from, TimeZone to) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(date.getTime());
    TimeZone fromTimeZone = from;
    TimeZone toTimeZone = to;

    calendar.setTimeZone(fromTimeZone);
    calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
    if (fromTimeZone.inDaylightTime(calendar.getTime())) {
      calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
    }

    calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
    if (toTimeZone.inDaylightTime(calendar.getTime())) {
      calendar.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
    }

    calendar.setTimeZone(toTimeZone);

    return calendar.getTime();
  }

  public static String normalizeTimezone(String str) {
    int length = str.length();
    StringBuilder sb = new StringBuilder(str.substring(0, length - 5));
    sb.append("UTC");

    if (Integer.parseInt(str.substring(length - 4, length)) != 0) {
      sb.append(str.substring(length - 5, length - 2));
      sb.append(":");
      sb.append(str.substring(length - 2, length));
    }
    return sb.toString();
  }

  public static String normalizeAMPM(String str) {
    str = str.replaceAll(" AM", " am");
    str = str.replaceAll(" PM", " pm");
    return str;
  }

  public static String getDatePatternNoYear() {
    return datePatternNoYear;
  }

  public static void setDatePatternNoYear(String datePatternNoYear) {
    DateTimeHelper.datePatternNoYear = datePatternNoYear;
  }

  public static String getDatePatternSlashWithTimezone() {
    return datePatternSlashWithTimezone;
  }

  public static void setDatePatternSlashWithTimezone(String datePatternSlashWithTimezone) {
    DateTimeHelper.datePatternSlashWithTimezone = datePatternSlashWithTimezone;
  }

  public static String getDatePatternDotsWithTimezone() {
    return datePatternDotsWithTimezone;
  }

  public static void setDatePatternDotsWithTimezone(String datePatternDotsWithTimezone) {
    DateTimeHelper.datePatternDotsWithTimezone = datePatternDotsWithTimezone;
  }

  public static String toJSFormat(String javaFormat) {
    if (javaFormat.contains("yyyy")) {
      javaFormat = javaFormat.replace("yyyy", "%Y");
    }
    if (javaFormat.contains("MMM")) {
      javaFormat = javaFormat.replace("MMM", "%b");
    }
    if (javaFormat.contains("MM")) {
      javaFormat = javaFormat.replace("MM", "%m");
    }
    if (javaFormat.contains("dd")) {
      javaFormat = javaFormat.replace("dd", "%d");
    }

    return javaFormat;
  }

  public static String getTimePatternWithSuffix() {
    return timePatternWithSuffix;
  }

  public static void setTimePatternWithSuffix(String timePatternWithSuffix) {
    DateTimeHelper.timePatternWithSuffix = timePatternWithSuffix;
  }

  public static String getPathDateTimeWithSecondsPattern() {
    return pathDateTimeWithSecondsPattern;
  }

  public static void setPathDateTimeWithSecondsPattern(String pathDateTimeWithSecondsPattern) {
    DateTimeHelper.pathDateTimeWithSecondsPattern = pathDateTimeWithSecondsPattern;
  }

  public static String getAppointmentDatePattern() {
    return appointmentDatePattern;
  }

  public static void setAppointmentDatePattern(String appointmentDatePattern) {
    DateTimeHelper.appointmentDatePattern = appointmentDatePattern;
  }

  public static String getAppointmentTimePattern() {
    return appointmentTimePattern;
  }

  public static void setAppointmentTimePattern(String appointmentTimePattern) {
    DateTimeHelper.appointmentTimePattern = appointmentTimePattern;
  }

  public static Date getCurrentYearFirstDate() {
    return getFirstDate(new Date());
  }

  public static Date getFirstDate(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MONTH, Calendar.JANUARY);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    return getDayStart(calendar.getTime());
  }

  public static Date getCurrentYearLastDate() {
    return getLastDate(new Date());
  }

  public static Date getLastDate(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MONTH, Calendar.DECEMBER);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    return getDayEnd(calendar.getTime());
  }

  public static boolean isDateInInterval(Date date, Date minDate, Date maxDate) {
    return date.compareTo(minDate) >= 0 && date.compareTo(maxDate) <= 0;
  }

  public static String getPostgresTimePattern() {
    return postgresTimePattern;
  }

  public static void setPostgresTimePattern(String postgresTimePattern) {
    DateTimeHelper.postgresTimePattern = postgresTimePattern;
  }

  public static class ComboboxHelper extends BaseBaseClass {
    public static <T extends BaseModel> List<SelectItem> ratings() {
      List<SelectItem> items = new ArrayList<SelectItem>();
      for (int i = 1; i <= 5; i++) {
	items.add(new SelectItem(new Double(i), String.valueOf(i)));
      }
      return items;
    }

    public static <T extends BaseModel> List<SelectItem> listToCombobox(List<T> list,
	boolean showNotSelected) {
      return fillList(list, showNotSelected, false);
    }

    public static <T extends BaseModel> List<SelectItem> listToCombobox(List<T> list) {
      return fillList(list, false, false);
    }

    public static <T> List<SelectItem> enumToCombobox(Class<T> clazz, boolean showNotSelected) {
      if (clazz.isEnum()) {
	return fillList(clazz.getEnumConstants(), null, showNotSelected, false);
      }
      return Collections.emptyList();
    }

    public static <T> List<SelectItem> enumToCombobox(Class<T> clazz) {
      return enumToCombobox(clazz, false);
    }

    public static <T> List<SelectItem> fillList(Class<T> clazz) throws Exception {
      return fillList(clazz, true);
    }

    public static <T> List<SelectItem> fillList(Class<T> clazz, boolean showNotSelected)
	throws Exception {
      return fillList(clazz, showNotSelected, false);
    }

    public static <T> List<SelectItem> fillList(Class<T> clazz, boolean showNotSelected,
	boolean showAllElement) throws Exception {
      if (clazz.isEnum()) {
	return fillList(clazz.getEnumConstants(), null, showNotSelected, showAllElement);
      }
      List<SelectItem> list = new ArrayList<SelectItem>();
      if (showNotSelected) {
	list.add(SelectItemHelper.getNotSelected());
      }
      if (showAllElement) {
	list.add(SelectItemHelper.getAllElement());
      }
      return list;
    }

    public static <T extends BaseModel> List<SelectItem> fillList(Class<T> clazz,
	List<Long> excludeList)
	    throws HibernateException, PersistenceBeanException, IllegalAccessException {
      return fillList(clazz, null, excludeList);
    }

    public static <T> List<SelectItem> fillList(Class<T> clazz, T[] excludeList,
	boolean showNotSelected, boolean showAllElement) throws HibernateException {
      return fillList(clazz.getEnumConstants(), excludeList, showNotSelected, showAllElement);
    }

    public static <T extends BaseModel> List<SelectItem> fillList(Class<T> clazz, Order order,
	List<Long> excludeList)
	    throws HibernateException, PersistenceBeanException, IllegalAccessException {
      return fillList(clazz, new ArrayList<Criterion>(0), order, excludeList);
    }

    public static <T extends BaseModel> List<SelectItem> fillList(Class<T> clazz,
	List<Criterion> ctiretions, Order order, List<Long> excludeList)
	    throws HibernateException, PersistenceBeanException, IllegalAccessException {
      List<SelectItem> list = new ArrayList<SelectItem>();
      list.add(SelectItemHelper.getNotSelected());

      /*
       * for (T item : DaoManager.load(clazz, ctiretions, order)) { if
       * (excludeList == null || !excludeList.contains(item.getId())) {
       * list.add(new SelectItem(item.getId(), item.toString())); } }
       */
      return list;

    }

    public static <T extends BaseModel> List<SelectItem> fillList(Class<T> clazz, Order order)
	throws HibernateException, PersistenceBeanException, IllegalAccessException {
      return fillList(clazz, order, true);
    }

    public static <T extends BaseModel> List<SelectItem> fillList(Class<T> clazz, Order order,
	boolean addNotSelected)
	    throws HibernateException, PersistenceBeanException, IllegalAccessException {
      return fillList(clazz, order, new ArrayList<Criterion>(), addNotSelected);
    }

    public static <T extends BaseModel> List<SelectItem> fillList(Class<T> clazz, Order order,
	List<Criterion> criterions, boolean addNotSelected)
	    throws HibernateException, PersistenceBeanException, IllegalAccessException {
      return fillList(clazz, order, criterions, addNotSelected, false);
    }

    public static <T extends BaseModel> List<SelectItem> fillList(Class<T> clazz, Order order,
	List<Criterion> criterions, boolean addNotSelected, boolean addAllElements)
	    throws HibernateException, PersistenceBeanException, IllegalAccessException {
      List<SelectItem> list = new ArrayList<SelectItem>();
      if (addNotSelected) {
	list.add(SelectItemHelper.getNotSelected());
      }
      if (addAllElements) {
	list.add(SelectItemHelper.getAllElement());
      }

      /*
       * for (T item : DaoManager.load(clazz, criterions, order)) { list.add(new
       * SelectItem(item.getId(), item.toString())); }
       */

      return list;
    }

    public static <T extends BaseModel> List<SelectItem> fillList(Class<T> clazz, Order order,
	List<Criterion> criterions, List<String> criteriaAlias, boolean addNotSelected)
	    throws HibernateException, PersistenceBeanException, IllegalAccessException {
      List<SelectItem> list = new ArrayList<SelectItem>();
      if (addNotSelected) {
	list.add(SelectItemHelper.getNotSelected());
      }

      /*
       * for (T item : DaoManager.load(clazz, criteriaAlias, criterions, order))
       * { list.add(new SelectItem(item.getId(), item.toString())); }
       */

      return list;
    }

    public static <T extends BaseModel> List<SelectItem> fillList(List<T> list,
	boolean showNotSelected) {
      return fillList(list, showNotSelected, false);
    }

    public static <T extends BaseModel> List<SelectItem> fillList(List<T> list,
	boolean showNotSelected, boolean showAllElement) {
      List<SelectItem> list1 = new ArrayList<SelectItem>();
      if (showNotSelected) {
	list1.add(SelectItemHelper.getNotSelected());
      } else if (showAllElement) {
	list1.add(SelectItemHelper.getAllElement());
      }
      for (T item : list) {
	if (!(isNullOrEmpty(item.getId()) || isNullOrEmpty(item.toString()))) {
	  list1.add(new SelectItem(item.getId(), item.toString()));
	}
      }
      return list1;
    }

    public static List<SelectItem> fillList(Object[] array) {
      return fillList(array, null, true, false);
    }

    public static List<SelectItem> fillList(Object[] array, boolean showNotSelected,
	boolean showAllElement) {
      return fillList(array, null, showNotSelected, showAllElement);
    }

    public static List<SelectItem> fillList(Object[] array, Object[] exclude,
	boolean showNotSelected, boolean showAllElement) {
      List<SelectItem> list = new ArrayList<SelectItem>();
      if (showNotSelected) {
	list.add(SelectItemHelper.getNotSelected());
      }
      if (showAllElement) {
	list.add(SelectItemHelper.getAllElement());
      }
      if (array.length > 0) {
	boolean isEnum = array[0].getClass().isEnum();

	Method getIdMethod = null;
	try {
	  getIdMethod = array[0].getClass().getMethod("getId");
	} catch (SecurityException e) {
	  log.error(e);
	} catch (NoSuchMethodException e) {
	}

	for (Object item : array) {
	  if (exclude != null && contains(exclude, item)) {
	    continue;
	  }

	  try {
	    list.add(new SelectItem(getIdMethod == null
		? isEnum ? item.getClass().getMethod("name").invoke(item) : item.toString()
		: getIdMethod.invoke(item), item.toString()));
	  } catch (Exception e) {
	    log.error(e);
	  }
	}
      }
      return list;
    }

    private static boolean contains(Object[] list, Object obj) {
      for (Object item : list) {
	if (item.equals(obj)) {
	  return true;
	}
      }

      return false;
    }

    public static List<SelectItem> fillList(int from, int to, String prefix,
	boolean addNumbersPrefix) {
      List<SelectItem> list = new ArrayList<SelectItem>();
      for (int i = from; i <= to; i++) {
	if (i == 1 && !isNullOrEmpty(prefix)) {
	  list.add(new SelectItem(i, String.format("%s", prefix)));
	} else {
	  if (addNumbersPrefix) {
	    list.add(new SelectItem(i, String.format("%d%s %s", i, getNumberPrefix(i), prefix)));
	  } else {
	    list.add(new SelectItem(i, String.format("%d %s", i, prefix)));
	  }
	}
      }

      return list;
    }

    public static List<SelectItem> fillList(int from, int to, int step) {
      List<SelectItem> list = new ArrayList<SelectItem>();
      for (int i = from; i <= to; i = i + step) {

	list.add(new SelectItem(i, String.valueOf(i)));

      }

      return list;
    }

    public static List<SelectItem> fillWeekDays() {
      DateFormatSymbols dfs = new DateFormatSymbols(Locale.US);
      String weekdays[] = dfs.getWeekdays();
      List<SelectItem> list = new ArrayList<SelectItem>();
      for (int i = 0; i < weekdays.length; i++) {
	if (!isNullOrEmpty(weekdays[i])) {
	  list.add(new SelectItem(i, weekdays[i]));
	}
      }

      return list;
    }

    public static List<SelectItem> fillMonth() {
      DateFormatSymbols dfs = new DateFormatSymbols(Locale.US);
      String month[] = dfs.getMonths();
      List<SelectItem> list = new ArrayList<SelectItem>();
      for (int i = 0; i < month.length; i++) {
	list.add(new SelectItem(i, month[i]));
      }

      return list;
    }

    private static String getNumberPrefix(Integer number) {
      if (number >= 21) {
	switch (Integer.parseInt(number.toString().substring(number.toString().length() - 1,
	    number.toString().length()))) {
	case 1: {
	  return "st";
	}
	case 2: {
	  return "nd";
	}
	case 3: {
	  return "rd";
	}
	default: {
	  return "th";
	}
	}
      }
      switch (number) {
      case 1: {
	return "st";
      }
      case 2: {
	return "nd";
      }
      case 3: {
	return "rd";
      }
      default: {
	return "th";
      }
      }
    }

    public static class SelectItemHelper {
      public static SelectItem getNotSelected() {
	return new SelectItem("", "- " + ResourcesHelper.getString("notSelected") + " -");
      }

      public static SelectItem getVirtualBaseModel() {

	return new SelectItem("-1", "- " + ResourcesHelper.getString("notSelected") + " -");
      }

      public static SelectItem getAllElement() {
	return new SelectItem("", "- " + ResourcesHelper.getString("all") + " -");
      }

      public static SelectItem getAllButton() {
	return new SelectItem(null, ResourcesHelper.getString("all"));
      }

      public static SelectItem getNoneElement() {
	return new SelectItem("", "- " + ResourcesHelper.getString("none") + " -");
      }
    }

  }

}
