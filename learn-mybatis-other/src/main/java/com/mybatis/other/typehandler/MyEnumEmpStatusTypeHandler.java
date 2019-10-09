package com.mybatis.other.typehandler;

import com.mybatis.other.po.EmpStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现TypeHandler接口，或者继承BaseTypeHandler
 * @ClassName: MyEnumEmpStatusTypeHandler
 * @Description:
 * @Author: lin
 * @Date: 2019/10/8 12:28
 * History:
 * @<version> 1.0
 */
public class MyEnumEmpStatusTypeHandler implements TypeHandler<EmpStatus> {

    /**
     * 定义当前数据如何保存到数据库中
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, EmpStatus parameter, JdbcType jdbcType) throws SQLException {
        System.out.println("保存的状态码: " + parameter.getCode());
        // 这里设置保存枚举
         ps.setString(i, parameter.getCode().toString());
    }

    @Override
    public EmpStatus getResult(ResultSet rs, String columnName) throws SQLException {
        // 根据从数据库拿到的枚举的状态码返回一个枚举对象
        int code = rs.getInt(columnName);
        System.out.println("从数据库中获取的状态码："+ code);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);
        return status;
    }

    @Override
    public EmpStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
        // 这里是按照索引拿取
        int code = rs.getInt(columnIndex);
        System.out.println("从数据库中获取的状态码："+ code);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);
        return status;
    }

    @Override
    public EmpStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 这里是调用的存储过程拿取
        int code = cs.getInt(columnIndex);
        System.out.println("从数据库中获取的状态码："+ code);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);
        return status;
    }
}
