package com.lecuong.sourcebase.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * @author CuongLM18
 * @created 06/06/2023 - 3:27 PM
 * @project source-base
 */
@Slf4j
public class DataUtil {

    public static Integer pageSize(Pageable pageable) {
        return (pageable.getPageNumber()) * pageable.getPageSize();
    }

    public static Double safeToDouble(Object obj1) {
        return safeToDouble(obj1, 0.0);
    }

    public static int safeToInt(Object obj1) {
        return safeToInt(obj1, 0);
    }

    public static Long safeToLong(Object obj1) {
        return safeToLong(obj1, 0L);
    }

    public static String safeToString(Object obj1, String defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }

        return obj1.toString().trim();
    }

    public static String safeToString(Object obj1) {
        return safeToString(obj1, "");
    }

    public static String[] safeToArrayString(Object obj1) {
        return safeToString(obj1, "").split(", ");
    }

    public static List<String> safeToListString(Object obj1) {
        return Arrays.asList(safeToString(obj1, "").split(", "));
    }

    public static Date safeToDate(Object obj1) {
        if (obj1 == null) {
            return null;
        }
        return (Date) obj1;
    }

    public static LocalDate safeToLocalDate(Object obj1) {
        if (obj1 == null) {
            return null;
        }
        return Instant.ofEpochMilli(((Date) obj1).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Double safeToDouble(Object obj1, Double defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(obj1.toString());
        } catch (final NumberFormatException nfe) {
            log.error(nfe.getMessage(), nfe);
            return defaultValue;
        }
    }

    public static int safeToInt(Object obj1, int defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }
        try {
            String data = obj1.toString();
            if (data.contains(".")) {
                data = data.substring(0, data.indexOf("."));
            }

            if (data.contains(",")) {
                data = data.substring(0, data.indexOf(","));
            }

            return Integer.parseInt(data);
        } catch (final NumberFormatException nfe) {
            log.error(nfe.getMessage(), nfe);
            return defaultValue;
        }
    }

    public static Long safeToLong(Object obj1, Long defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }
        if (obj1 instanceof BigDecimal) {
            return ((BigDecimal) obj1).longValue();
        }
        if (obj1 instanceof BigInteger) {
            return ((BigInteger) obj1).longValue();
        }
        if (obj1 instanceof Double) {
            return ((Double) obj1).longValue();
        }

        try {
            return Long.parseLong(obj1.toString());
        } catch (final NumberFormatException nfe) {
            log.error(nfe.getMessage(), nfe);
            return defaultValue;
        }
    }

    public static Short safeToShort(Object obj1, Short defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }
        try {
            return Short.parseShort(obj1.toString());
        } catch (final NumberFormatException nfe) {
            log.error(nfe.getMessage(), nfe);
            return defaultValue;
        }
    }

    public static Short safeToShort(Object obj1) {
        return safeToShort(obj1, (short) 0);
    }

    public static boolean isNullOrEmpty(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(final Object[] collection) {
        return collection == null || collection.length == 0;
    }

    public static boolean isNullOrEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNullOrEmpty(final Object obj) {
        return obj == null || obj.toString().isEmpty();
    }
}
