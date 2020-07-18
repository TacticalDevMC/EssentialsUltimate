package nl.tacticaldev.essentials.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class BufferStatement {

    private Object[] values;
    private String query;
    private Exception stacktrace;

    public BufferStatement(final String query, final Object... values) {
        super();
        this.query = query;
        this.values = values;
        (this.stacktrace = new Exception()).fillInStackTrace();
    }

    public PreparedStatement prepareStatement(final Connection con) throws SQLException {
        final PreparedStatement ps = con.prepareStatement(this.query);
        for (int i = 1; i <= this.values.length; i++) {
            ps.setObject(i, String.valueOf(this.values[i - 1]));
        }
        return ps;
    }

    public StackTraceElement[] getStacktrace() {
        return this.stacktrace.getStackTrace();
    }

    public String toString() {
        return "Query: " + this.query + ", values:" + Arrays.toString(this.values);
    }

}