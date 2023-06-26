package hr.algebra.java.web.lego.legostorewithdata.repository;

import hr.algebra.java.web.lego.legostorewithdata.domain.Log;

import java.util.List;

public interface LogRepository {

    void createLog(String log);

    List<Log> getAllLogs();


}
