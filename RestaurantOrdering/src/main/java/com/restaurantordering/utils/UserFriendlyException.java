package com.restaurantordering.utils;
/**
 * 异常包裹
 */
public class UserFriendlyException extends RuntimeException {
    private Integer errorCode;
    private String message;

    public UserFriendlyException(String message) {
        super(message);
        this.errorCode = 0;
    }

    public UserFriendlyException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * 条件为真时抛出异常消息
     *
     * @param bool
     * @param errorMessage
     */
    public static void throwException(boolean bool, String errorMessage) {
        if (bool) {
            throw new UserFriendlyException(errorMessage);
        }
    }

    /**
     * 如果无权访问时抛出异常消息
     *
     * @param bool
     */
    public static void throwBrokenAccessControlException(boolean bool) {
        throwException(bool, "无权访问该数据");
    }

    public static void throwException(String errorMessage) {
        throw new UserFriendlyException(errorMessage);
    }

    /*public void setCode(Integer code) {
        this.errorCode = code;
    }*/
}