package dao;

import java.util.List;

import model.VendedorModel;

public interface VendedorDao {
    void insert(VendedorModel obj);

    void update(VendedorModel obj);

    void deleteById(Integer id);

    VendedorModel findById(Integer id);

    List<VendedorModel> findAll();
}
