package com.alex.repository.jdbc;

import com.alex.model.Cost;
import com.alex.repository.CostRepository;
import com.alex.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcCostRepository implements CostRepository {
    private static final RowMapper<Cost> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Cost.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertCost;

    @Autowired
    public JdbcCostRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertCost = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("costs")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public Cost save(Cost cost, int userId) {
        ValidationUtil.validate(cost);
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", cost.getId())
                .addValue("description", cost.getDescription())
                .addValue("cost", cost.getCost())
                .addValue("date_time", cost.getDateTime())
                .addValue("user_id", userId);

        if (cost.isNew()) {
            Number newId = insertCost.executeAndReturnKey(map);
            cost.setId(newId.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("" +
                            "UPDATE costs " +
                            "   SET description=:description, cost=:cost, date_time=:date_time " +
                            " WHERE id=:id AND user_id=:user_id"
                    , map) == 0) {
                return null;
            }
        }
        return cost;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM costs WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Cost get(int id, int userId) {
        List<Cost> costs = jdbcTemplate.query(
                "SELECT * FROM costs WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(costs);
    }

    @Override
    public List<Cost> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM costs WHERE user_id=? ORDER BY date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Cost> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM costs WHERE user_id=? AND date_time BETWEEN ? and ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDateTime, endDateTime);
    }
}
