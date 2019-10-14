package cn.itsource.aiqi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class FastJSONTest {

    @Test
    public void test()throws Exception{

        //json字符串转java集合
        String jsonStr = "[{\"id\":1,\"username\":\"xiaoqiu\"},{\"id\":2,\"username\":\"xiaopang\"}]";
        List<User> users = JSONArray.parseArray(jsonStr, User.class);
        System.out.println(users);

    }

}
