package implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO seller "
                            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getDataDeNascimento().getTime()));
            st.setDouble(4, obj.getSalarioBase());
            st.setInt(5, obj.getDepartamentoModel().getId());

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                throw new DbException("Erro inesperado, nenhuma linha foi afetada");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(VendedorModel obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE seller "
                            + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE Id = ?");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getDataDeNascimento().getTime()));
            st.setDouble(4, obj.getSalarioBase());
            st.setInt(5, obj.getDepartamentoModel().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM seller "
                            + "WHERE Id = ?");

            st.setInt(1, id);
            st.executeUpdate();

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
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

    // instancias de vendedor
    // transforma dados do banco de dados em java
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

    // instancia de departamento
    // transforma dados do banco de dados em java
    private DepartamentoModel instantiateDepartmento(ResultSet rs) throws SQLException {
        DepartamentoModel dep = new DepartamentoModel();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setNome(rs.getString("DepName"));
        return dep;

    }

    @Override
    public List<VendedorModel> findAll() {
        // executa sql
        PreparedStatement st = null;
        // guarda resultado da consulta
        ResultSet rs = null;

        try {
            // executa comando sql
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            // retorna os dados no result set
            rs = st.executeQuery();

            // guarda os vendedores que serao retornados
            List<VendedorModel> list = new ArrayList<>();

            // para nao criar o mesmo departamento varias vezes, utiliza map por chave
            Map<Integer, DepartamentoModel> map = new HashMap<>();

            while (rs.next()) {

                // busca no map e evita departamento duplicado
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

    @Override
    public List<VendedorModel> findByDepartamento(DepartamentoModel dep) {
        // executa sql
        PreparedStatement st = null;
        // guarda resultado da consulta
        ResultSet rs = null;

        try {
            // executa comando sql
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name");

            // substitui o ? pelo id
            st.setInt(1, dep.getId());

            // retorna os dados no result set
            rs = st.executeQuery();

            // guarda os vendedores que serao retornados
            List<VendedorModel> list = new ArrayList<>();

            // para nao criar o mesmo departamento varias vezes, utiliza map por chave
            Map<Integer, DepartamentoModel> map = new HashMap<>();

            while (rs.next()) {

                // busca no map e evita departamento duplicado
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
