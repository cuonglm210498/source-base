package com.lecuong.sourcebase.constant;

/**
 * @author cuong.lemanh10
 * @created 21/08/2023
 * @project demo
 */
public class Constant {

    private Constant() {}

    public static class CertificateConstant {

        private CertificateConstant() {}

        public static final int STT_COLUMN = 0;
        public static final String STT_COLUMN_NAME = "STT";

        public static final int EMPLOYEE_COLUMN = 1;
        public static final String EMPLOYEE_COLUMN_NAME = "Mã nhân viên (*)";

        public static final int TYPE_COLUMN = 2;
        public static final String TYPE_COLUMN_NAME = "Phân loại chứng nhận/chứng chỉ (*)";

        public static final int FIELD_COLUMN = 3;
        public static final String FIELD_COLUMN_NAME = "Lĩnh vực";

        public static final int NAME_CERTIFICATE_COLUMN = 4;
        public static final String NAME_CERTIFICATE_COLUMN_NAME = "Tên đầy đủ chứng chỉ (*)";

        public static final int ACRONYM_COLUMN = 5;
        public static final String ACRONYM_COLUMN_NAME = "Viết tắt";

        public static final int ISSUER_COLUMN = 6;
        public static final String ISSUER_COLUMN_NAME = "Hãng cấp";

        public static final int DATE_RANGE_COLUMN = 7;
        public static final String DATE_RANGE_COLUMN_NAME = "Ngày cấp\n" + "(dd/MM/yyyy) (*)";

        public static final int EXPIRATION_DATE_COLUMN = 8;
        public static final String EXPIRATION_DATE_COLUMN_NAME = "Ngày hết hạn\n" + "(dd/MM/yyyy)";

        public static final int RESULT_COLUMN = 9;
        public static final String RESULT_COLUMN_NAME = "Lỗi";
    }
}
