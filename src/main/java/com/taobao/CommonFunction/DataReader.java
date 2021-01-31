package com.taobao.CommonFunction;
/**
 * TestNG DataProvider
 * @author :孤狼
 * @date:2020-03-29
 */
import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;
import com.taobao.CommonFunction.XmlUtils;

public class DataReader {

    @DataProvider(name = "TianMaoSearch")
    public static Object[][] TianMaoSearch(Method method) {
        //读取xml内容
        return XmlUtils.getTestData("TBSearchAppiumTest", method.getName());

    }

}