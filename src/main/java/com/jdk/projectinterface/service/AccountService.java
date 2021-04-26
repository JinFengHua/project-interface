package com.jdk.projectinterface.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdk.projectinterface.bean.Admin;
import com.jdk.projectinterface.bean.Student;
import com.jdk.projectinterface.bean.Teacher;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.AdminMapper;
import com.jdk.projectinterface.mapper.StudentMapper;
import com.jdk.projectinterface.mapper.TeacherMapper;
import com.jdk.projectinterface.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    StudentMapper studentMapper;

    /**
     * 根据type指定的用户角色查询数据库，进行登录
     * @param type 用户角色
     * @param username 用户账号
     * @param password 用户密码
     * @return ServiceResponse
     */
    public ServiceResponse accountLogin(Integer type, String username, String password){
        switch (type){
            case 0:
                QueryWrapper<Admin> adminQuery = new QueryWrapper<Admin>().eq("admin_account",username).eq("admin_password",password);
                Admin admin = adminMapper.selectOne(adminQuery);
                return ServiceResponse.backFailResponse("账号或密码错误","登陆成功",admin);
            case 1:
                QueryWrapper<Teacher> teacherQuery = new QueryWrapper<Teacher>().eq("teacher_account",username).eq("teacher_password",password);
                Teacher teacher = teacherMapper.selectOne(teacherQuery);
                return ServiceResponse.backFailResponse("账号或密码错误","登陆成功",teacher);
            case 2:
                QueryWrapper<Student> studentQuery = new QueryWrapper<Student>().eq("student_account",username).eq("student_password",password);
                Student student = studentMapper.selectOne(studentQuery);
                return ServiceResponse.backFailResponse("账号或密码错误","登陆成功",student);
            default:
                return ServiceResponse.createFailResponse("查询失败");
        }
    }

    /**
     * 添加教师
     * @param teacher 要添加的教师对象
     * @return 执行操作返回的ServiceResponse对象
     */
    public ServiceResponse<Teacher> addTeacher(Teacher teacher){
        ServiceResponse<Teacher> response;
        QueryWrapper<Teacher> teacherQuery = new QueryWrapper<Teacher>().eq("teacher_account",teacher.getTeacherAccount());
        Teacher queryResult = teacherMapper.selectOne(teacherQuery);
        if (!Utils.isEmpty(queryResult)){
            response = ServiceResponse.createEmptyResponse("该账号已被注册");
        } else {
            teacherMapper.insert(teacher);
            response = ServiceResponse.createResponse("账号注册成功");
        }
        return response;
    }

    /**
     * 添加学生用户
     * @param student 要添加的学生对象
     * @return 执行操作返回的ServiceResponse对象
     */
    public ServiceResponse<Student> addStudent(Student student){
        ServiceResponse<Student> response;
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<Student>().eq("student_account",student.getStudentAccount());
        Student queryResult = studentMapper.selectOne(studentQueryWrapper);
        if (!Utils.isEmpty(queryResult)){
            response = ServiceResponse.createEmptyResponse("该账号已被注册");
        } else {
            studentMapper.insert(student);
            response = ServiceResponse.createResponse("账号注册成功");
        }
        return response;
    }

    /**
     *通过id和password判断管理员身份
     */
    public ServiceResponse<Admin> findAdminById(Integer adminId, String adminPassword) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>().eq("admin_id",adminId).eq("admin_password",adminPassword);
        Admin admin = adminMapper.selectOne(queryWrapper);
        return ServiceResponse.backFailResponse("账号或密码错误","登陆成功",admin);
    }

    /**
     *修改教师信息
     */
    public ServiceResponse<Teacher> modifyTeacher(Teacher teacher) {
        teacherMapper.updateById(teacher);
        return ServiceResponse.createResponse("修改成功");
    }

    /**
     *修改学生信息
     */
    public ServiceResponse<Student> modifyStudent(Student student) {
        studentMapper.updateById(student);
        return ServiceResponse.createResponse("修改成功");
    }

    /**
     *查找所有教师
     */
    public ServiceResponse<List<Teacher>> findAllTeacher() {
        List<Teacher> teachers = teacherMapper.selectList(null);
        return ServiceResponse.createResponse("查询成功",teachers);
    }

    /**
     *查找所有学生
     */
    public ServiceResponse<List<Student>> findAllStudent() {
        List<Student> students = studentMapper.selectList(null);
        return ServiceResponse.createResponse("查询成功",students);
    }

    /**
     *根据column属性的value值，模糊查询教师信息
     */
    public ServiceResponse<List<Teacher>> findTeacher(String column,Object value) {
        List<Teacher> teachers = teacherMapper.selectList(new QueryWrapper<Teacher>().like(column, value));
        return ServiceResponse.createResponse("查询成功",teachers);
    }

    /**
     *根据column属性的value值，模糊查询学生信息
     */
    public ServiceResponse<List<Student>> findStudent(String column, Object value) {
        List<Student> students = studentMapper.selectList(new QueryWrapper<Student>().like(column, value));
        return ServiceResponse.createResponse("查询成功",students);
    }

    /**
     *通过教师id删除教师
     */
    public ServiceResponse<Teacher> deleteTeacher(Integer accountId) {
        teacherMapper.deleteById(accountId);
        return ServiceResponse.createResponse("删除成功");
    }

    /**
     * 通过学生id删除学生
     */
    public ServiceResponse<Student> deleteStudent(Integer accountId) {
        studentMapper.deleteById(accountId);
        return ServiceResponse.createResponse("删除成功");
    }

    /**
     * 查询某账号是否存在
     * 存在code为200，不存在为204
     */
    public Object confirmAccount(Integer type,String account) {
        Integer count;
        if (type == 1){
            count = teacherMapper.selectCount(new QueryWrapper<Teacher>().eq("teacher_account", account));
        } else {
            count = studentMapper.selectCount(new QueryWrapper<Student>().eq("student_account", account));
        }
        if (count == 0) {
            return ServiceResponse.createEmptyResponse(String.valueOf(count));
        } else {
            return ServiceResponse.createResponse(String.valueOf(count));
        }
    }

    public Object confirmAccount(Integer type, String account, String phone) {
        List<Teacher> accountTeachers = new ArrayList<>();
        List<Student> accountStudents = new ArrayList<>();
        if (type == 1){
            accountTeachers = teacherMapper.selectList(new QueryWrapper<Teacher>().eq("teacher_account", account).eq("teacher_phone",phone));
        } else {
            accountStudents = studentMapper.selectList(new QueryWrapper<Student>().eq("student_account", account).eq("student_phone",phone));
        }
        if (accountStudents.size() + accountTeachers.size() == 0 ) {
            return ServiceResponse.createEmptyResponse("0");
        } else if (accountStudents.size() != 0){
            return ServiceResponse.createResponse(String.valueOf(accountStudents.get(0).getStudentId()));
        } else {
            return ServiceResponse.createResponse(String.valueOf(accountTeachers.get(0).getTeacherId()));
        }
    }
}
