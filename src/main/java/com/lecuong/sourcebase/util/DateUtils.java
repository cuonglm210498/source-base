package com.lecuong.sourcebase.util;

import com.lecuong.sourcebase.common.DateTimeCommon;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author CuongLM
 * @created 24/05/2024 - 18:46
 * @project source-base
 */
public class DateUtils {

    public static Date getDate(String str) {
        if (!StringUtils.hasText(str)) return null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC);
        return Date.from(Instant.from(dateTimeFormatter.parse(str)));
    }

    public static String convertDateTimeToString(Date input) {
        if (input == null) return "";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return dateFormat.format(input);
    }

    public static String convertDateToString(Date input) {
        if (input == null) return "";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return dateFormat.format(input);
    }

    public static String convertDateToString(Date input, String format) {
        if (input == null) return "";
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormat.format(input);
    }

    public static Date stringMillisecondsToDate(String input) {
        if (input.trim().isEmpty()) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(input));
        return calendar.getTime();
    }

    public static String getTuThangNamDenThangNam(Date fromDate, Date toDate) {
        if (fromDate == null && toDate == null) return "";
        else if (fromDate == null && toDate != null) {
            DateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.ENGLISH);
            return dateFormat.format(toDate);
        } else if (fromDate != null && toDate == null) {
            DateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.ENGLISH);
            return dateFormat.format(fromDate);
        } else {
            DateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.ENGLISH);
            return dateFormat.format(fromDate).concat(" - ").concat(dateFormat.format(toDate));
        }
    }

    public static Date getFromDate(Date input) {
        if (input == null) {
            try {
                return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("01/01/1900");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(input);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
    }

    public static Date getToDate(Date input) {
        if (input == null) {
            try {
                return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("01/01/2050");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(input);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            // cal.add(Calendar.DATE, 1);
            return cal.getTime();
        }
    }

    public static Date getFromDateSearch(Date input) {
        if (input == null) {
            try {
                return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("01/01/1900");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(input);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.MILLISECOND, -1);
            return cal.getTime();
        }
    }

    public static Date getToDateSearch(Date input) {
        if (input == null) {
            try {
                return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("01/01/2050");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(input);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.MILLISECOND, 1);
            return cal.getTime();
        }
    }

    public static Date getDate(Integer date, Integer month, Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, date);
        return cal.getTime();
    }

    public static Date getStartDateOfMonth(Integer month, Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date getEndDateOfMonth(Integer month, Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date minDateIfNull(Date input) {
        if (input == null) {
            try {
                return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("01/01/1900");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else return input;
    }

    public static Date maxDateIfNull(Date input) {
        if (input == null) {
            try {
                return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("01/01/2050");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else return input;
    }

    public static Date getDateFromText(String input) {
        try {
            if (input.trim().isEmpty())
                return null;
            else if (input.indexOf("/") == -1) {
                return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("01/01/" + input);
            } else if (input.indexOf("/") > 0 && input.indexOf("/") < input.lastIndexOf("/")) {
                return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(input);
            } else {
                return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("01/" + input);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getViDate(String input) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getViDatetime(String input) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).parse(input);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.HOUR, -7);
            return c.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getFirstDayOfMonth(Date input) {
        Calendar cal = Calendar.getInstance();
        Calendar calInput = Calendar.getInstance();
        calInput.setTime(input);
        cal.set(calInput.get(Calendar.YEAR), calInput.get(Calendar.MONTH), 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date getLastDayOfMonth(Date input) {
        Calendar cal = Calendar.getInstance();
        Calendar calInput = Calendar.getInstance();
        calInput.setTime(input);
        cal.set(calInput.get(Calendar.YEAR), calInput.get(Calendar.MONTH) + 2, 1);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MILLISECOND, 1);
        return cal.getTime();
    }

    public static Date getFirstDayOfMonth(Integer month, Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date getLastDayOfMonth(Integer month, Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date getFirstDayOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, 1, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date getLastDayOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year + 1, 1, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date getDateByMonthAndYear(Integer year, Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month + 1, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        try {
            return formatter.parse(formatter.format(new Date()));
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static Date getCurrentDatetimeOfDate(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Calendar calCurr = Calendar.getInstance();
            calCurr.setTime(new Date());
            cal.set(Calendar.YEAR, calCurr.get(Calendar.YEAR));
            cal.set(Calendar.MONTH, calCurr.get(Calendar.MONTH));
            cal.set(Calendar.DATE, calCurr.get(Calendar.DATE));
            return cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date addMin(Date date, Integer amount) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MINUTE, amount);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date addDate(Date date, Integer amount) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, amount);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date addMonth(Date date, Integer amount) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, amount);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date addYear(Date date, Integer amount) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.YEAR, amount);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date removeTime(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String getNgayThang(Date input) {
        if (input == null) return "";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.ENGLISH);
        return dateFormat.format(input);
    }

    public static String getThangNam(Date input) {
        if (input == null) return "";
        DateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.ENGLISH);
        return dateFormat.format(input);
    }

    public static int getDiffYears(Date first, Date last) {
        if (first.after(last)) return 0;
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static int getDiffMonths(Date first, Date last) {
        if (first.after(last)) return 0;
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int years = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        int months = 12 * years;
        if (a.get(Calendar.MONTH) < b.get(Calendar.MONTH)) {
            months += b.get(Calendar.MONTH) - a.get(Calendar.MONTH);
        }
        return months;
    }

    public static long getDiffDays(Date first, Date last) {
        if (first.after(last)) return 0;
        return Duration.between(first.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), last.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).toDays();
    }

    public static long getDiffDays2(Date first, Date last) {
        if (first.after(last)) return 0;
        long diffInMillies = Math.abs(last.getTime() - first.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }

    public static double getDiffHours(Date first, Date last) {
        Calendar a = getCalendar(first);
        a.set(Calendar.YEAR, 2000);
        a.set(Calendar.MONTH, 1);
        a.set(Calendar.DATE, 1);
        Calendar b = getCalendar(last);
        b.set(Calendar.YEAR, 2000);
        b.set(Calendar.MONTH, 1);
        b.set(Calendar.DATE, 1);
        long diff = b.getTime().getTime() - a.getTime().getTime();
        return TimeUnit.MILLISECONDS.toHours(diff);
    }

    public static double getDiffMins(Date first, Date last) {
        Calendar a = getCalendar(first);
        a.set(Calendar.YEAR, 2000);
        a.set(Calendar.MONTH, 1);
        a.set(Calendar.DATE, 1);
        Calendar b = getCalendar(last);
        b.set(Calendar.YEAR, 2000);
        b.set(Calendar.MONTH, 1);
        b.set(Calendar.DATE, 1);
        return b.get(Calendar.MINUTE) - a.get(Calendar.MINUTE);
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static Date getNgayNghiHuuNam(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Integer month = cal.get(Calendar.MONTH) + 1;
        if (cal.get(Calendar.YEAR) > 1960 && month < 10) {
            cal.add(Calendar.YEAR, 60);
            cal.add(Calendar.MONTH, 3);
        } else if ((cal.get(Calendar.YEAR) > 1960 && month > 9) || (cal.get(Calendar.YEAR) > 1961 && month < 7)) {
            cal.add(Calendar.YEAR, 60);
            cal.add(Calendar.MONTH, 6);
        } else if ((cal.get(Calendar.YEAR) > 1961 && month > 6) || (cal.get(Calendar.YEAR) > 1962 && month < 4)) {
            cal.add(Calendar.YEAR, 60);
            cal.add(Calendar.MONTH, 9);
        } else if ((cal.get(Calendar.YEAR) > 1962 && month > 3)) {
            cal.add(Calendar.YEAR, 61);
        } else if ((cal.get(Calendar.YEAR) > 1963 && month < 10)) {
            cal.add(Calendar.YEAR, 61);
            cal.add(Calendar.MONTH, 3);
        } else if ((cal.get(Calendar.YEAR) > 1963 && month > 9) || (cal.get(Calendar.YEAR) > 1964 && month < 7)) {
            cal.add(Calendar.YEAR, 61);
            cal.add(Calendar.MONTH, 6);
        } else if ((cal.get(Calendar.YEAR) > 1964 && month > 6) || (cal.get(Calendar.YEAR) > 1965 && month < 4)) {
            cal.add(Calendar.YEAR, 61);
            cal.add(Calendar.MONTH, 9);
        } else if ((cal.get(Calendar.YEAR) > 1965 && month > 3)) {
            cal.add(Calendar.YEAR, 62);
        } else {
            cal.add(Calendar.YEAR, 60);
        }
        return cal.getTime();
    }

    public static Date getNgayNghiHuuNu(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Integer month = cal.get(Calendar.MONTH) + 1;
        if (cal.get(Calendar.YEAR) > 1965 && month < 9) {
            cal.add(Calendar.YEAR, 55);
            cal.add(Calendar.MONTH, 4);
        } else if ((cal.get(Calendar.YEAR) > 1965 && month > 8) || (cal.get(Calendar.YEAR) > 1966 && month < 5)) {
            cal.add(Calendar.YEAR, 55);
            cal.add(Calendar.MONTH, 8);
        } else if ((cal.get(Calendar.YEAR) > 1966 && month > 4)) {
            cal.add(Calendar.YEAR, 56);
        } else if ((cal.get(Calendar.YEAR) > 1967 && month < 9)) {
            cal.add(Calendar.YEAR, 56);
            cal.add(Calendar.MONTH, 4);
        } else if ((cal.get(Calendar.YEAR) > 1967 && month > 8) || (cal.get(Calendar.YEAR) > 1968 && month < 5)) {
            cal.add(Calendar.YEAR, 56);
            cal.add(Calendar.MONTH, 8);
        } else if ((cal.get(Calendar.YEAR) > 1968 && month > 4)) {
            cal.add(Calendar.YEAR, 57);
        } else if ((cal.get(Calendar.YEAR) > 1969 && month < 9)) {
            cal.add(Calendar.YEAR, 57);
            cal.add(Calendar.MONTH, 4);
        } else if ((cal.get(Calendar.YEAR) > 1969 && month > 8) || (cal.get(Calendar.YEAR) > 1970 && month < 5)) {
            cal.add(Calendar.YEAR, 57);
            cal.add(Calendar.MONTH, 8);
        } else if ((cal.get(Calendar.YEAR) > 1970 && month > 4)) {
            cal.add(Calendar.YEAR, 58);
        } else if ((cal.get(Calendar.YEAR) > 1971 && month < 9)) {
            cal.add(Calendar.YEAR, 58);
            cal.add(Calendar.MONTH, 4);
        } else if ((cal.get(Calendar.YEAR) > 1971 && month > 8) || (cal.get(Calendar.YEAR) > 1972 && month < 5)) {
            cal.add(Calendar.YEAR, 58);
            cal.add(Calendar.MONTH, 8);
        } else if ((cal.get(Calendar.YEAR) > 1972 && month > 4)) {
            cal.add(Calendar.YEAR, 59);
        } else if ((cal.get(Calendar.YEAR) > 1973 && month < 9)) {
            cal.add(Calendar.YEAR, 59);
            cal.add(Calendar.MONTH, 4);
        } else if ((cal.get(Calendar.YEAR) > 1973 && month > 8) || (cal.get(Calendar.YEAR) > 1974 && month < 5)) {
            cal.add(Calendar.YEAR, 59);
            cal.add(Calendar.MONTH, 8);
        } else if ((cal.get(Calendar.YEAR) > 1974 && month > 4)) {
            cal.add(Calendar.YEAR, 60);
        } else {
            cal.add(Calendar.YEAR, 55);
        }
        return cal.getTime();
    }

    public static Integer getMonth(Date date) {
        if (date == null) return -1;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static Integer getYear(Date date) {
        if (date == null) return -1;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static Integer getDay(Date date) {
        if (date == null) return -1;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getDayOfWeek(Date date) {
        if (date == null) return -1;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static Date convertToDate(String value)  {
        try {
            return new SimpleDateFormat(DateTimeCommon.DateTimeFormat.DD_MM_YYYY).parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date today() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date plusDay(Date startDate, Integer numberExtraDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, numberExtraDays);
        return calendar.getTime();
    }
}
