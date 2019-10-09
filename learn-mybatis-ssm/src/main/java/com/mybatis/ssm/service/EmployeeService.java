package com.mybatis.ssm.service;

import com.mybatis.ssm.dao.EmployeeMapper;
import com.mybatis.ssm.po.Employee;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: EmployeeService
 * @Description:
 * @Author: lin
 * @Date: 2019/10/7 16:25
 * History:
 * @<version> 1.0
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

// 这个是在配置文件中配置的，批量操作
//    @Autowired
//    private SqlSession sqlSession;

    /**
     * 查询全部
     * @return
     */
    public List<Employee> findAll(){
        List<Employee> all = employeeMapper.findAll();
        return  all;
    }
}
