package app;

import java.sql.DriverManager;
import java.util.Date;
import java.util.List;

import dao.FabricaDao;
import dao.VendedorDao;
import model.DepartamentoModel;
import model.VendedorModel;

public class App {
    public static void main(String[] args) {

        DriverManager.getDrivers()
                .asIterator()
                .forEachRemaining(d -> System.out.println(d.getClass().getName()));

        VendedorDao vendedorDao = FabricaDao.criarVendedorDao();

        System.out.println("=== teste 1: vendedor findById ===");

        VendedorModel vendedorModel = vendedorDao.findById(3);

        System.out.println(vendedorModel);

        System.out.println("=== teste 2: vendedor findByDepartment ===");
        DepartamentoModel dep = new DepartamentoModel(2, null);
        List<VendedorModel> list = vendedorDao.findByDepartamento(dep);

        for (VendedorModel vendedorModel2 : list) {
            System.out.println(vendedorModel2);
        }

        System.out.println("=== teste 3: vendedor findAll ===");
        DepartamentoModel dep2 = new DepartamentoModel(2, null);
        list = vendedorDao.findAll();

        for (VendedorModel vendedorModel2 : list) {
            System.out.println(vendedorModel2);
        }

        System.out.println("=== teste 4: vendedor insert ===");
        VendedorModel novoVendedor = new VendedorModel(null, "Samuel", "samueldsn@gmail.com", new Date(), 4000,
                dep2);
        vendedorDao.insert(novoVendedor);
        System.out.println("Inserido! Novo ID: " + novoVendedor.getId());

        System.out.println("=== teste 5: vendedor update ===");
        vendedorModel = vendedorDao.findById(1);
        vendedorModel.setNome("Fonfis");
        vendedorDao.update(vendedorModel);
        System.out.println("Atualizado!");

    }
}
