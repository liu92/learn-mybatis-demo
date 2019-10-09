package com.mybatis.mbg;

import com.mybatis.mbg.dao.EmployeeMapper;
import com.mybatis.mbg.po.Employee;
import com.mybatis.mbg.po.EmployeeExample;
import com.mybatis.mbg.po.EmployeeExample.Criteria;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnMybatisMbgApplicationTests {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		File file = ResourceUtils.getFile("classpath:mybatis/mybatis-config.xml");

//		File inputFile = ResourceUtils.getFile("classpath:mybatis/mybatis-config.xml");
		InputStream inputStream = new FileInputStream(file);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}


	@Test
	public void contextLoads() {
	}


	@Test
	public void testGeneratorMbg() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
		List<String> warnings = new ArrayList<>();
		boolean overwrite = true;
//		File configFile = new File("classpath:mbg.xml");
		File file = ResourceUtils.getFile("classpath:mbg.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(file);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}


	@Test
	public void testSimple() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//			Employee employee = new Employee(3, "哈哈哈","567876@qq.com", 1, 5);

			List<Employee> list = mapper.selectByExample(null);
			for (Employee employee : list) {
				System.out.println(employee);
			}
//			Employee employeeByMap = mapper.selectByExample();
//			// 代理对象
			System.out.println(mapper.getClass());
//			// 如果这里再次调用查询,会去查询数据库吗？这里不会再次查询，因为第二次查询的数据和第一次的一样，所以
//			// 第二次查询是从缓存中取数据
//			Employee employee1 = mapper.getEmployeeById(1);
//			System.out.println(employee1);
//			System.out.println(employeeByMap==employee1);
		} finally {
			sqlSession.close();
		}
	}



	@Test
	public void testMybatis3() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		try {
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			//xxxExample就是封装查询条件的
			//1、查询所有
			//List<Employee> emps = mapper.selectByExample(null);
			//2、查询员工名字中有 哈 字的，和员工性别是0的
			//封装员工查询条件的example
			EmployeeExample example = new EmployeeExample();
			//创建一个Criteria，这个Criteria就是拼装查询条件
			//select id, last_name, email, gender, d_id from employee
			// WHERE ( last_name like ? and gender = ? ) or email like "%e%"
			Criteria criteria = example.createCriteria();
			criteria.andLastNameLike("%哈%");
			criteria.andGenderEqualTo(0);

			Criteria criteria2 =  example.createCriteria();
			criteria2.andEmailLike("%e%");
            // 如果要在拼装一个email 那么可以使用 两个Criteria 来拼接，然后使用example来拼接
			// 拼接后的 sql:
			// select id, last_name, email, gender, d_id from employee
			// WHERE ( last_name like ? and gender = ? ) or( email like ? )
			example.or(criteria2);

			List<Employee> list = mapper.selectByExample(example);
			for (Employee employee : list) {
				System.out.println(employee);
			}
			// 代理对象
			System.out.println(mapper.getClass());
		} finally {
			sqlSession.close();
		}
	}


























}
