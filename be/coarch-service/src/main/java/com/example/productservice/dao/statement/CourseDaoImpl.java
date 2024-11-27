package com.example.productservice.dao.statement;

import com.example.productservice.dao.mapper.CourseMapper;
import com.example.productservice.dao.statement.interfaces.CourseDao;
import com.example.productservice.model.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
@Component
public class CourseDaoImpl implements CourseDao {
    private final JdbcTemplate jdbcTemplate;

    GeneratedKeyHolder holder =new GeneratedKeyHolder();

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private CourseMapper courseMapper = new CourseMapper();
    @Override
    public List<Course> getAllCourse() {
        String query = "SELECT * FROM course";
        return jdbcTemplate.query(query, new CourseMapper().getCourseMap());
    }
    @Override
    public List<Course> getCourseByType(String type){
        String query = "select * from course where type = ?";
        return jdbcTemplate.query(query, new CourseMapper().getCourseMap(),type);
    }
    @Override
    public Long createCourse(Course course){
        String query = "INSERT INTO course(pt_id,type,price,description,number_session,img,name_course,createAt)" +
                "VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1,course.getPtId());
                statement.setString(2,course.getType());
                statement.setFloat(3,course.getPrice());
                statement.setString(4,course.getDescription());
                statement.setLong(5,course.getNumberSession());
                statement.setString(6,course.getImg());
                statement.setString(7,course.getNameCoarch());
                statement.setString(8, course.getCreateAt());
                return statement;
            }
        },holder);
        Long key = holder.getKey().longValue();
        return key;
    }

    public Course getCourseById(Long courseId) {
        String sql = "SELECT * FROM Course WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,new  CourseMapper().getCourseMap(), courseId);
    }

    public int updateCourseQuantity(Long courseId, int quantity) {
        String sql = "UPDATE Course SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";
        return jdbcTemplate.update(sql, quantity, courseId, quantity);
    }


}
