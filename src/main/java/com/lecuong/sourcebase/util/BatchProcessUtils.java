package com.lecuong.sourcebase.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CuongLM
 * @created 16/05/2024 - 19:35
 * @project source-base
 */
public class BatchProcessUtils {

    /**
     * Hàm xử lý chia list to thành nhiều list nhỏ theo số lượng (size)
     * VD: 1 list user có 1000 user và size = 10, hàm xử lý sẽ tách 1 list gồm các list nhỏ mà mỗi list nhỏ gồm 10 user
     */
    public static <T> List<List<T>> partitionList(List<T> list, int size) {
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }
}
