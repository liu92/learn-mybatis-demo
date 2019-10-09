package com.mybatis.source.po;

import java.util.List;

/**
 * 部门
 * @ClassName: Department
 * @Description:
 * @Author: lin
 * @Date: 2019/10/6 22:14
 * History:
 * @<version> 1.0
 */
public class Department {

    private int id;

    private String departmentName;

    /**
     * 一个部门对应多个员工
     */
    private List<Employee> employees;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
