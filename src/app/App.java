package app;

import java.sql.DriverManager;

import dao.FabricaDao;
import dao.VendedorDao;
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
    }
}
