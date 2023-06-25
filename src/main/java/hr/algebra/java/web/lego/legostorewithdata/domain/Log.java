package hr.algebra.java.web.lego.legostorewithdata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    int logId;
    private int userId;
    private Timestamp loginTime;
    private String ipAddress;
}
