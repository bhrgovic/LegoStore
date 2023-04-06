package hr.algebra.java.web.lego.legostorewithdata.repository;

import hr.algebra.java.web.lego.legostorewithdata.domain.Category;
import hr.algebra.java.web.lego.legostorewithdata.domain.Lego;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
@Transactional
public class LegoRepositoryJdbc implements LegoRepository {

    private static final String LEGO_PIECES_TABLE_NAME = "LEGO_PIECES";
    private static final String LEGO_PIECES_TABLE_NAME_ID = "ID";

    private static final String SELECT_ALL_LEGO_PIECES = "SELECT * FROM LEGO_PIECES WHERE 1=1 ";

    private static final String SELECT_LEGO_PIECE = "SELECT * FROM LEGO_PIECES WHERE ID=";

    private static final String DELETE_LEGO_PIECE = "DELETE FROM LEGO_PIECES WHERE ID=";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public LegoRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate =jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(LEGO_PIECES_TABLE_NAME)
                .usingGeneratedKeyColumns(LEGO_PIECES_TABLE_NAME_ID);
    }

    @Override
    public List<Lego> getAllLegoPiecesList() {
        return jdbcTemplate.query(SELECT_ALL_LEGO_PIECES, this::mapRowToLego);
    }

    private Lego mapRowToLego(ResultSet rs, int rowNum) throws SQLException {
        return new Lego(
                rs.getString("NAME"),
                rs.getInt(LEGO_PIECES_TABLE_NAME_ID),
                Category.valueOf(rs.getString("CATEGORY")),
                rs.getBigDecimal("PRICE")
        );
    }

    @Override
    public void saveNewLegoPiece(Lego lego) {
        saveLegoPieceDetails(lego);
    }

    private void saveLegoPieceDetails(Lego lego) {
        Map<String, Object> legoDetails = new HashMap<>();

        legoDetails.put(LEGO_PIECES_TABLE_NAME_ID, lego.getId());
        legoDetails.put("NAME", lego.getName());
        legoDetails.put("CATEGORY", lego.getCategory());
        legoDetails.put("PRICE", lego.getPrice());

        simpleJdbcInsert.executeAndReturnKey(legoDetails);
    }

    @Override
    public List<Lego> filterLegoPieces(Lego filter) {

        String query = SELECT_ALL_LEGO_PIECES;

        if(Optional.ofNullable(filter.getId()).isPresent()) {
            query += " AND ID = " + filter.getId();
        }

        if(Optional.ofNullable(filter.getName()).isPresent()) {
            query += " AND NAME = " + filter.getName();
        }

        if(Optional.ofNullable(filter.getCategory()).isPresent()) {
            query += " AND CATEGORY = " + filter.getCategory();
        }

        if(Optional.ofNullable(filter.getPrice()).isPresent()) {
            query += " AND PRICE = " + filter.getPrice();
        }

        return jdbcTemplate.query(query, this::mapRowToLego);
    }

    @Override
    public void deleteLego(Lego lego) {
        String query = DELETE_LEGO_PIECE + lego.getId();
        jdbcTemplate.execute(query);
    }

    @Override
    public void updateLego(Lego lego) {
        String query="UPDATE LEGO_PIECES SET NAME='" + lego.getName() +
        "' ,PRICE=" + lego.getPrice() +
                " ,CATEGORY='" + lego.getCategory() +
                "' WHERE ID=" + lego.getId();
        jdbcTemplate.execute(query);
    }

    @Override
    public Lego getLegoPiece(int id) {
        String query = SELECT_LEGO_PIECE + id;
        return jdbcTemplate.queryForObject(query,this::mapRowToLego);
    }






}

