package com.jdk.projectinterface.controller;

import com.jdk.projectinterface.bean.Student;
import com.jdk.projectinterface.bean.Teacher;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.service.AccountService;
import com.jdk.projectinterface.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    /**
     * 用户登录
      * @param type 用户角色
     * @param account 账号
     * @param password 密码
     * @return 登录成功返回用户信息，失败返回code401
     */
    @GetMapping("/login")
    public Object login(@RequestParam("type") Integer type,
                        @RequestParam("account") String account,
                        @RequestParam("password") String password){

        return accountService.accountLogin(type, account, password);
    }

    /**
     * 注销用户
     * @param adminId 执行操作的管理员id
     * @param adminPassword 执行操作的管理员密码
     * @param type 被删除用户角色
     * @param accountId 被删用户的账户id
     * @return 执行删除操作的结果
     */
    @GetMapping("/deleteAccount")
    public Object deleteAccount(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("adminPassword") String adminPassword,
            @RequestParam("type") Integer type,
            @RequestParam("accountId") Integer accountId
    ){
        ServiceResponse response;
        response = accountService.findAdminById(adminId,adminPassword);
        if (response.getCode() != 200){
            return ServiceResponse.createFailResponse("管理员信息错误");
        }
        switch (type){
            case 1:
                /**
                 * 删除教师用户
                 */
                return accountService.deleteTeacher(accountId);
            case 2:
                /**
                 * 删除学生用户
                 */
                return accountService.deleteStudent(accountId);
            default:
                return ServiceResponse.createFailResponse("操作不允许");
        }
    }

    /**
     * 添加学生和教师的两种方法
     */

    @GetMapping("/addTeacher")
    public Object addTeacher(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("sex") Boolean sex,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("avatar") String avatar
    ){
        ServiceResponse<Teacher> response;
        if (!Utils.isPhone(phone) || !Utils.IsEmail(email)){
            response = ServiceResponse.createFailResponse("电话或邮箱填写有误");
        } else {
            Teacher teacher = new Teacher(adminId,account,password,name,sex,phone,email,avatar);
            response = accountService.addTeacher(teacher);
        }
        return response;
    }

    @GetMapping("/addStudent")
    public Object addStudent(
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("sex") Boolean sex,
            @RequestParam("avatar") String avatar,
            @RequestParam("class") String classname,
            @RequestParam("face") String face,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email
    ){
        ServiceResponse<Student> response;
        if (!Utils.isPhone(phone) || !Utils.IsEmail(email)){
            response = ServiceResponse.createFailResponse("电话或邮箱填写有误");
        } else {
            Student student = new Student(account,password,name,sex,avatar,classname,face,phone,email);
            response = accountService.addStudent(student);
        }
        return response;
    }

    /**
     * 修改学生和教师的两种方法
     */
    @GetMapping("/modifyTeacher")
    public Object modifyTeacher(
            @RequestParam("teacherId") Integer teacherId,
            @RequestParam("adminId") Integer adminId,
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("sex") Boolean sex,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("avatar") String avatar
    ){
        ServiceResponse<Teacher> response;
        if (!Utils.isPhone(phone) || !Utils.IsEmail(email)){
            response = ServiceResponse.createFailResponse("电话或邮箱填写有误");
        } else {
            Teacher teacher = new Teacher(adminId,account,password,name,sex,phone,email,avatar);
            teacher.setTeacherId(teacherId);
            response = accountService.modifyTeacher(teacher);
        }
        return response;
    }

    @GetMapping("/modifyStudent")
    public Object modifyStudent(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("sex") Boolean sex,
            @RequestParam("avatar") String avatar,
            @RequestParam("class") String classname,
            @RequestParam("face") String face,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email
    ){
        ServiceResponse<Student> response;
        if (!Utils.isPhone(phone) || !Utils.IsEmail(email)){
            response = ServiceResponse.createFailResponse("电话或邮箱填写有误");
        } else {
            Student student = new Student(account,password,name,sex,avatar,classname,face,phone,email);
            student.setStudentId(studentId);
            response = accountService.modifyStudent(student);
        }
        return response;
    }

    /**
     * 查询所有用户账号
     */
    @GetMapping("/findAccount")
    public Object findAccount(@RequestParam("type") Integer type){
        switch (type){
            case 1:
                return accountService.findAllTeacher();
            case 2:
                return accountService.findAllStudent();
            default:
                return ServiceResponse.createFailResponse("查询失败");
        }
    }

    /**
     * 通过模糊查询查找用户
     * @return 返回是一个数组
     */
    @GetMapping("/findAccountByColumn")
    public Object findAccountByColumn(
            @RequestParam("type") Integer type,
            @RequestParam("column") String column,
            @RequestParam("value") Object value
    ){

        switch (type){
            case 1:
                return accountService.findTeacher(column,value);
            case 2:
                return accountService.findStudent(column,value);
            default:
                return ServiceResponse.createFailResponse("查询失败");
        }
    }

    /**
     * 测试提取图片功能
     */
    /*@GetMapping("/show")
    public ModelAndView showImages(@RequestParam("id") Integer id, HttpServletRequest request,ModelAndView mv){
        mv.setViewName("index");
        String face = accountService.findStudent(id);
        String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + face;
        mv.addObject("imagePath",path);
        return mv;
    }*/
}
