package com.miller.seckill.enums;

/**
 * Created by miller on 2018/9/1
 */
public class EnumUtil {

    public static <T extends BaseResult> T getByCode(int code, Class<T> enumClass){
        for (T each: enumClass.getEnumConstants()){
            if(code == each.getCode()){
                return each;
            }
        }
        return null;
    }

    public static <T extends BaseResult> String getMsg(int code, Class<T> enumClass){
        T byCode = getByCode(code, enumClass);
        return byCode.getMsg();
    }

}
