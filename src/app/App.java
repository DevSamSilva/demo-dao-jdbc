package app;

import model.DepartamentoModel;

public class App {
    public static void main(String[] args) {
        DepartamentoModel departamentoModel = new DepartamentoModel(1, "Livros");
        System.out.println(departamentoModel);
    }
}
