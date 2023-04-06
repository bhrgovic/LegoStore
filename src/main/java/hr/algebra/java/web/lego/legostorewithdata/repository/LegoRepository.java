package hr.algebra.java.web.lego.legostorewithdata.repository;

import hr.algebra.java.web.lego.legostorewithdata.domain.Lego;

import java.util.ArrayList;
import java.util.List;

public interface LegoRepository {

    List<Lego> getAllLegoPiecesList();

    void saveNewLegoPiece(Lego lego);

    List<Lego> filterLegoPieces(Lego filter);

    Lego getLegoPiece(int id);

    void deleteLego(Lego lego);

    void updateLego(Lego lego);

}
