package com.lecuong.sourcebase.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author CuongLM18
 * @created 29/03/2023 - 4:09 PM
 * @project source-base
 */
public class StringUtils {

    // Vì function validate trong class Validator trả về true thì mới throw exception
    // Nên các function ở đây sẽ phải trả về true nếu muốn throw exception
    // Đúng thành sai - sai thành đúng

    /**
     * @param email
     * @return false if matcher.matches() is true
     * @return true if matcher.matches() is false
     * if function return true then throw exception
     */
    public static boolean verifyEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    /**
     * @param phoneNumber
     * @return false if matcher.matches() is true
     * @return true if matcher.matches() is false
     * if function return true then throw exception
     */
    public static boolean verifyPhoneNumber(String phoneNumber) {
        String regex = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return !matcher.matches();
    }

    /**
     *
     * @param s
     * @return true if s.length = 0 or exist white space
     */
    public static boolean isBlank(String s) {
        if (s.length() == 0)
            return true;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isWhitespace(s.charAt(i)))
                return true;
        }
        return false;
    }

    /**
     *
     * @param password minimum of eight characters, at least one uppercase letter, one lowercase letter, one number, and one special character
     * @return false if matcher.matches() is true
     * @return true if matcher.matches() is false
     * if function return true then throw exception
     */
    public static boolean verifyPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return !matcher.matches();
    }

    public static void main(String[] args) throws JsonProcessingException {
//        UserCreateRequest userCreateRequest = new UserCreateRequest();
//        userCreateRequest.setUserName("cuonglm");
//        userCreateRequest.setPassword("Maiyeuem@123");
//        userCreateRequest.setEmail("abc@gmail.com");
//        userCreateRequest.setPhone("0354947766");
//        Validator.data(userCreateRequest)
//                .validate(UserCreateRequest::getUserName, StringUtils::isBlank, () -> new BusinessException(BusinessCodeResponse.USER_NAME_IS_EMPTY))
//                .validate(UserCreateRequest::getPassword, StringUtils::isBlank, () -> new BusinessException(BusinessCodeResponse.PASSWORD_IS_EMPTY))
//                .validate(UserCreateRequest::getPassword, StringUtils::verifyPassword, () -> new BusinessException(BusinessCodeResponse.PASSWORD_INVALIDATE))
//                .validate(UserCreateRequest::getEmail, StringUtils::verifyEmail, () -> new BusinessException(BusinessCodeResponse.EMAIL_INVALIDATE))
//                .validate(UserCreateRequest::getEmail, StringUtils::isBlank, () -> new BusinessException(BusinessCodeResponse.EMAIL_INVALIDATE))
//                .validate(UserCreateRequest::getPhone, StringUtils::verifyPhoneNumber, () -> new RuntimeException("Error"))
//                .getData();

    }
}
