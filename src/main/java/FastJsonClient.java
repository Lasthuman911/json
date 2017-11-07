import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * fastJson 的最常用的使用方式
 * @author wzm
 */
public class FastJsonClient {
    public static void main(String[] args) throws ParseException {
        User user = new User();
        List<Address> addressList = new ArrayList<Address>();
        Address address = new Address();
        address.setCity("上海");
        address.setCountry("中国");
        Address address2 = new Address();
        address2.setCountry("美国");
//        address2.setCity("newYork");
        address2.setCity(null);
        addressList.add(address);
        addressList.add(address2);
        user.setName("wzm");
        user.setAge(20);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        user.setBirthDay(dateformat.parse("1996-10-01"));
        user.setEmail("haha@163.com");
        user.setAddress(addressList);
        //序列化bean->str
        String bean2jsonStr = JSON.toJSONString(user,true);
        System.out.println(bean2jsonStr);

        //反序列化str->bean
        User str2Bean = JSON.parseObject(bean2jsonStr, User.class);
        System.out.println(str2Bean);

        //str -> map
        Map<String, Object> map = JSON.parseObject(bean2jsonStr, Map.class);
        JSONArray jsonArray = (JSONArray) map.get("address");
        for (int i=0;i<jsonArray.size();i++){
            String country = jsonArray.getJSONObject(i).getString("country");
            String city = jsonArray.getJSONObject(i).getString("city");
            System.out.println("country = " + country + ";city = " + city);
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        System.out.println(map);
    }
}
