package com.mybatis.cache;

import com.mybatis.cache.dao.DepartmentMapper;
import com.mybatis.cache.dao.EmployeeMapper;
import com.mybatis.cache.po.Department;
import com.mybatis.cache.po.Employee;
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
public class LearnMybatisCacheApplicationTests {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		File file = ResourceUtils.getFile("classpath:mybatis/mybatis-config.xml");

//		File inputFile = ResourceUtils.getFile("classpath:mybatis/mybatis-config.xml");
		InputStream inputStream = new FileInputStream(file);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void contextLoads() {
	}


	/**
	 * mybatis提供了两级缓存
	 * 一级缓存：(本地缓存), 也称为sqlSession级别的缓存，一级缓存是一直开启的。
	 *   当前的sqlSession缓存和新的sqlSession缓存 两者之间是不能共用的。
	 *         与数据库同一次会话期间查询到的数据会放到本地缓存中
	 *         以后如果需要缓存相同的数据，直接从缓存中拿取，没有必要再去查询数据库
	 *    如果一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要向数据库发出查询请求）
	 *
	 * 二级缓存：(全局缓存)
	 * （全局缓存）：基于namespace级别的缓存：一个namespace对应一个二级缓存：
	 * 		工作机制：
	 * 		1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
	 * 		2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容；
	 * 		3、sqlSession===EmployeeMapper==>Employee
	 * 						DepartmentMapper===>Department
	 * 			不同namespace查出的数据会放在自己对应的缓存中（map）
	 * 			效果：数据会从二级缓存中获取
	 * 				查出的数据都会被默认先放在一级缓存中。
	 * 				只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
	 * 		使用：
	 * 			1）、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
	 * 			2）、去mapper.xml中配置使用二级缓存：
	 * 				<cache></cache>
	 * 			3）、我们的POJO需要实现序列化接口
	 *
	 * 和缓存有关的设置/属性：
	 * 			1）、cacheEnabled=true：false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
	 * 			2）、每个select标签都有useCache="true"：
	 * 					false：不使用缓存（一级缓存依然使用，二级缓存不使用）
	 * 			3）、【每个增删改标签的：flushCache="true"：（一级二级都会清除）】
	 * 					增删改执行完成后就会清楚缓存；
	 * 					测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
	 * 					查询标签：flushCache="false"：
	 * 						如果flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
	 * 			4）、sqlSession.clearCache();只是清楚当前session的一级缓存；
	 * 			5）、localCacheScope：本地缓存作用域：（一级缓存SESSION）；当前会话的所有数据保存在会话缓存中；
	 * 								STATEMENT：可以禁用一级缓存；
	 */

	@Test
	public void testFirstLeverCache() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employeeByMap = mapper.getEmployeeById(1);
			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println(employeeByMap);
			// 如果这里再次调用查询,会去查询数据库吗？这里不会再次查询，因为第二次查询的数据和第一次的一样，所以
			// 第二次查询是从缓存中取数据
			Employee employee1 = mapper.getEmployeeById(1);
			System.out.println(employee1);

			System.out.println(employeeByMap==employee1);
			// true
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）：
	 * 		1、sqlSession不同。
	 * 		2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据)
	 * 		3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
	 * 		4、sqlSession相同，手动清除了一级缓存（缓存清空）
	 * @throws IOException
	 */
	@Test
	public void testFirstLeverCacheInvalid() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
//		SqlSession sqlSession2 = factory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employeeByMap = mapper.getEmployeeById(1);
			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println(employeeByMap);
			// 如果这里再次调用查询,会去查询数据库吗？这里不会再次查询，因为第二次查询的数据和第一次的一样，所以
			// 第二次查询是从缓存中取数据

			//1、sqlSession不同。开启一个新的sqlSession
//			EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
//			Employee employee2 = mapper2.getEmployeeById(1);
//			System.out.println(employee2);

//			System.out.println(employeeByMap==employee2);

			// 3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
//			Employee employeeAdd = new Employee(null, "黑乎乎","8976@qq.com", 1, 5);
//			mapper.insert(employeeAdd);
//			System.out.println("添加数据成功");

//			4、sqlSession相同，手动清除了一级缓存（缓存清空）
//			sqlSession.clearCache();

			Employee employee2 = mapper.getEmployeeById(1);
			System.out.println(employee2);



			//2.sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据,这个当然也会去数据库查询，因为缓存中没有)
//			Employee employee = mapper.getEmployeeById(2);
//			System.out.println(employee);



		} finally {
			sqlSession.close();
//			sqlSession2.close();
		}
	}


	/**
	 * 开启了二级缓存
	 *
	 *
	 *  * 二级缓存：(全局缓存)
	 * （全局缓存）：基于namespace级别的缓存：一个namespace对应一个二级缓存：
	 * 		工作机制：
	 * 		1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
	 * 		2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容；
	 * 		3、sqlSession===EmployeeMapper==>Employee
	 * 						DepartmentMapper===>Department
	 * 			不同namespace查出的数据会放在自己对应的缓存中（map）
	 * 			效果：数据会从二级缓存中获取
	 * 				查出的数据都会被默认先放在一级缓存中。
	 *
	 * 			这里注意注意注意【只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中】
	 *
	 *
	 * 		使用：
	 * 			1）、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
	 * 			2）、去mapper.xml中配置使用二级缓存：
	 * 				<cache></cache>
	 * 			3）、我们的POJO需要实现序列化接口
	 *
	 * 和缓存有关的设置/属性：
	 * 			1）、cacheEnabled=true：false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
	 * 			2）、每个select标签都有useCache="true"：
	 * 					false：不使用缓存（一级缓存依然使用，二级缓存不使用）
	 * 			3）、【每个增删改标签的：flushCache="true"：（一级二级都会清除）】
	 * 					增删改执行完成后就会清楚缓存；
	 * 					测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
	 * 					查询标签：flushCache="false"：
	 * 						如果flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
	 * 			4）、sqlSession.clearCache();只是清楚当前session的一级缓存；
	 * 			5）、localCacheScope：本地缓存作用域：（一级缓存SESSION）；当前会话的所有数据保存在会话缓存中；
	 * 								STATEMENT：可以禁用一级缓存；
	 * @throws IOException
	 */
	@Test
	public void testSecondLeverCache() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		SqlSession sqlSession2 = factory.openSession();
		try {
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);

			Employee employeeByMap = mapper.getEmployeeById(1);
			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println("二级缓存开启查询employeeByMap："+employeeByMap);
			// 使用二级缓存,一定要注意 在关闭了这个sqlSession会话才可以
			sqlSession.close();

			//第二次查询前执行增删改
//			Employee employeeAdd = new Employee(null, "sg","7546@qq.com", 0, 4);
			// 注意上面的 第一个sqlSession已经关闭了，所以不能写成mapper，应该是mapper2
//			mapper2.insert(employeeAdd);

			Employee employee2 = mapper2.getEmployeeById(1);
			System.out.println("二级缓存开启employee2："+ employee2);
			sqlSession2.close();

		} finally {


		}
	}



	@Test
	public void testSecondLeverCache2() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		SqlSession sqlSession = factory.openSession();
		SqlSession sqlSession2 = factory.openSession();
		try {
			DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
			DepartmentMapper mapper2 = sqlSession2.getMapper(DepartmentMapper.class);

			Department department = mapper.findById(1);
			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println("没有开启二级缓存查询department："+department);
			// 使用二级缓存,一定要注意 在关闭了这个sqlSession会话才可以
			sqlSession.close();


			Department department2 = mapper2.findById(1);
			System.out.println("没有开启二级缓存查询employee2："+ department2);
			sqlSession2.close();

		} finally {


		}
	}
























}
