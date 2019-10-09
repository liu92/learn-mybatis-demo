package com.mybatis.dynamic;

import com.mybatis.dynamic.dao.DepartmentMapper;
import com.mybatis.dynamic.dao.EmployeeDynamicSqlMapper;
import com.mybatis.dynamic.dao.EmployeeMapper;
import com.mybatis.dynamic.po.Department;
import com.mybatis.dynamic.po.Employee;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnMybatisDynamicApplicationTests {

	@Test
	public void contextLoads() {
	}


	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		File file = ResourceUtils.getFile("classpath:mybatis/mybatis-config.xml");

//		File inputFile = ResourceUtils.getFile("classpath:mybatis/mybatis-config.xml");
		InputStream inputStream = new FileInputStream(file);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	/**
	 * 1、根据xml配置文件，创建一个SqlSessionFactory对象
	 * @throws IOException
	 */
	@Test
	public void  testMybatis() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		//2、通过SqlSessionFactory，来获取sqlSession 实例对象，能直接执行已经映射的sql 语句
		SqlSession sqlSession = factory.openSession();

		try {
			Employee employee = sqlSession.selectOne("getEmployeeById", 1);
			System.out.println(employee);
		}finally {
			sqlSession.close();
		}

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


	@Test
	public void  testAddOrUpdate() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();

		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

			Employee employee = new Employee(3, "哈哈哈","567876@qq.com", 1, 5);
			//添加
			mapper.insert(employee);
			// 更新
			//mapper.update(employee);
//			mapper.deleteById(3);


			// 手动提交数据
			sqlSession.commit();
			// 代理对象
			System.out.println(mapper.getClass());
//			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}


	@Test
	public void  testMap() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();

		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

			Map<String, Object> map =new HashMap<>();
			map.put("id",1);
			map.put("lastName","李四");
//			map.put("tableName","employee");

			Employee employeeByMap = mapper.getEmployeeByMap(map);

			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println(employeeByMap);
		} finally {
			sqlSession.close();
		}
	}



	@Test
	public void  test03() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();

		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

			Employee employeeByMap = mapper.getEmployeeByIdAndEmail(1,"5615@qq.com");

			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println(employeeByMap);
		} finally {
			sqlSession.close();
		}
	}


	@Test
	public void  testList() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();

		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

			List<Employee> employeeByMap = mapper.findByLastName("哈哈哈");

			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println(employeeByMap);


			Map<String, Object> employeeByIdReturnMap = mapper.getEmployeeByIdReturnMap(2);

			System.out.println(employeeByIdReturnMap);
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void  test05() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();

		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

			Map<Integer, Employee> employeeByMap = mapper.getEmployeeByLastNameReturnMap("哈哈哈");

			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println(employeeByMap);
			// 结果
//			class com.sun.proxy.$Proxy75
//			{2=Employee [id=2, lastName=哈哈哈, email=454@se.cpm, gender=0, dId=6], 3=Employee [id=3, lastName=哈哈哈, email=567876@qq.com, gender=1, dId=5]}
//
		} finally {
			sqlSession.close();
		}
	}



	@Test
	public void  testEmpAndDepart() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();

		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

//			Employee employee = mapper.getEmployeeAndDepart(2);

			// 代理对象
//			System.out.println(mapper.getClass());
//			System.out.println(employee);
//			System.out.println(employee.getDepartment());
			// 结果
//			class com.sun.proxy.$Proxy75
//			{2=Employee [id=2, lastName=哈哈哈, email=454@se.cpm, gender=0, dId=6], 3=Employee [id=3, lastName=哈哈哈, email=567876@qq.com, gender=1, dId=5]}
//

			Employee employee2 = mapper.getEmployeeByIdStep(2);
			System.out.println(mapper.getClass());
			System.out.println(employee2);

			//开启延迟加载后， 在没有使用部门之前 ，只先去查询员工，在需要用到部门的时候，再去查询部门 ，
			//
			System.out.println(employee2.getDepartment());
		} finally {
			sqlSession.close();
		}
	}


	/**
	 * 鉴别分步查询
	 * @throws IOException
	 */
	@Test
	public void  testDiscriminator() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

			Employee employee2 = mapper.getEmployeeByIdDiscriminatorStep(2);
			System.out.println(mapper.getClass());
			System.out.println(employee2.getLastName());

			//开启延迟加载后， 在没有使用部门之前 ，只先去查询员工，在需要用到部门的时候，再去查询部门 ，
			//
			System.out.println(employee2.getDepartment());
		} finally {
			sqlSession.close();
		}
	}



	@Test
	public void  testDepart() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
			Department department = mapper.getDepartByIdPlus(2);
			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println(department);
			System.out.println(department.getEmployees());
		} finally {
			sqlSession.close();
		}
	}


	@Test
	public void  testDepartStep() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
			Department department = mapper.getDepartByIdStep(1);
			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println(department);
			System.out.println(department.getDepartmentName());
			System.out.println(department.getEmployees());
		} finally {
			sqlSession.close();
		}
	}



	@Test
	public void  testDynamic() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeDynamicSqlMapper mapper = sqlSession.getMapper(EmployeeDynamicSqlMapper.class);
			Employee employee = new Employee(3, "哈哈哈","567876@qq.com", 0, 5);

			List<Employee> employees = mapper.getEmployeeByConditionIf(employee);
			for (Employee employee1 : employees) {
				System.out.println(employee1);
			}
			// 代理对象
			System.out.println(mapper.getClass());

//			  如果查询的时候有些条件没有带 sql拼装会有问题，比如id 没有带，那么怎么处理呢？
//			第一种：可以在where查询是使用 where 1=1
//			第二种：mybatis 推荐使用where标签将所有的查询条件 包括在内

		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void  testDynamicTrim() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		try {

			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeDynamicSqlMapper mapper = sqlSession.getMapper(EmployeeDynamicSqlMapper.class);
			Employee employee = new Employee(null, "哈哈哈",null, null, 5);

			List<Employee> employees = mapper.getEmployeeByConditionTrim(employee);
			for (Employee employee1 : employees) {
				System.out.println(employee1);
			}
			// 代理对象
			System.out.println(mapper.getClass());

//			  如果查询的时候有些条件没有带 sql拼装会有问题，比如id 没有带，那么怎么处理呢？
//			第一种：可以在where查询是使用 where 1=1
//			第二种：mybatis 推荐使用where标签将所有的查询条件 包括在内

		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void  testDynamicChoose() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		try {

			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeDynamicSqlMapper mapper = sqlSession.getMapper(EmployeeDynamicSqlMapper.class);
			Employee employee = new Employee(null, "哈哈哈",null, null, 5);

			List<Employee> employees = mapper.getEmployeeByConditionChoose(employee);
			for (Employee employee1 : employees) {
				System.out.println(employee1);
			}
			// 代理对象
			System.out.println(mapper.getClass());

//			  如果查询的时候有些条件没有带 sql拼装会有问题，比如id 没有带，那么怎么处理呢？
//			第一种：可以在where查询是使用 where 1=1
//			第二种：mybatis 推荐使用where标签将所有的查询条件 包括在内

		} finally {
			sqlSession.close();
		}
	}





	@Test
	public void  testDynamicSqlUpdate() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeDynamicSqlMapper mapper = sqlSession.getMapper(EmployeeDynamicSqlMapper.class);

//			Employee employee = new Employee(3, "嘿嘿dg",null, null, 5);
//			//添加
//			mapper.update(employee);
//
//			// 手动提交数据
//			sqlSession.commit();

			List<Employee> employees = mapper.getEmployeeByConditionForeach(Arrays.asList(1, 2, 3, 4));
			for (Employee employee : employees) {
				System.out.println(employee);
			}
			// 代理对象
			System.out.println(mapper.getClass());
//			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}
























}
