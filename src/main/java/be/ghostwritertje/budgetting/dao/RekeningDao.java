package be.ghostwritertje.budgetting.dao;

import be.ghostwritertje.budgetting.domain.Rekening;

import java.util.List;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public interface RekeningDao {
    List<Rekening> getRekeningen(String username);
}
