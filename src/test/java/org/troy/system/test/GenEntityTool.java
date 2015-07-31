package org.troy.system.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.troy.common.component.jdbc.NameConverter;

public class GenEntityTool  extends BaseJunitCase{

    @Autowired
    private JdbcTemplate template;

    String entityPath  ="F:\\workspace\\IntelliJworkspace\\tcsf_hz\\src\\main\\java\\com\\cnnct\\tcsf\\entity";

    private String[] tablenames = new String[]{"trans_addpaid"};

    private String[] colnames; // 列名数组

    private String[] colTypes; // 列名类型数组

    private int[] colSizes; // 列名大小数组

    private boolean f_util = false; // 是否需要导入包java.util.*

    private boolean f_sql = false; // 是否需要导入包java.sql.*

    @Test
    public void genEntity() throws Exception{
        Connection conn = template.getDataSource().getConnection(); // 得到数据库连接
        try {
            for (String table:tablenames){
                readTable(table,conn,"select * from " + table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    private void readTable(String tablename,Connection conn,String strsql) throws Exception{
        PreparedStatement pstmt = conn.prepareStatement(strsql);
        ResultSetMetaData rsmd = pstmt.getMetaData();
        int size = rsmd.getColumnCount(); // 共有多少列
        colnames = new String[size];
        colTypes = new String[size];
        colSizes = new int[size];
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            colnames[i] = rsmd.getColumnName(i + 1);
            colTypes[i] = rsmd.getColumnTypeName(i + 1);
            if (colTypes[i].equalsIgnoreCase("datetime")) {
                f_util = true;
            }
            if (colTypes[i].equalsIgnoreCase("image")
                    || colTypes[i].equalsIgnoreCase("text")) {
                f_sql = true;
            }
            colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
        }
        String content = parse(tablename);
        try {
            File to = new File(entityPath+File.separator+NameConverter.table2java(tablename) + ".java");
            FileUtils.writeStringToFile(to, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析处理(生成实体类主体代码)
     */
    private String parse(String tablename) {
        StringBuffer sb = new StringBuffer();
        if (f_util) {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n\r\n\r\n");
        }
        sb.append("public class " + NameConverter.table2java(tablename) + " {\r\n");
        processAllAttrs(sb);
        processAllMethod(sb);
        sb.append("}\r\n");
        System.out.println(sb.toString());
        return sb.toString();

    }

    /**
     * 生成所有的方法
     *
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colnames.length; i++) {
            String colname = NameConverter.db2java(colnames[i]);
            sb.append("\tpublic void set" +NameConverter.table2java(colname) + "(" + sqlType2JavaType(colTypes[i]) + " " + colname + "){\r\n");
            sb.append("\t\tthis." + colname + "=" + colname + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get"+ NameConverter.table2java(colname) + "(){\r\n");
            sb.append("\t\treturn " + colname + ";\r\n");
            sb.append("\t}\r\n");
        }
    }

    /**
     * 解析输出属性
     *
     * @return
     */
    private void processAllAttrs(StringBuffer sb) {
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " "
                    + NameConverter.db2java(colnames[i]) + ";\r\n");
        }
    }

    private String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "bool";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "int";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("decimal")
                || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("varchar")
                || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar")
                || sqlType.equalsIgnoreCase("nchar")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blob";
        } else if (sqlType.equalsIgnoreCase("text")) {
            return "Clob";
        }
        return null;
    }
}
