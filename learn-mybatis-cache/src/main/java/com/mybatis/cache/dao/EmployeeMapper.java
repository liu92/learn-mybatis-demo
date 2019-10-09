package com.mybatis.cache.dao;

import com.mybatis.cache.po.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     * 查询多个，多条记录封装一个map,
     * 使用@MapKey 注解就是告诉mybatis 封装这个map的时候使用那个属性作为 map的key
     * @param lastName
     * @return
     */
    @MapKey("id")
    Map<Integer, Employee> getEmployeeByLastNameReturnMap(String lastName);


    /**
     * 查询单条
     * @param id
     * @return
     */
    Map<String,Object> getEmployeeByIdReturnMap(Integer id);


    /**
     * 查询
     * @param map
     * @return
     */
    Employee getEmployeeByMap(Map<String, Object> map);

    /**
     * 根据id和email查询, 但是这种查询 如果不加入注解 @Param() 就会报错，因为这没有与mybatis的规则对应
     *  Parameter 'id' not found. Available parameters are [arg1, arg0, param1, param2]
     * @param id
     * @param email
     * @return
     */
    Employee getEmployeeByIdAndEmail(@Param("id") Integer id, @Param("email") String email);

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
     * 根据名称查询
     * @param lastName
     * @return
     */
    List<Employee> findByLastName(String lastName);


    /**
     * 根据id查询
     * @param id
     * @return
     */
    Employee getEmployeeAndDepart(Integer id);


    /**
     * 根据id查询
     * @param id
     * @return
     */
    Employee getEmployeeByIdStep(Integer id);



    /**
     * 根据id查询
     * @param id
     * @return
     */
    Employee getEmployeeByIdDiscriminatorStep(Integer id);

    /**
     * 根据departId查询
     * @param departId
     * @return
     */
    List<Employee> getEmployeeByDepartId(Integer departId);
}





