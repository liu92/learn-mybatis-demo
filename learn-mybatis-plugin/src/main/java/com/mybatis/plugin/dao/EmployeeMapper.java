package com.mybatis.plugin.dao;

import com.mybatis.plugin.po.Employee;
import org.apache.ibatis.annotations.Mapper;

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


}





