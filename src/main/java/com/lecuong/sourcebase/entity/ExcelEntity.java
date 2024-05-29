package com.lecuong.sourcebase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class ExcelEntity {
    private Object col1;
    private Object col2;
    private Object col3;
    private Object col4;
    private Object col5;
    private Object col6;
    private Object col7;
    private Object col8;
    private Object col9;
    private Object col10;

    @Tolerate
    public ExcelEntity() {
    }
}
