package dao;

import implement.VendedorDaoJdbc;

public class FabricaDao {
    public static VendedorDao criarVendedorDao() {
        return new VendedorDaoJdbc();
    }
}
