package bht.expense.enterprise.common;

import java.io.Serializable;

/**
 * @author 姚轶文
 * @date 2020/7/13 11:30
 */
public class ResultDto implements Serializable {

    private static final long serialVersionUID = -5372450875750675775L;

    /**
     * 编码
     */
    private Integer code;


    /**
     * 消息
     */
    private String msg;


    /**
     * 数据
     */
    private Object data;

    public ResultDto() {
    }


    public ResultDto(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static ResultDto success() {
        ResultDto result = new ResultDto();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }


    public static ResultDto success(Object data) {
        ResultDto result = new ResultDto();
        result.setData(data);
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }


    public static ResultDto failure(ResultCode resultCode) {
        ResultDto result = new ResultDto();
        result.setResultCode(resultCode);
        return result;
    }


    public static ResultDto failure(ResultCode resultCode, Object data) {
        ResultDto result = new ResultDto();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }


    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }


    public Integer getCode() {
        return code;
    }


    public void setCode(Integer code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Object getData() {
        return data;
    }


    public void setData(Object data) {
        this.data = data;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
