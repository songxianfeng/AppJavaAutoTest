package com.taobao.CommonFunction;
/**
 * App基本操作函数类
 * @author :孤狼
 * @date:2020-03-29
 */

import java.io.File;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;
import org.apache.log4j.Logger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class BaseComFunction {
    //记录错误日志
    public static Logger Log = Logger.getLogger(BaseComFunction.class);
    public AppiumDriver driver;


    public BaseComFunction() throws Exception {
        /**
         * 构造函数，设置appium手机链接
         */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", getPhoneDevice());
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("app", getAppPath());
        capabilities.setCapability("appPackage", "com.taobao.taobao");
        capabilities.setCapability("appActivity", "com.taobao.tao.welcome.Welcome");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }

    public void quiteApp()
    {
        /**
         * 退出App
         */
        driver.quit();
    }

    public static String getAppPath() {
        /*
          获取要测试的app的位置
         */
        File directory = new File("");//参数为空
        String appPath = directory.getAbsolutePath();
        appPath = appPath + "/src/main/resources/app/shoujitaobao.apk";
        return appPath;

    }

    public static String getAbsolutiohnPath(String filename) {
        /*
        获取文件的获取路径
        @param filename 要获取路径的文件名
        @return 数据文件的绝对路径
        */
        File directory = new File("");//参数为空
        String courseFile = directory.getAbsolutePath();
        courseFile = courseFile + "/src/main/java/com/taobao/TestData/" + filename + ".xml";
        return courseFile;

    }

    public static String getPhoneDevice() {
        /*
          获取手机型号
         */
        Process process = null;
        List<String> processList = new ArrayList<String>();
        try {
            String command = "adb devices -l";
            process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String devinfo = processList.get(1);
        if (devinfo.length() == 0) {
            Log.error("获取连接手机失败，请运行adb devices -l查看手机连接情况！");
            return "";
        } else {
            String[] dinfo = devinfo.split(" ");
            return dinfo[0];
        }

    }

    public WebElement getElement(String fmethod, String fvalue) {
        /**
         * 获取元素
         * @param fmethod:定位方法，Xpath,Id等
         * @param fvalue:定位方法对应的值
         */
        WebElement felem = null;
        if (fmethod.equals("Xpath")) {
            felem = driver.findElement(By.xpath(fvalue));
        }
        if (fmethod.equals("ID")) {
            felem = driver.findElement(By.id(fvalue));
        }
        return felem;
    }


    public List<WebElement> getElements(String fmethod, String fvalue) {
        /**
         * 获取多个同种属性的元素
         * @param fmethod:定位方法，Xpath,Id等
         * @param fvalue:定位方法对应的值
         */
        List<WebElement> felems = null;
        if (fmethod.equals("Xpath")) {
            felems = driver.findElements(By.xpath(fvalue));
        }
        if (fmethod.equals("ID")) {
            felems = driver.findElements(By.id(fvalue));
        }
        return felems;
    }


    public boolean isExistElement(String fmethod, String fvalue) {
        /**
         * 判断元素是否存在
         * @param fmethod:定位方法，Xpath,Id等
         * @param fvalue:定位方法对应的值
         */
        try {
            this.getElement(fmethod, fvalue);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void ClickElem(String fmethod, String fvalue) {
        /**
         * 单击元素
         * @param fmethod:定位方法，Xpath,Id等
         * @param fvalue:定位方法对应的值
         */
        if (this.isExistElement(fmethod, fvalue)) {
            this.getElement(fmethod, fvalue).click();
        } else {
            System.out.println("要单击的元素不存在！");

        }
    }

    public void InputContent(String fmethod, String fvalue, String content) {
        /**
         * 输入内容
         * @param fmethod:定位方法，Xpath,Id等
         * @param fvalue:定位方法对应的值
         * @param content:要输入的元素内容
         */
        if (this.isExistElement(fmethod, fvalue)) {
            this.getElement(fmethod, fvalue).sendKeys(content);
        } else {
            System.out.println("输入框不存在！");
        }
    }

    public String getTextFromElem(String fmethod, String fvalue) {
        /**
         * 获取元素内容
         * @param fmethod:定位方法，Xpath,Id等
         * @param fvalue:定位方法对应的值
         */
        if (this.isExistElement(fmethod, fvalue)) {
            return this.getElement(fmethod, fvalue).getText();
        } else {
            System.out.println("要获取内容的元素不存在！");
            return "";
        }

    }


    public String getTextFromElemIndex(String fmethod, String fvalue,int index) {
        /**
         * 获取相同属性的元素第index的内容
         * @param fmethod:定位方法，Xpath,Id等
         * @param fvalue:定位方法对应的值
         * @para index ：要获取的第几个元素的内容
         */
        String content="";
       if(index>0)
       {
           List<WebElement> selems=this.getElements(fmethod,fvalue);
           content=selems.get(index).getText();
       }
       else
       {
           System.out.println("不能获取下标为负数的元素内容！");
           Log.error("不能获取下标为负数的元素内容！");
       }
       return content;
    }



}
