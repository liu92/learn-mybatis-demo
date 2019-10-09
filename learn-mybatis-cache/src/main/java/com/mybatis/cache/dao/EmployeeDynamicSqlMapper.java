package com.mybatis.cache.dao;

import com.mybatis.cache.po.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: EmployeeDynamicSqlMapper
 * @Description:
 * @Author: lin
 * @Date: 2019/10/7 9:37
 * @History:
 * @<version> 1.0
 */
@Mapper
public interface EmployeeDynamicSqlMapper {

    /**
     * 携带那个字段就拼接上，if判断处理
     * @param employee
     * @return
     */
    List<Employee> getEmployeeByConditionIf(Employee employee);


    /**
     *  截取处理
     * @param employee
     * @return
     */
    List<Employee> getEmployeeByConditionTrim(Employee employee);



    /**
     *  分支选择处理
     * @param employee
     * @return
     */
    List<Employee> getEmployeeByConditionChoose(Employee employee);


    /**
     * 更新处理
     * @param employee
     * @return
     */
    void update(Employee employee);


    /**
     *  遍历处理
     * @param ids
     * @return
     */
    List<Employee> getEmployeeByConditionForeach(@Param("ids") List<Integer> ids);















}
