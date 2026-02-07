package implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import dao.VendedorDao;
import db.DB;
import db.DbException;
import model.DepartamentoModel;
import model.VendedorModel;

public class VendedorDaoJdbc implements VendedorDao {

    private Connection conn;

    public VendedorDaoJdbc(Connection conn) {
        this.conn = conn;
    }

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

        // executa sql
        PreparedStatement st = null;
        // guarda resultado da consulta
        ResultSet rs = null;

        try {
            // monta a query sql
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?");
            // substitui ? por id
            st.setInt(1, id);

            // retorna os dados no result set
            rs = st.executeQuery();

            // verifica se encontrou resultado
            if (rs.next()) {
                // transforma dados do banco de dados em java
                DepartamentoModel departamentoModel = new DepartamentoModel();
                departamentoModel.setId(rs.getInt("DepartmentId"));
                departamentoModel.setNome(rs.getString("DepName"));

                VendedorModel vendedorModel = new VendedorModel();
                vendedorModel.setId(rs.getInt("Id"));
                vendedorModel.setNome(rs.getString("Name"));
                vendedorModel.setEmail(rs.getString("Email"));
                vendedorModel.setSalarioBase(rs.getDouble("BaseSalary"));
                vendedorModel.setDataDeNascimento(rs.getDate("BirthDate"));
                vendedorModel.setDepartamentoModel(departamentoModel);
                return vendedorModel;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public List<VendedorModel> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
