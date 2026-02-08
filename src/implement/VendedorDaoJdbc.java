package implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                DepartamentoModel departamentoModel = instantiateDepartmento(rs);

                VendedorModel vendedorModel = instantiateVendedor(rs, departamentoModel);

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

    private VendedorModel instantiateVendedor(ResultSet rs, DepartamentoModel dep) throws SQLException {
        VendedorModel vendedor = new VendedorModel();
        vendedor.setId(rs.getInt("Id"));
        vendedor.setNome(rs.getString("Name"));
        vendedor.setEmail(rs.getString("Email"));
        vendedor.setSalarioBase(rs.getDouble("BaseSalary"));
        vendedor.setDataDeNascimento(rs.getDate("BirthDate"));
        vendedor.setDepartamentoModel(dep);
        return vendedor;
    }

    private DepartamentoModel instantiateDepartmento(ResultSet rs) throws SQLException {
        DepartamentoModel dep = new DepartamentoModel();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setNome(rs.getString("DepName"));
        return dep;

    }

    @Override
    public List<VendedorModel> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List<VendedorModel> findByDepartamento(DepartamentoModel dep) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name");

            st.setInt(1, dep.getId());

            rs = st.executeQuery();

            List<VendedorModel> list = new ArrayList<>();

            Map<Integer, DepartamentoModel> map = new HashMap<>();

            while (rs.next()) {

                DepartamentoModel department = map.get(rs.getInt("DepartmentId"));
                if (department == null) {
                    department = instantiateDepartmento(rs);
                    map.put(rs.getInt("DepartmentId"), department);
                }

                VendedorModel vendedorModel = instantiateVendedor(rs, department);

                list.add(vendedorModel);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

}
