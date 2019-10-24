package dao;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;

public class UserDao {
    private static HashMap<String,String> userMap = new HashMap<>();

    static {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read("FTPServer/src/user.xml");
            Element root = document.getRootElement();
            List list = root.elements();

            //置入Map
            for (Object aList : list) {
                Element element = (Element) aList;
                userMap.put(element.elementText("name"), element.elementText("pass"));
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }


    public static boolean userRight(String user){
        return userMap.containsKey(user);
    }

    public static boolean pwdRight(String user,String pwd){
        return userMap.get(user).equals(pwd);
    }

}
