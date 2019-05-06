package me.cchao.insomnia.exception;

/**
 * The interface Error code.
 *
 * @author Jcxcc
 * @date 2019 -02-23
 * @since 1.0
 */
public interface ErrorMessage {

    /**
     * 消息
     *
     * @return the string
     */
    String getMessage();


    /**
     * Gets code.
     *
     * @return the code
     */
    Integer getCode();
}
