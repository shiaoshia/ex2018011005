package tw.com.shiaoshia.ex2018011005;

import android.util.Log;
import android.util.Patterns;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by USER-NB on 2018/1/10.
 */

public class MyHandler extends DefaultHandler {

    boolean isItem = false; //取出Item資料
    boolean isTitle = false; //取出Title資料
    boolean isLink = false; //取出連結位址
    boolean isDescription = false; //取出簡介
    //ArrayList<String> titles = new ArrayList<>();
    //ArrayList<String> links = new ArrayList<>();
    StringBuilder linkSB = new StringBuilder();
    StringBuilder descSB = new StringBuilder();
    //將資料放在Mobile01NewsItem的ArrayList裡;newsItems.title、newsItems.item
    public ArrayList<Mobile01NewsItem> newsItems = new ArrayList<>();
    Mobile01NewsItem item;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch(qName) {
            case "title":
                isTitle = true;
                break;
            case "item":
                isItem = true;
                item = new Mobile01NewsItem();
                break;
            case "link":
                isLink = true;
                break;
            case "description":
                isDescription = true;
                break;
        }
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch(qName) {
            case "title":
                isTitle = false;
                break;
            case "item":
                isItem = false;
                newsItems.add(item);
                break;
            case "link":
                isLink = false;
                if (isItem) {
                    Log.d("NET", linkSB.toString());  //將連結秀出來
                    item.link = linkSB.toString();
                    linkSB = new StringBuilder();   //清空linkSB
                }
                break;
            case "description":
                isDescription = false;
                if (isItem) {

                    String str = descSB.toString();
                    //取出img的位址
                    Pattern pattern = Pattern.compile("https.*jpg");
                    Matcher m = pattern.matcher(str);
                    String imgurl = "";
                    if (m.find()) {
                        imgurl = m.group(0);
                    }
                    str = str.replaceAll("<img.*/>","");    //利用正規表示式將<img />拿掉
                    Log.d("NET", str); //秀出Description
                    item.description = str;
                    Log.d("NET", imgurl); //秀出images
                    item.imgurl = imgurl;
                    descSB = new StringBuilder(); //清空descSB
                }
                break;
        }
        super.endElement(uri, localName, qName);
    }

    //用if的方式
//    @Override
//    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        super.startElement(uri, localName, qName, attributes);
//        //Log.d("NET",qName);
//        if (qName.equals("title")) {
//            isTitle = true;   //遇到<title>開關開
//        }
//        if (qName.equals("item")) {
//            isItem = true;     //遇到<item>開
//            item = new Mobile01NewsItem();
//        }
//        if (qName.equals("link")) {
//            isLink = true;
//        }
//    }

//    @Override
//    public void endElement(String uri, String localName, String qName) throws SAXException {
//        super.endElement(uri, localName, qName);
//        if (qName.equals("title")) {
//            isTitle = false;    //遇到<title>開關關
//        }
//        if (qName.equals("item")) {
//            isItem = false;
//            newsItems.add(item);
//        }
//        if (qName.equals("link")) {
//            isLink =false;
//            if (isItem) {
//                Log.d("NET", linkSB.toString());  //將連結秀出來
//                //links.add(linkSB.toString());
//                item.link = linkSB.toString();
//                linkSB = new StringBuilder();   //清空linkSB
//            }
//        }
//    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if(isTitle && isItem) {
            Log.d("NET", new String(ch,start,length));  //將文字秀出來
            //titles.add(new String(ch,start,length));
            item.title = new String(ch,start,length);
        }
        if(isLink && isItem) {
            linkSB.append(new String(ch,start,length)); //將link字串組合起來
        }
        if(isDescription && isItem) {
            descSB.append(new String(ch,start,length));
        }
    }
}
