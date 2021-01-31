package com.taobao.TestCases;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.taobao.CommonFunction.TaoBaoOperations;
import com.taobao.CommonFunction.DataReader;

import java.util.Map;

public class TBSearchAppiumTest {

    public TaoBaoOperations tbopr;

    @BeforeTest
    public void setUp() throws Exception {
        //测试用例准备操作
        tbopr=new TaoBaoOperations();
    }

    @Test(dataProvider = "TianMaoSearch", dataProviderClass = DataReader.class)
    public void testSearchAppium(Map<?, ?> param) throws Exception
    {
        Thread.sleep(5000);

        //允许App授权
        tbopr.AllowAllRequest();
        //搜索appium
        String Searkey = (String) param.get("searkey");
        System.out.println("搜索关键字："+Searkey);
        tbopr.TBSearch(Searkey);
        //获取搜索结果
        //String searchres=tbopr.getTextFromPage((String) param.get("searmethod"),(String) param.get("searvalue"));
        //处理包含广告的情况
        String searchres=tbopr.getTextFromPageIndex((String) param.get("searmethod"),(String) param.get("searvalue"),2);
        searchres=searchres.toLowerCase();
        System.out.println("搜索结果为：" + searchres);

        if (!searchres.isEmpty()) {
            //判断搜索结果是否包含关键字
            Assert.assertTrue(searchres.contains(Searkey+"111"));
            System.out.println("测试淘宝搜索功能，搜索关键字为"+Searkey+"-------passed!");
        } else {
            System.out.println("测试淘宝搜索功能，搜索关键字为"+Searkey+"-------failed!,搜索结果为：" + searchres);
            Assert.assertEquals(1, 0);
        }
    }

    @AfterTest
    public void tearDown()
    {
        //测试执行完的操作
        tbopr.quiteTaoBaoApp();
    }
}
