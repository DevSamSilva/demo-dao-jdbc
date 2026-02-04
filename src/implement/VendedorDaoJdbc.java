package implement;

import java.util.List;

import dao.VendedorDao;
import model.VendedorModel;

public class VendedorDaoJdbc implements VendedorDao {

    @Override
    public void insert(VendedorModel obj) {
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void update(VendedorModel obj) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public VendedorModel findById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<VendedorModel> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
