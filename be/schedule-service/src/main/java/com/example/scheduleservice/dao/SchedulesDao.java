package com.example.scheduleservice.dao;

import com.example.scheduleservice.dao.mapper.SchedulesMapper;
import com.example.scheduleservice.model.DTO.SessionOfflineDTO;
import com.example.scheduleservice.model.DTO.SessionOnlineDTO;
import com.example.scheduleservice.model.Exercise;
import com.example.scheduleservice.model.Sessions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class SchedulesDao {
    public final JdbcTemplate jdbcTemplate;
    GeneratedKeyHolder holder =new GeneratedKeyHolder();

    public SchedulesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Long setSessionOffline(SessionOfflineDTO sessions){
        String query = "insert into session(schedules_id,exercise_id,sessions_time,nutrition,status_sessions,session_no,location,description)" +
                "values(?,?,?,?,1,?,?,?,?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1,sessions.getSchedulesId());
                statement.setLong(2,sessions.getExerciseId());
                statement.setString(3,sessions.getSessionTime());
                statement.setString(4,sessions.getNutrition());
                statement.setLong(5,sessions.getSessionNo());
                statement.setString(6,sessions.getLocation());
                statement.setString(7,sessions.getDescription());
                return statement;
            }
        },holder);
        Long c = holder.getKey().longValue();
        return c;
    }



    public Long setExercise(Exercise exercise){
        String query = "insert into exercise(session_id,exercise_name,exe_set,exe_rep,descriptions,status)" +
                "values(?,?,?,?,?,1)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1,exercise.getSessionId());
                statement.setString(2,exercise.getExerciseName());
                statement.setLong(3,exercise.getExeSet());
                statement.setLong(4,exercise.getExeRep());
                statement.setString(5,exercise.getDescriptions());
                return statement;
            }
        },holder);

        return holder.getKey().longValue();
    }


}
