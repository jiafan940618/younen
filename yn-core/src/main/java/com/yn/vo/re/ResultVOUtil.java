package com.yn.vo.re;

import java.util.List;
import java.util.Map;

import com.yn.enums.ResultEnum;
import com.yn.model.Station;
import com.yn.utils.ResultData;

/**
 * 数据返回
 * Created by hy-03 on 2017/7/19.
 */
public class ResultVOUtil {

    public static ResultData<Object> success(Object object) {
        ResultData resultData = new ResultData();
        resultData.setCode(200);
        resultData.setMsg("成功");
        resultData.setSuccess(true);
        resultData.setData(object);
        return resultData;
    }
    
    public static  ResultData<Object> newhsuccess(Object object,List<Station> list,Map<String,String> map) {
    	ResultData resultData = new ResultData();
        
        resultData.setCode(200);
        resultData.setMsg("成功");
        resultData.setSuccess(true);
        resultData.setData(object);
        resultData.setList(list);
        resultData.setMap(map);
        return resultData;
    }

    
    public static <T> ResultData<Object> newsuccess(Object object,List<T> list,List<T> newlist,T pro) {
        ResultData resultData = new ResultData();
        
        resultData.setCode(200);
        resultData.setMsg("成功");
        resultData.setSuccess(true);
        resultData.setData(object);
        resultData.setList(list);
        resultData.setProdu(pro);
        resultData.setNewlist(newlist);
        return resultData;
    }
    
    public static <T> ResultData<Object> newsuccess(Object object,List<T> list) {
        ResultData resultData = new ResultData();
        
        resultData.setCode(200);
        resultData.setMsg("成功");
        resultData.setSuccess(true);
        resultData.setData(object);
        resultData.setList(list);
        return resultData;
    }
    
 /*   public static ResultData<Object> aginsuccess(Object object,List<PriceVo> list,List<Apolegamy> listapo,ProductionDetail production) {
        ResultData resultData = new ResultData();
        resultData.setCode(200);
        resultData.setMsg("成功");
        resultData.setSuccess(true);
        resultData.setData(object);
        resultData.setList(list);
        return resultData;
    }*/
    
    

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

    public static ResultData<Object> error(ResultEnum resultEnum) {
        ResultData resultData = new ResultData();
        resultData.setCode(resultEnum.getCode());
        resultData.setMsg(resultEnum.getMessage());
        resultData.setSuccess(false);
        resultData.setData(null);
        return resultData;
    }
}
