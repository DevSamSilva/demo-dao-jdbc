package dao;

import java.util.List;

import model.DepartamentoModel;

public interface DepartamentoDao {
    void insert(DepartamentoModel obj);

    void update(DepartamentoModel obj);

    void deleteById(Integer id);

    DepartamentoModel findById(Integer id);

    List<DepartamentoModel> findAll();
}
