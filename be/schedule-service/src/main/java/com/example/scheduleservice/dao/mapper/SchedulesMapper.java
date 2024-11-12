package com.example.scheduleservice.dao.mapper;

import com.example.scheduleservice.model.Sessions;
import org.springframework.jdbc.core.RowMapper;

public class SchedulesMapper {
    RowMapper<Sessions> sessionsRowMapper = ((rs, rowNum) -> {
        Sessions sessions = new Sessions();
        sessions.setId(rs.getLong("id"));
        sessions.setSchedulesId(rs.getLong("schedules_id"));
        sessions.setSessionTime(rs.getString("sessions_time"));
        sessions.setSessionName(rs.getString("session_name"));
        sessions.setNutrition(rs.getString("nutrition"));
        sessions.setSession_no(rs.getLong("session_no"));
        sessions.setLocation(rs.getString("location"));
        sessions.setDescription(rs.getString("description"));
        return sessions;
    });

    public SchedulesMapper(RowMapper<Sessions> sessionsRowMapper) {
        this.sessionsRowMapper = sessionsRowMapper;
    }
}
