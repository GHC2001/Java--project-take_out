package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     * @param employeeLoginDTO
     * @autor lianghx
     * @hate 2025/01/23 上午 9:01
     * @return result
     */
    @PostMapping("/login")
    @ApiOperation("员工登陆")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }


    /**
     * 新增
     * @param employeeDTO
     * @autor lianghx
     * @hate 2025/02/1 上午 8:30
     * @return result
     * */
    @PostMapping
    @ApiOperation("员工新增")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("员工新增，参数为：{}", employeeDTO);
        if (employeeDTO == null) {
            return Result.error("参数为空，请检查");
        }

        employeeService.sava(employeeDTO);

        return Result.success("SUCCESS!");
    }

    /**
     * 分页查询
     * @autor lianghx
     * @hate 2025/02/1 下午 15:15
     * @return result
     * */
    @GetMapping("page")
    @ApiOperation("员工分页查询")
    public Result pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        if (employeePageQueryDTO == null) {
            return Result.error("参数为空，请检查");
        }
        log.info("员工分页查询，参数为：{}", employeePageQueryDTO);
        PageResult result =  employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(result);
    }

    /**
     * 退出
     * @autor lianghx
     * @hate 2025/02/1 下午 2:00
     * @return result
     */
    @PostMapping("/logout")
    @ApiOperation("员工登出")
    public Result<String> logout() {
        return Result.success();
    }

}
