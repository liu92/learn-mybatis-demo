package com.mybatis.ssm.dao;

import com.mybatis.ssm.po.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 员工mapper
 * @ClassName: EmployeeMapper
 * @Description:
 * @Author: lin
 * @Date: 2019/9/25 20:17
 * History:
 * @<version> 1.0
 */
@Mapper
public interface EmployeeMapper {



    /**
     * 查询
     * @param id
     * @return
     */
    Employee getEmployeeById(Integer id);

    /**
     * 更新
     * @param employee
     */
    void update(Employee employee);

    /**
     * 删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 插入数据
     * @param employee
     */
    void insert(Employee employee);

    /**
     * 根据名称查询
     * @param lastName
     * @return
     */
    Employee getByName(String lastName);


    /**
     * 查询全部
     * @return
     */
    List<Employee> findAll();

}





