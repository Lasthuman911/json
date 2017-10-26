import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 本例最常用的对象绑定模式处理JSON-Data Binding
 * 另外两种模式：
 * -----------Streaming API：是效率最高的处理方式(开销低、读写速度快，但程序编写复杂度高)
 * -----------Tree Model：是最灵活的处理方式
 */
public class JacksonClient {
    public static void main(String[] args) throws ParseException, IOException {
        User user = new User();
        List<Address> addressList = new ArrayList<Address>();
        Address address = new Address();
        address.setCity("上海");
        address.setCountry("中国");
        Address address2 = new Address();
        address2.setCountry("美国");
        address2.setCity("newYork");
        addressList.add(address);
        addressList.add(address2);
        user.setName("wzm");
        user.setAge(20);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        user.setBirthDay(dateformat.parse("1996-10-01"));
        user.setEmail("haha@163.com");
        user.setAddress(addressList);

        /*
          ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
          ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
          writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
          writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
          writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
          writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
         */
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("----- java对象转换为JSON-----");
        String json = mapper.writeValueAsString(user);
        mapper.writeValue(new File("d://user.json"), user);
        System.out.println(json);

        System.out.println("\n----- java集合转换为JSON-----");
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        userList.add(user);
        String jsonList = mapper.writeValueAsString(userList);
        System.out.println(jsonList);

        System.out.println("\n----- JSON转换为java对象-----");
        User backUser = mapper.readValue(json, User.class);
        System.out.println(backUser);

//        JSON转换为map， 需要注意的是这里的Map实际为一个LikedHashMap，即链式哈希表，可以按照读入顺序遍历
        jsonToLinkedHashMap(mapper, json);

        System.out.println("\n----- JSON转换为List-----");
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, User.class);
        List<User> me = mapper.readValue(jsonList, javaType);
        System.out.println(me);
    }

    private static void jsonToLinkedHashMap(ObjectMapper mapper, String json) throws IOException {
        System.out.println("\n ---JSON转换为map---");
        Map<String, Object> map = mapper.readValue(json, Map.class);
//        此方式只能遍历value
      /*  for (Object value : map.values()) {
            System.out.println(value);
        }*/
//        推荐使用此方式遍历map，尤其是容量大时
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
