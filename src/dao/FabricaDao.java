package dao;

import db.DB;
import implement.DepartamentoDaoJdbc;
import implement.VendedorDaoJdbc;

public class FabricaDao {
    public static VendedorDao criarVendedorDao() {
        return new VendedorDaoJdbc(DB.getConnection());
    }

    public static DepartamentoDao criarDepartamentoDao() {
        return new DepartamentoDaoJdbc(DB.getConnection());
    }
}
