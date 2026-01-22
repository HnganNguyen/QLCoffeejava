package shared.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableDTO {

    private int id;
    private String nameTable;
    private int status;

    public TableDTO() {
    }

    public TableDTO(int id, String nameTable, int status) {
        this.id = id;
        this.nameTable = nameTable;
        this.status = status;
    }

    public TableDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("MA");
        this.nameTable = rs.getString("TENBAN");
        this.status = rs.getInt("TRANGTHAI");
    }

    public int getID() {
        return id;
    }

    public String getNameTable() {
        return nameTable;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return nameTable;
    }
}
