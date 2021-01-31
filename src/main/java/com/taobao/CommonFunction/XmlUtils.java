package com.taobao.CommonFunction;
/**
 * XML文件操作类
 * @author:孤狼
 * @date:2020-03-29
 *
 */
import java.util.*;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.testng.Assert;
import com.taobao.CommonFunction.BaseComFunction;

public class XmlUtils {
    public static List getXmlComent(String fname) {
        /*
        功能：将xml文件中的内容读取到List中
        @param fname 数据文件名
        @return 存储数据文件内容的List
         */
        //获取xml文件完全路径
        String path = BaseComFunction.getAbsolutiohnPath(fname);
        List contList = new ArrayList();
        //dom4j中读取xml文件的方法
        SAXReader saxR = new SAXReader();
        try {
            Document doc = saxR.read(path);
            //存放顶结点
            Element eleroot = doc.getRootElement();
            //parMap，存放顶结点下一级结点
            Map parMap = new HashMap();
            Map sonMap = new HashMap();
            for (Iterator i = eleroot.elementIterator(); i.hasNext(); ) {
                //parMap中存放的结点的子结点
                Element elepar = (Element) i.next();
                for (Iterator j = elepar.elementIterator(); j.hasNext(); ) {
                    Element eleSon = (Element) j.next();
                    sonMap.put(eleSon.getName(), eleSon.getText());
                }

                parMap.put(elepar.getName(), sonMap);
                contList.add(parMap);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return contList;
    }

    public static Object[][] getTestData(String filename, String nodename) {
        /**
         * 将xml文件中的内容转化成Object
         * @param filename 测试数据文件
         * @param nodename 数据节点名
         * @return Object 类型数据
         *
         */
        Object[][] obj;
        List sonList = new ArrayList();
        List parList = getXmlComent(filename);
        for (int i = 0; i < parList.size(); i++) {
            Map map = (Map) parList.get(i);
            if (map.containsKey(nodename)) {
                Map<String, String> submap = (Map<String, String>) map.get(nodename);
                sonList.add(submap);
            }
        }
        if (sonList.size() > 0) {
            obj = new Object[sonList.size()][];
            for (int i = 0; i < sonList.size(); i++) {
                obj[i] = new Object[]{sonList.get(i)};
            }
            return obj;
        } else {
            Assert.assertTrue(sonList.size() != 0, sonList + "为空，找不到属性值：" + nodename);
            return null;
        }
    }

    public static void main(String args[]) {
        System.out.println("*************************");
        Object[][] xmlcont = getTestData("TBSearchAppiumTest", "testSearchAppium");
        if (xmlcont != null) {
            System.out.println("Object数据长度："+xmlcont[0].length);
            for (int i=0;i<xmlcont.length;i++)
            {
                System.out.println(Arrays.toString(xmlcont[i]));
                for(int j=0;j<xmlcont[i].length;j++)
                {
                    System.out.println(xmlcont[i][j]);
                   Map<?,?> readcont= (Map<?, ?>) xmlcont[i][j];
                   System.out.println("searkey:"+readcont.get("searkey"));
                   System.out.println("searmethod:"+readcont.get("searmethod"));
                   System.out.println("searvalue:"+readcont.get("searvalue"));
                }
            }

        } else {
            System.out.println("找不到数据！");
        }

    }
}