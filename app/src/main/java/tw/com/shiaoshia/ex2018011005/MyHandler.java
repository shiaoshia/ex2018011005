package tw.com.shiaoshia.ex2018011005;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by USER-NB on 2018/1/10.
 */

public class MyHandler extends DefaultHandler {

    boolean isItem = false; //取出Item資料
    boolean isTitle = false; //取出Title資料
    boolean isLink = false; //取出連結位址
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> links = new ArrayList<>();
    StringBuilder linkSB = new StringBuilder();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //Log.d("NET",qName);
        if (qName.equals("item")) {
            isItem = true;     //遇到<item>開
        }
        if (qName.equals("title")) {
            isTitle = true;   //遇到<title>開關開
        }
        if (qName.equals("link")) {
            isLink = true;
        }


    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("item")) {
            isItem = false;
        }
        if (qName.equals("title")) {
            isTitle = false;    //遇到<title>開關關
        }
        if (qName.equals("link")) {
            isLink =false;
            if (isItem) {
                Log.d("NET", linkSB.toString());  //將連結秀出來
                links.add(linkSB.toString());
                linkSB = new StringBuilder();   //清空linkSB
            }
        }


    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if(isTitle && isItem) {
            Log.d("NET", new String(ch,start,length));  //將文字秀出來
            titles.add(new String(ch,start,length));
        }
        if(isLink && isItem) {
            linkSB.append(new String(ch,start,length)); //將link字串組合起來
        }
    }
}
