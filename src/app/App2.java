package app;

import java.util.List;
import java.util.Scanner;

import dao.DepartamentoDao;
import dao.FabricaDao;
import model.DepartamentoModel;

public class App2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DepartamentoDao departamentoDao = FabricaDao.criarDepartamentoDao();

        System.out.println("=== Teste 1: Insert department");
        DepartamentoModel departamentoModel = new DepartamentoModel(null, "Cor");
        departamentoDao.insert(departamentoModel);
        System.out.println("Inserido! Novo ID: " + departamentoModel.getId());

        System.out.println("=== Teste 2: update department");
        departamentoModel = departamentoDao.findById(7);
        departamentoModel.setNome("Sapatos");
        departamentoDao.update(departamentoModel);
        System.out.println("Atualizado!");

        System.out.println("=== Teste 3: delete department");
        System.out.println("Digite o ID que deseja deletar");
        int id = sc.nextInt();
        departamentoDao.deleteById(id);
        System.out.println("Departamento deletado");

        System.out.println("=== teste 4: department findAll ===");
        DepartamentoModel dep2 = new DepartamentoModel(2, null);
        List<DepartamentoModel> list = departamentoDao.findAll();

        for (DepartamentoModel dp : list) {
            System.out.println(dp);
        }

        sc.close();
    }
}
