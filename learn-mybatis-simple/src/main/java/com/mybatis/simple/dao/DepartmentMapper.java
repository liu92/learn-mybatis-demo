package com.mybatis.simple.dao;

import com.mybatis.simple.po.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName: DepartmentMapper
 * @Description:
 * @Author: lin
 * @Date: 2019/9/29 16:19
 * @History:
 * @<version> 1.0
 */
@Mapper
public interface DepartmentMapper {


    /**
     * 根据id
     * @param id
     * @return
     */
    Department getDepartByIdPlus(int id);


    /**
     * 根据id
     * @param id
     * @return
     */
    Department findById(int id);

    /**
     * 添加
     * @param department
     */
    Boolean add(Department department);

    /**
     * 查询全部
     * @return
     */
    List<Department> findAll();


    /**
     * 根据id 分步查询
     * @param id
     * @return
     */
    Department getDepartByIdStep(int id);

}
