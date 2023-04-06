package hr.algebra.java.web.lego.legostorewithdata.repository;

import hr.algebra.java.web.lego.legostorewithdata.domain.Category;
import hr.algebra.java.web.lego.legostorewithdata.domain.Lego;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LegoRepositoryMock implements LegoRepository {

    private List<Lego> legoList;

    public LegoRepositoryMock() {

        legoList = new ArrayList<>();

        Lego newLegoPiece = new Lego("Mindstorms 3",
                2893, Category.MINDSTORMS,
                new BigDecimal(300));

        Lego secondLegoPiece = new Lego("Bionics T-Rex",
                5546, Category.BIONICLE,
                new BigDecimal(400));

        legoList.add(newLegoPiece);
        legoList.add(secondLegoPiece);
    }

    @Override
    public List<Lego> getAllLegoPiecesList() {
        return legoList;
    }

    @Override
    public void saveNewLegoPiece(Lego lego) {
        legoList.add(lego);
    }

    @Override
    public List<Lego> filterLegoPieces(Lego filter) {

        List<Lego> filteredList = getAllLegoPiecesList();

        if(Optional.ofNullable(filter.getId()).isPresent()) {
            filteredList = filteredList.stream()
                    .filter(l -> l.getId().equals(filter.getId()))
                    .collect(Collectors.toList());
        }

        if(Optional.ofNullable(filter.getName()).isPresent()) {
            filteredList = filteredList.stream()
                    .filter(l -> l.getName().equals(filter.getName()))
                    .collect(Collectors.toList());
        }

        if(Optional.ofNullable(filter.getPrice()).isPresent()) {
            filteredList = filteredList.stream()
                    .filter(l -> l.getPrice().equals(filter.getPrice()))
                    .collect(Collectors.toList());
        }

        if(Optional.ofNullable(filter.getCategory()).isPresent()) {
            filteredList = filteredList.stream()
                    .filter(l -> l.getCategory().equals(filter.getCategory()))
                    .collect(Collectors.toList());
        }

        return filteredList;
    }

    @Override
    public Lego getLegoPiece(int id) {
        return null;
    }

    @Override
    public void deleteLego(Lego lego) {

    }

    @Override
    public void updateLego(Lego lego) {

    }
}
