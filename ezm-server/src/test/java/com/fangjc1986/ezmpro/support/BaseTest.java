package com.fangjc1986.ezmpro.support;

import com.fangjc1986.support.entity.R;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class BaseTest {

    public MockMvc mockMvc;

    @Autowired
    public WebApplicationContext webApplicationContext;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 将 mvcResult 转换成 R
     *
     * @param mvcResult
     * @param <T>
     * @return
     * @throws UnsupportedEncodingException
     */
    public <T> R<T> getR(MvcResult mvcResult) throws UnsupportedEncodingException {
        return R.fromJSON(mvcResult.getResponse().getContentAsString());
    }

    /**
     * 打印 mvcResult
     * @param result
     * @throws UnsupportedEncodingException
     */
    public void printlnMvcResult(MvcResult result) throws UnsupportedEncodingException {
        System.out.println(result.getResponse().getContentAsString());
    }
}
