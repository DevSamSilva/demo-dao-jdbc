package implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import dao.DepartamentoDao;
import db.DB;
import db.DbException;
import model.DepartamentoModel;

public class DepartamentoDaoJdbc implements DepartamentoDao {

    private Connection conn;

    public DepartamentoDaoJdbc(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(DepartamentoModel obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO department "
                            + "(Name) "
                            + "VALUES "
                            + "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());

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
    public void update(DepartamentoModel obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE department "
                            + "SET Name = ? "
                            + "WHERE Id = ?");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getId());

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
                    "DELETE FROM department "
                            + "WHERE Id = ?");

            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public DepartamentoModel findById(Integer id) {

        // executa sql
        PreparedStatement st = null;
        // guarda resultado da consulta
        ResultSet rs = null;

        try {
            // monta a query sql
            st = conn.prepareStatement(
                    "SELECT * FROM department WHERE Id = ?");
            // substitui ? por id
            st.setInt(1, id);

            // retorna os dados no result set
            rs = st.executeQuery();

            // verifica se encontrou resultado
            if (rs.next()) {
                DepartamentoModel departamentoModel = instantiateDepartmento(rs);

                return departamentoModel;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private DepartamentoModel instantiateDepartmento(ResultSet rs) throws RuntimeException, SQLException {
        DepartamentoModel dep = new DepartamentoModel();
        dep.setId(rs.getInt("Id"));
        dep.setNome(rs.getString("Name"));
        return dep;
    }

    @Override
    public List<DepartamentoModel> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
