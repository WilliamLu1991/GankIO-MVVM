package com.williamlu.toolslib;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: WilliamLu
 * @Date: 2019-05-09
 * @Description:
 */
public class UrlCutUtil {
    /**
     * 在指定url后追加参数
     *
     * @param url
     * @param data 参数集合 key = value
     * @return
     */
    private static String appendUrl(String url, Map<String, Object> data) {
        String newUrl = url;
        StringBuffer param = new StringBuffer();
        for (String key : data.keySet()) {
            param.append(key + "=" + data.get(key).toString() + "&");
        }
        String paramStr = param.toString();
        paramStr = paramStr.substring(0, paramStr.length() - 1);
        if (newUrl.indexOf("?") >= 0) {
            newUrl += "&" + paramStr;
        } else {
            newUrl += "?" + paramStr;
        }
        return newUrl;
    }

    /**
     * 获取指定url中的某个参数
     *
     * @param url
     * @param name
     * @return
     */
    public static String getParamByUrl(String url, String name) {
        url += "&";
        String pattern = "(\\?|&){1}#{0,1}" + name + "=[a-zA-Z0-9]*(&{1})";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(url);
        if (m.find()) {
            System.out.println(m.group(0));
            return m.group(0).split("=")[1].replace("&", "");
        } else {
            return null;
        }
    }

    /**
     * 解析出url对应的方法
     * 如：eshopAction://active?titile=1&url=www.baidu.com  获取到 active
     */
    public static String getMethodByUrl(String url) {
        try {
            if (url.contains("//")) {
                String[] urlSplit = url.split("//");
                if (urlSplit[1].contains("?")) {
                    String[] methodSplit = urlSplit[1].split("[?]");
                    return methodSplit[0];
                } else {
                    return urlSplit[1];
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     * @author lzf
     */
    public static Map<String, String> urlSplit(String URL) {
        try {
            Map<String, String> mapRequest = new HashMap<String, String>();
            String[] arrSplit = null;
            String strUrlParam = URL;
            if (strUrlParam == null) {
                return mapRequest;
            }
            //获取url路径
            String[] urlRouteArray = strUrlParam.split("[?]");
            String urlRoute = "";
            if (urlRouteArray.length >= 1) {
                urlRoute = urlRouteArray[0];
            }
            //获取请求参数
            if (urlRouteArray.length > 1) {
                strUrlParam = urlRouteArray[1];
                arrSplit = strUrlParam.split("[&]");
                for (String strSplit : arrSplit) {
                    String[] arrSplitEqual = null;
                    arrSplitEqual = strSplit.split("[=]");
                    //解析出键值
                    if (arrSplitEqual.length > 1) {
                        //正确解析
                        mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
                    } else {
                        if (arrSplitEqual[0] != "") {
                            //只有参数没有值，不加入
                            mapRequest.put(arrSplitEqual[0], "");
                        }
                    }
                }
            }
            return mapRequest;

        } catch (Exception e) {
            return null;
        }

    }
}
