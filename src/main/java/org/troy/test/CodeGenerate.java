package org.troy.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CodeGenerate {

    private static Connection conn;

    private static String     classPath;

    public static void main(String[] args) {
        //        getJAVANameFromTableName("platform_item_rela");
        generateCode();
    }

    public static void generateCode() {
        try {

            classPath = CodeGenerate.class.getClassLoader().getResource("").getPath();
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/platform?useUnicode=true&amp;characterEncoding=utf8";

            conn = DriverManager.getConnection(url, "root", "");
            DatabaseMetaData data = conn.getMetaData();

            ResultSet rest = data.getTables("platform", null, null, new String[] { "TABLE" });
            List<String> tableList = new ArrayList<String>();
            while (rest.next()) {
                String tableName = rest.getString("TABLE_NAME");
                if (!tableName.toLowerCase().startsWith("platform")) {
                    continue;
                }
                tableList.add(tableName);
            }

            //开始根据表结构来进行代码的生成
            for (String tableName : tableList) {
                createTableCode(tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据表明来生成代码
     * 
     * @param tableName  
     * @throws SQLException 
     */
    public static void createTableCode(String tableName) throws SQLException, IOException {
        PreparedStatement stateMent = conn.prepareStatement("select * from " + tableName);
        ResultSetMetaData data = stateMent.executeQuery().getMetaData();
        int count = data.getColumnCount();
        for (int i = 1; i <= count; i++) {
            createEntity(data, tableName);
            createDAO(data, tableName);
            createService(data, tableName);
            createServiceImpl(data, tableName);
            createController(data, tableName);
            createJSPFile(data, tableName);
        }
    }

    /**
     * 创建尸体对象
     * 
     * @param data
     * @param tableName
     * @throws SQLException 
     */
    public static void createEntity(ResultSetMetaData data, String tableName) throws SQLException {
        StringBuffer sb = new StringBuffer(
            "package org.troy.platform.entity;\nimport java.util.Date;\nimport javax.persistence.Column;\nimport javax.persistence.Entity;\nimport javax.persistence.Table;\nimport org.troy.system.entity.IdEntity;")
            .append("\n\n");
        sb.append("@Entity").append("\n");
        sb.append("@Table(name = \"" + tableName + "\")").append("\n");
        sb.append("public class " + getJAVANameFromTableName(tableName) + " extends IdEntity {")
            .append("\n");
        sb.append(
            "    private static final long serialVersionUID = "
                    + UUID.randomUUID().getLeastSignificantBits() + "L;").append("\n\n");

        int count = data.getColumnCount();
        for (int i = 1; i <= count; i++) {
            if ("id".equals(data.getColumnName(i))) {
                continue;
            }
            sb.append("    @Column(length = " + data.getColumnDisplaySize(i) + ")").append("\n");
            sb.append(
                "    private " + getSimpleClassName(data.getColumnClassName(i)) + "    "
                        + getJAVAParamName(data.getColumnName(i)) + ";").append("\n\n");
        }

        for (int i = 1; i <= count; i++) {

            //生成get方法
            String paramName = getJAVAParamName(data.getColumnName(i));

            if ("id".equals(paramName)) {
                continue;
            }
            sb.append(
                "    public String get" + getJAVANameFromTableName(data.getColumnName(i)) + "() {")
                .append("\n").append("        return " + paramName + ";").append("\n")
                .append("    }").append("\n\n");

            //生成set方法
            sb.append(
                "    public void set" + getJAVANameFromTableName(data.getColumnName(i)) + "("
                        + getSimpleClassName(data.getColumnClassName(i) + " " + paramName + ") {"))
                .append("\n").append("        this." + paramName + " = " + paramName + ";")
                .append("\n").append("    }").append("\n\n");
        }
        sb.append("}");
        createCodeFile(sb.toString(), classPath + File.separator
                                      + getJAVANameFromTableName(tableName) + ".java");
    }

    /**
     * 创建DAO
     * 
     * @param data
     * @param tableName
     */
    public static void createDAO(ResultSetMetaData data, String tableName) {
        StringBuffer sb = new StringBuffer("package org.troy.platform.dao;").append("\n\n");
        sb.append("import org.springframework.data.jpa.repository.JpaRepository;").append("\n");
        String javaName = getJAVANameFromTableName(tableName);
        sb.append("import org.troy.platform.entity." + javaName).append("\n\n");
        sb.append(
            "public interface " + javaName + "Dao extends JpaRepository<" + javaName + ", Long> {")
            .append("\n\n");

        sb.append("    " + javaName + " findById(Long id);").append("\n\n");
        sb.append("    List<" + javaName + "> findAll();").append("\n\n");
        sb.append("}");
        createCodeFile(sb.toString(), classPath + File.separator + javaName + "Dao.java");
    }

    /**
     * 创建Service
     * 
     * @param data
     * @param tableName
     */
    public static void createService(ResultSetMetaData data, String tableName) {

        String javaTableName = getJAVANameFromTableName(tableName);
        StringBuffer sb = new StringBuffer("package org.troy.platform.service;").append("\n");
        sb.append("import java.util.List;").append("\n");
        sb.append("import org.troy.platform.entity." + javaTableName + ";").append("\n");
        sb.append("import org.troy.common.component.jdbc.BaseJdbcService;").append("\n");
        sb.append("import org.troy.common.exception.ServiceException;").append("\n\n");

        sb.append("public interface " + javaTableName + "Service extends BaseJdbcService {")
            .append("\n\n");
        sb.append("    " + javaTableName + " findById(Long id)  throws ServiceException;").append(
            "\n\n");
        sb.append("    List<" + javaTableName + "> findAll() throws ServiceException;").append(
            "\n\n");
        sb.append(
            "    " + javaTableName + " update(" + javaTableName + " " + getJAVAParamName(tableName)
                    + ") throws ServiceException;").append("\n\n");
        sb.append(
            "    " + javaTableName + " save(" + javaTableName + " " + getJAVAParamName(tableName)
                    + ") throws ServiceException;").append("\n\n");

        sb.append("}").append("\n");
        createCodeFile(sb.toString(), classPath + File.separator + javaTableName + "Service.java");
    }

    /**
     * 创建Service实现文件
     * 
     * @param data
     * @param tableName
     */
    public static void createServiceImpl(ResultSetMetaData data, String tableName) {
        String javaTableName = getJAVANameFromTableName(tableName);
        String paramName = getJAVAParamName(tableName);
        StringBuffer sb = new StringBuffer("package org.troy.platform.service.impl;")
            .append("\n\n");
        sb.append("import java.util.List;").append("\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;").append("\n");
        sb.append("import org.springframework.stereotype.Service;").append("\n");
        sb.append("import org.troy.platform.dao." + javaTableName + "Dao;").append("\n");
        sb.append("import org.troy.platform.entity." + javaTableName + ";").append("\n");
        sb.append("import org.troy.platform.service." + javaTableName + "Service;").append("\n");
        sb.append("import org.troy.common.component.jdbc.BaseJdbcServiceImpl;").append("\n");
        sb.append("import org.troy.common.exception.ServiceException;").append("\n\n");
        sb.append("@Service").append("\n");
        sb.append(
            "public class " + javaTableName + "ServiceImpl extends BaseJdbcServiceImpl implements "
                    + javaTableName + "Service {").append("\n\n");
        sb.append("    @Autowired").append("\n");
        sb.append("    private " + javaTableName + "Dao " + paramName + "Dao;").append("\n\n");
        sb.append("    @Override").append("\n");
        sb.append(
            "    public " + javaTableName + " update(" + javaTableName + " " + paramName
                    + ") throws ServiceException {").append("\n");
        sb.append("       return  " + paramName + "Dao.save(" + paramName + ");").append("\n");
        sb.append("    }").append("\n\n");

        sb.append("    @Override").append("\n");
        sb.append(
            "    public javaTableName save(" + javaTableName + " " + paramName
                    + ") throws ServiceException {").append("\n");
        sb.append("        return " + paramName + "Dao.save(" + paramName + ");").append("\n");
        sb.append("    }").append("\n\n");

        sb.append("    @Override").append("\n");
        sb.append("    public " + javaTableName + " findById(Long id) throws ServiceException {")
            .append("\n");
        sb.append("        return " + paramName + "Dao.findOne(id);").append("\n");
        sb.append("    }").append("\n\n");

        sb.append("    @Override").append("\n");
        sb.append("    public List<" + javaTableName + "> findAll() throws ServiceException {")
            .append("\n");
        sb.append("        return " + paramName + "Dao.findAll();").append("\n");
        sb.append("    }").append("\n\n");
        sb.append("    }");

        createCodeFile(sb.toString(), classPath + File.separator + javaTableName
                                      + "ServiceImpl.java");
    }

    /**
     * 创建Controller文件
     * 
     * @param data
     * @param tableName
     */
    public static void createController(ResultSetMetaData data, String tableName) {
        StringBuffer sb = new StringBuffer("package org.troy.platform.controller;").append("\n\n");

        String javaTableName = getJAVANameFromTableName(tableName);
        String paramName = getJAVAParamName(tableName);
        sb.append("import java.util.Date;").append("\n");
        sb.append("import java.util.List;").append("\n");
        sb.append("import java.util.Map;").append("\n");
        sb.append("import javax.servlet.http.HttpServletRequest;").append("\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;").append("\n");
        sb.append("import org.springframework.stereotype.Controller;").append("\n");
        sb.append("import org.springframework.web.bind.annotation.RequestMapping;").append("\n");
        sb.append("import org.springframework.web.bind.annotation.RequestMethod;").append("\n");
        sb.append("import org.springframework.web.bind.annotation.ResponseBody;").append("\n");
        sb.append("import org.troy.system.controller.ViewController;").append("\n");
        sb.append("import org.troy.platform.entity." + javaTableName + ";").append("\n");
        sb.append("import org.troy.platform.service." + javaTableName + "Service;").append("\n");
        sb.append("import org.troy.common.utils.DateUtil;").append("\n");
        sb.append("import org.troy.common.utils.dwz.AjaxObject;").append("\n");
        sb.append("import org.troy.common.utils.dwz.Page;").append("\n\n\n");
        sb.append("@Controller").append("\n");
        sb.append("@RequestMapping(\"/platform/backstage/" + paramName + "\")").append("\n");
        sb.append("public class " + javaTableName + "Controller extends ViewController {").append(
            "\n\n");
        sb.append("    @Autowired").append("\n");
        sb.append("    private " + javaTableName + "Service " + paramName + "Service;").append(
            "\n\n");
        sb.append(
            "    private static final String       LIST      = \"platform/backstage/" + paramName
                    + "/list\";").append("\n\n");
        sb.append(
            "    private static final String       CREATE      = \"platform/backstage/" + paramName
                    + "/create\";").append("\n");
        sb.append(
            "    private static final String       DETAIL      = \"platform/backstage/" + paramName
                    + "/detail\";").append("\n");
        sb.append(
            "    private static final String       UPDATE      = \"platform/backstage/" + paramName
                    + "/update\";").append("\n\n");
        sb.append("    @RequestMapping(value = \"/create\", method = RequestMethod.GET)").append(
            "\n");
        sb.append("    public String preCreate(Map<String, Object> map) {").append("\n");
        sb.append("        return CREATE;").append("\n");
        sb.append("    }").append("\n\n");

        sb.append("    @RequestMapping(value = \"/create\", method = RequestMethod.POST)").append(
            "\n");
        sb.append(
            "    public @ResponseBody String create(" + javaTableName + " " + paramName + ") {")
            .append("\n");
        sb.append("        evenName = \"添加\";").append("\n");
        sb.append("        AjaxObject ajaxObject = new AjaxObject(evenName + \"成功！\");").append(
            "\n");
        sb.append("        " + paramName + "Service.save(" + paramName + ");").append("\n");
        sb.append("        return ajaxObject.toString();").append("\n");
        sb.append("    }").append("\n\n");

        sb.append("    @RequestMapping(value = \"/update/{id}\", method = RequestMethod.GET)")
            .append("\n");
        sb.append("    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {")
            .append("\n");
        sb.append(
            "        " + javaTableName + " " + paramName + " = " + paramName
                    + "Service.findById(id);").append("\n");
        sb.append("            map.put(\"obj\",\"" + paramName + "\")").append("\n");
        sb.append("        return UPDATE;").append("\n");
        sb.append("    }").append("\n\n");

        sb.append("    @RequestMapping(value = \"/update\", method = RequestMethod.POST)").append(
            "\n");
        sb.append(
            "    public @ResponseBody String update(" + javaTableName + " " + paramName
                    + ", HttpServletRequest request) {").append("\n");
        sb.append("        evenName = \"修改\";").append("\n");
        sb.append("        AjaxObject ajaxObject = new AjaxObject(evenName + \"成功！\");").append(
            "\n");
        sb.append("        long id = " + paramName + ".getId();").append("\n");
        sb.append("        " + javaTableName + " obj = " + paramName + "Service.findById(id);")
            .append("\n");
        sb.append("        " + paramName + "Service.update(obj);").append("\n");
        sb.append("        return ajaxObject.toString();").append("\n");
        sb.append("    }").append("\n\n");

        sb.append(
            "    @RequestMapping(value = \"/list\", method = { RequestMethod.GET, RequestMethod.POST })")
            .append("\n");
        sb.append("    public String list(Page page, Map<String, Object> map) {").append("\n");
        sb.append(
            "        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();")
            .append("\n");
        sb.append("        map.put(\"page\", page);").append("\n");
        sb.append("        map.put(\"data\", mapList);").append("\n");
        sb.append("        return LIST;").append("\n");
        sb.append("    }").append("\n\n");

        sb.append("}");
        createCodeFile(sb.toString(), classPath + File.separator + javaTableName
                                      + "Controller.java");
    }

    /**
     * 创建JSP文件
     * 
     * @param data
     * @param tableName
     * @throws SQLException 
     */
    public static void createJSPFile(ResultSetMetaData data, String tableName) throws SQLException {

        //列表界面
        listJSP(data, tableName);

        //添加界面
        addJSP(data, tableName);

        //更新界面
        updateJSP(data, tableName);

        //删除 一般不需要
    }

    /**
     * 创建列表界面
     * 
     * @param data
     * @param tableName
     * @throws SQLException 
     */
    public static void listJSP(ResultSetMetaData data, String tableName) throws SQLException {
        String paramName = getJAVAParamName(tableName);

        StringBuffer sb = new StringBuffer("<%@page import=\"java.util.Date\"%>").append("\n");
        sb.append(
            "<%@ page contentType=\"text/html;charset=UTF-8\" pageEncoding=\"UTF-8\" trimDirectiveWhitespaces=\"true\"%>")
            .append("\n");
        sb.append("<%@ include file=\"/WEB-INF/views/include.inc.jsp\"%>").append("\n");
        sb.append(
            "<dwz:paginationForm action=\"${ contextPath}/platform/backstage/" + paramName
                    + "/list\" page=\"${ page}\">").append("\n");
        sb.append(" <input type=\"hidden\" name=\"name\" value=\"${" + paramName + ".id}\"/>")
            .append("\n");
        sb.append("</dwz:paginationForm>").append("\n");
        sb.append(
            "<form method=\"post\" action=\"${ contextPath }/platform/backstage/" + paramName
                    + "\"/list\" onsubmit=\"return navTabSearch(this)\">").append("\n");
        sb.append(" <div class=\"pageHeader\">").append("\n");
        sb.append("     <div class=\"searchBar\">").append("\n");
        sb.append("         <ul class=\"searchContent\">").append("\n");
        sb.append("             <li>").append("\n");
        sb.append("                 <label>名称：</label>").append("\n");
        sb.append(
            "                 <input type=\"text\" name=\"name\" value=\"${ " + paramName
                    + ".id}\"/>").append("\n");
        sb.append("             </li>").append("\n");
        sb.append("         </ul>").append("\n");
        sb.append("         <div class=\"subBar\">").append("\n");
        sb.append("             <ul>").append("\n");
        sb.append(
            "                 <li><div class=\"button\"><div class=\"buttonContent\"><button type=\"submit\">搜索</button></div></div></li>")
            .append("\n");
        sb.append("             </ul>").append("\n");
        sb.append("         </div>").append("\n");
        sb.append("     </div>").append("\n");
        sb.append(" </div>").append("\n");
        sb.append("</form>").append("\n");
        sb.append("<div class=\"pageContent\">").append("\n");
        sb.append(" <div class=\"panelBar\">").append("\n");
        sb.append("     <ul class=\"toolBar\">").append("\n");
        sb.append(
            "         <li><a class=\"add\" target=\"dialog\" mask=\"true\" width=\"530\" height=\"600\" href=\"${ contextPath }/platform/backstage/"
                    + paramName + "/create\"><span>添加</span></a></li>").append("\n");
        sb.append(
            "         <li><a class=\"edit\" target=\"dialog\" mask=\"true\" width=\"530\" height=\"400\" href=\"${ contextPath }/platform/backstage/"
                    + paramName + "/update/{slt_uid}\"><span>编辑</span></a></li>").append("\n");
        sb.append(
            "         <li><a class=\"delete\" target=\"selectedTodo\"  href=\"${ contextPath }/platform/backstage/"
                    + paramName
                    + "/delete\" title=\"确认要删除所有的交易记录?\"><span>删除所有交易记录</span></a></li>").append(
            "\n");
        sb.append("     </ul></div>").append("\n");
        sb.append(" <table class=\"table\" layoutH=\"137\" width=\"100%\">").append("\n");
        sb.append("     <thead>").append("\n");
        sb.append("         <tr>").append("\n");
        sb.append(
            "         <th width=\"22\"><input type=\"checkbox\" group=\"ids\" class=\"checkboxCtrl\"></th>")
            .append("\n");

        int count = data.getColumnCount();
        for (int i = 1; i <= count; i++) {
            sb.append("                      <th>字段</th>").append("\n");
        }

        sb.append("                     </tr>").append("\n");
        sb.append("     </thead>").append("\n");
        sb.append("     <tbody>").append("\n");
        sb.append("         <c:forEach var=\"item\" items=\"${ data }\">").append("\n");
        sb.append("         <tr target=\"slt_uid\" rel=\"${item.id}\">").append("\n");
        sb.append("          <td><input name=\"ids\" value=\"${item.id}\" type=\"checkbox\"></td>")
            .append("\n");

        for (int i = 1; i <= count; i++) {
            sb.append(
                "                           <td>${ item." + getJAVAParamName(data.getColumnName(i))
                        + " }</td>").append("\n");
        }

        sb.append("                     </tr>").append("\n");
        sb.append("         </c:forEach>").append("\n");
        sb.append("     </tbody>").append("\n");
        sb.append(" </table>").append("\n");
        sb.append(" <dwz:pagination page=\"${page}\"/>").append("\n");
        sb.append("</div>").append("\n");

        createCodeFile(sb.toString(), classPath + File.separator + paramName + File.separator
                                      + "list.jsp");
    }

    /**
     * 创建添加界面
     * 
     * @param data
     * @param tableName
     * @throws SQLException 
     */
    public static void addJSP(ResultSetMetaData data, String tableName) throws SQLException {
        String paramName = getJAVAParamName(tableName);
        StringBuffer sb = new StringBuffer(
            "<%@ page contentType=\"text/html;charset=UTF-8\" pageEncoding=\"UTF-8\" trimDirectiveWhitespaces=\"true\"%>")
            .append("\n");
        sb.append("<%@ include file=\"/WEB-INF/views/include.inc.jsp\"%>").append("\n");
        sb.append("<div class=\"pageContent\">").append("\n");
        sb.append(
            "<form method=\"post\" action=\"${ contextPath }/platform/backstage/"
                    + paramName
                    + "/create\" class=\"required-validate pageForm\" onsubmit=\"return validateCallback(this, dialogReloadNavTab);\">")
            .append("\n");
        sb.append(" <div class=\"pageFormContent\" layoutH=\"97\">").append("\n");

        int count = data.getColumnCount();
        for (int i = 1; i <= count; i++) {
            sb.append("              <dl>").append("\n");
            sb.append("         <dt>字段名：</dt>").append("\n");
            sb.append("         <dd>").append("\n");
            sb.append(
                "             <input type=\"text\" name=\""
                        + getJAVAParamName(data.getColumnName(i))
                        + "\"  size=\"20\" maxlength=\"30\" alt=\"请输入字段名\"/>").append("\n");
            sb.append("          </dd>").append("\n");
            sb.append("       </dl>").append("\n");
        }
        sb.append(" </div>").append("\n");
        sb.append(" <div class=\"formBar\">").append("\n");
        sb.append("     <ul>").append("\n");
        sb.append(
            "         <li><div class=\"button\"><div class=\"buttonContent\"><button type=\"submit\">确定</button></div></div></li>")
            .append("\n");
        sb.append(
            "         <li><div class=\"button\"><div class=\"buttonContent\"><button type=\"button\" class=\"close\">关闭</button></div></div></li>")
            .append("\n");
        sb.append("     </ul>").append("\n");
        sb.append(" </div>").append("\n");
        sb.append("</form>").append("\n");
        sb.append("</div>").append("\n");
        createCodeFile(sb.toString(), classPath + File.separator + paramName + File.separator
                                      + "create.jsp");
    }

    /**
     * 创建更新界面
     * 
     * @param data
     * @param tableName
     * @throws SQLException 
     */
    public static void updateJSP(ResultSetMetaData data, String tableName) throws SQLException {
        String paramName = getJAVAParamName(tableName);
        StringBuffer sb = new StringBuffer(
            "<%@ page contentType=\"text/html;charset=UTF-8\" pageEncoding=\"UTF-8\" trimDirectiveWhitespaces=\"true\"%>")
            .append("\n");
        sb.append("<%@ include file=\"/WEB-INF/views/include.inc.jsp\"%>").append("\n");
        sb.append("<div class=\"pageContent\">").append("\n");
        sb.append(
            "<form method=\"post\" action=\"${ contextPath }/platform/backstage/"
                    + paramName
                    + "/update\" class=\"required-validate pageForm\" onsubmit=\"return validateCallback(this, dialogReloadNavTab);\">")
            .append("\n");
        sb.append(" <div class=\"pageFormContent\" layoutH=\"97\">").append("\n");

        int count = data.getColumnCount();
        for (int i = 1; i <= count; i++) {
            sb.append("              <dl>").append("\n");
            sb.append("         <dt>字段名：</dt>").append("\n");
            sb.append("         <dd>").append("\n");
            sb.append(
                "             <input type=\"text\" name=\""
                        + getJAVAParamName(data.getColumnName(i)) + "\" value=\"${obj."
                        + getJAVAParamName(data.getColumnName(i)) + "}\""
                        + "  size=\"20\" maxlength=\"30\" alt=\"请输入字段名\"/>").append("\n");
            sb.append("          </dd>").append("\n");
            sb.append("       </dl>").append("\n");
        }
        sb.append(" </div>").append("\n");
        sb.append(" <div class=\"formBar\">").append("\n");
        sb.append("     <ul>").append("\n");
        sb.append(
            "         <li><div class=\"button\"><div class=\"buttonContent\"><button type=\"submit\">确定</button></div></div></li>")
            .append("\n");
        sb.append(
            "         <li><div class=\"button\"><div class=\"buttonContent\"><button type=\"button\" class=\"close\">关闭</button></div></div></li>")
            .append("\n");
        sb.append("     </ul>").append("\n");
        sb.append(" </div>").append("\n");
        sb.append("</form>").append("\n");
        sb.append("</div>").append("\n");
        createCodeFile(sb.toString(), classPath + File.separator + paramName + File.separator
                                      + "update.jsp");
    }

    public static String getSimpleClassName(String className) {
        if (null == className) {
            return "";
        }

        int index = className.lastIndexOf(".");
        if (index == -1) {
            return "";
        }

        return className.substring(index + 1);
    }

    public static String getJAVAParamName(String columeName) {
        StringBuffer sb = new StringBuffer();
        String[] names = columeName.split("_");
        boolean isFirst = true;
        for (String name : names) {
            sb.append((isFirst ? name.substring(0, 1).toLowerCase() : name.substring(0, 1)
                .toUpperCase()) + name.substring(1));
            isFirst = false;
        }
        return sb.toString();
    }

    public static String getJAVANameFromTableName(String tableName) {
        StringBuffer sb = new StringBuffer();
        String[] names = tableName.split("_");
        for (String name : names) {
            sb.append(name.substring(0, 1).toUpperCase() + name.substring(1));
        }
        return sb.toString();
    }

    /**
     * 开始创建文件
     * 
     * @param content   文件内容
     * @param filePath  文件路径
     * @throws IOException 
     */
    public static void createCodeFile(String content, String filePath) {

        File file = new File(filePath);
        //如果文件不存在的情况下，开始创建
        if (!file.exists()) {
            FileOutputStream output = null;
            try {
                File parent = new File(file.getParent());
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                file.createNewFile();
                output = new FileOutputStream(file);
                output.write(content.getBytes("UTF-8"));
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("文件已经生成，路径：" + filePath);
    }

}
