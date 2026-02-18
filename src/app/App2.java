package app;

import java.util.Scanner;

import dao.DepartamentoDao;
import dao.FabricaDao;
import model.DepartamentoModel;

public class App2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DepartamentoDao departamentoDao = FabricaDao.criarDepartamentoDao();

        System.out.println("=== Teste 1: Insert department");
        DepartamentoModel departamentoModel = new DepartamentoModel(null, "Moda");
        departamentoDao.insert(departamentoModel);
        System.out.println("Inserido! Novo ID: " + departamentoModel.getId());

        sc.close();
    }
}
