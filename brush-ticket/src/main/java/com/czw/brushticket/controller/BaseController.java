package com.czw.brushticket.controller;

import com.czw.brushticket.Enum.ReturnInfoEnum;
import com.czw.brushticket.utils.NResult;
import com.czw.brushticket.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: czw
 * @Date: 2018-12-03 17:40
 **/
public class BaseController {
    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected Result getResult(Object data) {
        NResult object = new NResult();
        object.setCode(ReturnInfoEnum.Success.getCode());
        object.setMessage(ReturnInfoEnum.Success.getDesc());
        object.setData(data);
        return new Result(true, object);
    }
}
