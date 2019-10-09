package com.mybatis.plugin;

import com.mybatis.plugin.dao.EmployeeMapper;
import com.mybatis.plugin.po.Employee;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnMybatisPluginApplicationTests {

	@Test
	public void contextLoads() {
	}

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		File file = ResourceUtils.getFile("classpath:mybatis/mybatis-config.xml");
		InputStream inputStream = new FileInputStream(file);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}


	@Test
	public void  test01() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		SqlSession sqlSession = factory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmployeeById(2);
			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}


	/**
	 * 插件编写
	 * 1、编写Interceptor的实现类
	 * 2、使用@Intercepts注解完成插件签名
	 * 3、将写好的插件注册到全局配置文件中
	 *     也就是在mybatis-config.xml中去配置标签
	 *
	 */
	@Test
	public void testPlugin(){

	}
}
