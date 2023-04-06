package hr.algebra.java.web.lego.legostorewithdata.domain;

import java.math.BigDecimal;

public record LegoRecord(String name, Integer id, Category category, BigDecimal price) {
}
