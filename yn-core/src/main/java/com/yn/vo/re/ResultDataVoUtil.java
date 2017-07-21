package com.yn.vo.re;

import com.yn.utils.ResultData;

/**
 * 数据返回
 * Created by hy-03 on 2017/7/19.
 */
public class ResultDataVoUtil {

    public static ResultData<Object> success(Object object) {
        ResultData resultData = new ResultData();
        resultData.setCode(200);
        resultData.setMsg("成功");
        resultData.setSuccess(true);
        resultData.setData(object);
        return resultData;
    }

    public static ResultData<Object> success() {
        return success(null);
    }

    public static ResultData<Object> error(Integer code, String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(code);
        resultData.setMsg(msg);
        resultData.setSuccess(false);
        resultData.setData(null);
        return resultData;
    }
}
