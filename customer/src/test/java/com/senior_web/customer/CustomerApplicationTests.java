package com.senior_web.customer;

import com.senior_web.common.domin.User;
import com.senior_web.common.service.UserService;
import com.senior_web.customer.controller.UserController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerApplicationTests {

	@Autowired
	private UserController userController;



    @Before
	public void init(){
    	System.out.println("开始单元测试");
	}
	@After
	public void after(){
    	System.out.println("测试结束");
	}
	@Test
	public void testUserService(){
		Assert.assertSame("用户密码不对","555",userController.getUserById().getPassword());
	}







}
