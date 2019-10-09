package com.mybatis.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatis.other.dao.EmployeeMapper;
import com.mybatis.other.po.Employee;
import org.apache.ibatis.session.ExecutorType;
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
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnMybatisOtherApplicationTests {

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
			// 使用地址 https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
			//分页查询 只需要 PageHelper.startPage(1, 10); 这样就可以
			Page<Object> page = PageHelper.startPage(1, 6);

			List<Employee> employeeList = mapper.findEmpList();
			for (Employee employee : employeeList) {
				System.out.println(employee);
			}
			// 代理对象
			System.out.println(mapper.getClass());

			System.out.println("当前页码：" +  page.getPageNum());
			System.out.println("总记录数：" +  page.getTotal());
			System.out.println("每页的记录数：" +  page.getPageSize());
			System.out.println("总页码：" +  page.getPages());
		} finally {
			sqlSession.close();
		}
	}



	@Test
	public void  testPageInfo() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		SqlSession sqlSession = factory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			// 使用地址 https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
			//分页查询 只需要 PageHelper.startPage(1, 10); 这样就可以
			Page<Object> page = PageHelper.startPage(5, 1);

			////获取第1页，10条内容，默认查询总数count
			//PageHelper.startPage(1, 10);
			//List<Country> list = countryMapper.selectAll();
			////用PageInfo对结果进行包装
			//PageInfo page = new PageInfo(list);
			////测试PageInfo全部属性
			////PageInfo包含了非常全面的分页属性
			//assertEquals(1, page.getPageNum());
			//assertEquals(10, page.getPageSize());
			//assertEquals(1, page.getStartRow());
			//assertEquals(10, page.getEndRow());
			//assertEquals(183, page.getTotal());
			//assertEquals(19, page.getPages());
			//assertEquals(1, page.getFirstPage());
			//assertEquals(8, page.getLastPage());
			//assertEquals(true, page.isFirstPage());
			//assertEquals(false, page.isLastPage());
			//assertEquals(false, page.isHasPreviousPage());
			//assertEquals(true, page.isHasNextPage());

			List<Employee> employeeList = mapper.findEmpList();
//			PageInfo<Employee> info = new PageInfo<>(employeeList);
			// 表示分页导航， navigatePages 传入要连续显示多少页
			// 这里连续显示 5页， 也就是 如果有3页，那么是就是1,2,3
			// 如果是 有10 页， 那么第一页连续的就是 1,2,3,4,5 ,
			// 第二页也是 1,2,3,4,5 ,第三页也是 1,2,3,4,5，
			// 第四页是 2,3,4,5,6 ， 第五页是 3,4,5,6,7 这样的形式

			PageInfo<Employee> info = new PageInfo<>(employeeList, 5);
			for (Employee employee : employeeList) {
				System.out.println(employee);
			}
			// 代理对象
			System.out.println(mapper.getClass());

			System.out.println("当前页码：" +  info.getPageNum());
			System.out.println("总记录数：" +  info.getTotal());
			System.out.println("每页的记录数：" +  info.getPageSize());
			System.out.println("总页码：" +  info.getPages());
			System.out.println("是否是第一页：" +  info.isIsFirstPage());
			System.out.println("是否是最后一页：" +  info.isIsLastPage());

			System.out.println("连续显示多少页");
			// 连续显示多少页
			int[] navigatepageNums = info.getNavigatepageNums();
			for (int i = 0; i <navigatepageNums.length ; i++) {
				System.out.println(navigatepageNums[i]);
			}
		} finally {
			sqlSession.close();
		}
	}



	@Test
	public void  testAddOrUpdate() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		// 1、获取到的SqlSession不会自动提交数据
		// 批量操作 的sqlsession
		SqlSession sqlSession = factory.openSession(ExecutorType.BATCH);
         long start = System.currentTimeMillis();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

			for (int i = 0; i <10 ; i++) {
				Employee employee = new Employee(null, UUID.randomUUID().toString().substring(0, 5),"99776@qq.com", 1, 5);
				//添加
				mapper.insert(employee);
			}


			sqlSession.commit();
			long end = System.currentTimeMillis();
			// （预编译sql一次 =设置参数--->10--->执行(1)
			// 批量执行： Parameters: null, b976c(String), 99776@qq.com(String), 1(Integer), 5(Integer) ，不会打印sql语句
			// 而批量执行首先将sql语句预编译好，往这个sql语句中不断设置参数，这个sql语句发给数据库，
			// 数据库可以预编译好，接下来数据库只等待这些sql语句 要执行多少遍用的参数，把这些参数发个数据库，数据库就直接一次性执行成功

			// 非批量： 执行方式就是每次执行一个方法（增删改查），立即发送给sql 数据库，
			// 发完以后数据库一 执行，同样第二也是这样的操作
			// （预编译sql==设置参数=执行）-->10

			System.out.println("执行时长："+ (end - start) );
			// 代理对象
			System.out.println(mapper.getClass());
//			System.out.println(employee);
		} finally {
			sqlSession.close();
		}
	}



	@Test
	public void  testEnum() throws IOException {
		SqlSessionFactory factory = getSqlSessionFactory();
		SqlSession sqlSession = factory.openSession();
		try {

			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//			Employee employee = new Employee(null, UUID.randomUUID().toString().substring(0, 5),"896@qq.com", 1, 5);
//			mapper.insert(employee);
			Employee employeeById = mapper.getEmployeeById(12);
			sqlSession.commit();
			// 代理对象
			System.out.println(mapper.getClass());
			System.out.println(employeeById.getEmpStatus());
		} finally {
			sqlSession.close();
		}
	}
}
