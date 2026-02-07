package dao;

import db.DB;
import implement.VendedorDaoJdbc;

public class FabricaDao {
    public static VendedorDao criarVendedorDao() {
        return new VendedorDaoJdbc(DB.getConnection());
    }
}
