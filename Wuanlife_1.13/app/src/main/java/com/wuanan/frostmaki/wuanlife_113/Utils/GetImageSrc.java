package com.wuanan.frostmaki.wuanlife_113.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Frostmaki on 2016/10/11.
 */
public class GetImageSrc {
    /**
     * 获取img标签中的src值
     * @param content
     * @return
     */
    public static List<String> getImgSrc(String content){

        List<String> list = new ArrayList<String>();
        //目前img标签标示有3种表达式
        //<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
        //开始匹配content中的<img />标签
        //String s ="<p>Image 1:< img width=\"199\"src=\"_image/12/label\"alt=\"\"/> Image 2: < img width=\"199\"src=\"_image/12/labe2\"alt=\"\"/>< img width=\"199\"src=\"_image/12/labe3\"alt=\"\"/></p >";
        String s=content;
        //<string name="patterno">img[^>]+srcs*=s*['"]([^'"]+)['"][^>]*</string>
        Pattern p = Pattern.compile("(src|SRC)=(\\\"|\\')(.*?)(\\\"|\\')");//<img[^<>]*src=['"]([0-9A-Za-z.\/]*)['"].(.*?)>");
        Matcher m = p.matcher(s);
        //System.out.println(m.find());
        System.out.println(m.groupCount());
        while(m.find()){
            //System.out.println(m.group()+"-------------↓↓↓↓↓↓");
            //System.out.println(m.group(3));
            list.add(m.group(3));
        }

        return list;
    }

    /**
     * 定义script的正则表达式
     */
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";

    public static String delHTMLTag(String htmlStr) {
// 过滤script标签
        Pattern p_script = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
// 过滤style标签
        Pattern p_style = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
// 过滤html标签
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
// 过滤空格回车标签
        Pattern p_space = Pattern.compile(REGEX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");
        return htmlStr.trim(); // 返回文本字符串
    }
}
