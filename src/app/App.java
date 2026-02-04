package app;

import java.util.Date;

import dao.FabricaDao;
import dao.VendedorDao;
import model.DepartamentoModel;
import model.VendedorModel;

public class App {
    public static void main(String[] args) {
        DepartamentoModel departamentoModel = new DepartamentoModel(1, "Livros");
        System.out.println(departamentoModel);

        VendedorModel vendedorModel = new VendedorModel(21, "Bob", "bob@gmail.com", new Date(), 3000.00,
                departamentoModel);

        System.out.println(vendedorModel);

        VendedorDao vendedorDao = FabricaDao.criarVendedorDao();
    }
}
