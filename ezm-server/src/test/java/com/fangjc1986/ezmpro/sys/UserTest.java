package com.fangjc1986.ezmpro.sys;

import com.alibaba.fastjson.JSONObject;
import com.fangjc1986.ezmpro.support.BaseTest;
import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.util.CommonUtil;
import com.fangjc1986.support.util.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest extends BaseTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void list() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get(
                "/sys/user/list"
            ).header("token", "dssddfsfdf")
        ).andReturn();
        R<User> res = getR(mvcResult);
        System.out.println(mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("用户查询失败!", 200, (int) res.getCode());
    }

    @Test
    public void saveUser() throws Exception {
        Map<String, String> user = new HashMap<>();
        user.put("userName", CommonUtil.getStringRandom(6));
        user.put("birthday", "2000-11-11 12:00:00");
        System.out.println(JSONObject.toJSONString(user));
        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/sys/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user))
        ).andReturn();
        R<User> res = getR(mvcResult);
        printlnMvcResult(mvcResult);
        Assert.assertEquals("新增用户失败！", 200, (int) res.getCode());
    }

    @Test
    public void redisTest(){
        redisUtil.get("123");
    }

  
    //@Test
    //public void testPost() throws Exception {
    //    Map<String, Object> form = new HashMap<>();
    //    form.put("id", 1111);
    //    form.put("pid", 222);
    //    form.put("name", "范德萨范德萨范德萨");
    //    MvcResult mvcResult = mockMvc.perform(
    //        MockMvcRequestBuilders.post("/sys/user/testPost")
    //            .contentType(MediaType.APPLICATION_JSON)
    //            .content(JSONObject.toJSONString(form))
    //    )
    //        .andExpect(status().isOk())
    //        .andReturn();
    //    R<Auth> res = getR(mvcResult);
    //    Assert.assertEquals("水电费is东方闪电", 100, (int) res.getCode());
    //}

}
