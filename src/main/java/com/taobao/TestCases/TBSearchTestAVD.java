package com.taobao.TestCases;
/**
 * 测试淘宝搜素Appium，在模拟器下执行测试用例
 */

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import java.io.File;
import java.net.URL;
import java.util.List;

public class TBSearchTestAVD {

    //定义全局driver
    public AppiumDriver driver;

    @BeforeTest
    public void setUp() throws Exception {
        //初始化测试用例相关操作
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "127.0.0.1:62001");
        capabilities.setCapability("platformVersion", "5.1.1");
        File directory = new File("");//参数为空
        String appPath = directory.getAbsolutePath();
        capabilities.setCapability("app", appPath + "/src/main/resources/app/shoujitaobao.apk");
        capabilities.setCapability("appPackage", "com.taobao.taobao");
        capabilities.setCapability("appActivity", "com.taobao.tao.welcome.Welcome");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void testSearchAppium() throws Exception {
        /**
         * 测试搜索Appium
         */
        //1，允许应用授权
        Thread.sleep(5000);
        //单击同意按钮
        driver.findElementById("com.taobao.taobao:id/uik_mdButtonDefaultPositive").click();
        Thread.sleep(3000);
        //再次单击同意按钮
        driver.findElementById("com.taobao.taobao:id/provision_positive_button").click();
        Thread.sleep(15000);

        //2,单击搜索按钮
        driver.findElementByXPath("(//android.view.View[@content-desc=\"搜索\"])[1]").click();
        Thread.sleep(10000);
        //输入关键字
        driver.findElementById("com.taobao.taobao:id/searchEdit").sendKeys("appium");
        //单击搜索
        driver.findElementById("com.taobao.taobao:id/searchbtn").click();
        Thread.sleep(15000);

        //检测搜索结果,去掉广告
        List<WebElement> reselems=driver.findElementsById("com.taobao.taobao:id/title");
        //String serchres = driver.findElementById("com.taobao.taobao:id/title").getText();
        String serchres =reselems.get(2).getText();
        serchres = serchres.toLowerCase();
        System.out.println("搜索结果为：" + serchres);

        if (!serchres.isEmpty()) {
            //判断搜索结果是否包含关键字
            Assert.assertTrue(serchres.contains("appium"));
            System.out.println("测试淘宝搜索功能，搜索关键字为appium-------passed!");
        } else {
            System.out.println("测试淘宝搜索功能，搜索关键字为appium-------failed!,搜索结果为：" + serchres);
            Assert.assertEquals(1, 0);
        }

    }

    @AfterTest
    public void tearDown() {
        //测试清理操作
        driver.quit();
    }
}
