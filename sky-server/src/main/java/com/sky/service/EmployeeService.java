package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

import java.util.List;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 员工新增
     * @param employeeDTO
     * */
    void sava(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 修改员工状态
     * @context：1启用-0禁用
     * @param id
     * @param status
     */
    void update(Long id, Integer status);
}
