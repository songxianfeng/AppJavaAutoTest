package com.taobao.CommonFunction;
/**
 * 淘宝App基本操作函数类
 * @author :孤狼
 * @date:2020-03-29
 */

import com.taobao.CommonFunction.BaseComFunction;

public class TaoBaoOperations {

    public BaseComFunction comfun;

    public TaoBaoOperations() throws Exception {
        //实例化BaseComFunction

        comfun=new BaseComFunction();
    }

    public void AllowAllRequest() throws InterruptedException {
        /**
         * 允许所有的授权
         */
        Thread.sleep(3000);
        //3,单击同意按钮
        comfun.ClickElem("ID", "com.taobao.taobao:id/provision_positive_button");
        Thread.sleep(3000);
        //单击好的按钮
        comfun.ClickElem("ID", "com.taobao.taobao:id/txtConfirm");
        Thread.sleep(3000);
        //单击两次允许
        comfun.ClickElem("ID", "com.android.packageinstaller:id/permission_allow_button");
        Thread.sleep(3000);
        comfun.ClickElem("ID", "com.android.packageinstaller:id/permission_allow_button");
        Thread.sleep(3000);
    }

    public void TBSearch(String skey) throws InterruptedException {
        /**
         * 淘宝搜索操作
         * @param skey:要搜索的关键字
         */
        //（2）点击顶部导航中的搜索按钮
        comfun.ClickElem("Xpath", "(//android.view.View[@content-desc=\"搜索\"])[1]");
        Thread.sleep(3000);
        //(3)在搜索框架中输入要搜索的内容：“appium”
        comfun.InputContent("ID", "com.taobao.taobao:id/searchEdit", skey);
        Thread.sleep(3000);
        //单击搜索按钮
        comfun.ClickElem("ID", "com.taobao.taobao:id/searchbtn");
        Thread.sleep(10000);
    }

    public String getTextFromPage(String locmethods,String location)
    {
        /**
        获取淘宝app上元素内容
        @param locmethods:元素定位方法
        @param location:无素定位值
        @return 获取到的元素内容
        */
        String tbtext=comfun.getTextFromElem(locmethods, location);
        return tbtext;
    }


    public String getTextFromPageIndex(String locmethods,String location,int index)
    {
        /**
         获取淘宝app上同类元素中的第index的内容
         @param locmethods:元素定位方法
         @param location:无素定位值
         @param index :第几个元素的内容
         @return 获取到的元素内容
         */
        String tbtext=comfun.getTextFromElemIndex(locmethods,location,index);
        return tbtext;
    }
    public void quiteTaoBaoApp()
    {
        /**
         * 退出淘宝
         */
        comfun.quiteApp();
    }



}
