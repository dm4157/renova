package party.msdg.renova.mock;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MockTest {

    public static void main(String[] args) {
        String sqlCreateTable = "create table t_gen_task\n" +
                "(\n" +
                "    id     int auto_increment primary key,\n" +
                "    name   varchar(200)                       not null comment '任务名称',\n" +
                "    tableId int                                not null comment '表id',\n" +
                "    num    int                                not null comment '生成数量',\n" +
                "    status int                                not null comment '状态，1-待执行，2-执行中，3-执行完成，4-执行失败',\n" +
                "    remark varchar(500)                       null comment '备注信息',\n" +
                "    ctime  datetime default current_timestamp not null comment '创建时间',\n" +
                "    mtime  datetime default current_timestamp not null on update current_timestamp comment '最新修改时间',\n" +
                "    cuser  int                                not null comment '创建人',\n" +
                "    muser  int                                not null comment '修改人' default 0\n" +
                ") comment '生成任务';";

        String packageName = "com.example.model";
        String outputDir = "./src/main/java/com/example/model/";

        try {
            generateJavaModel(sqlCreateTable, packageName, outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateJavaModel(String sqlCreateTable, String packageName, String outputDir) throws IOException {
        // Extract table name
        Pattern tableNamePattern = Pattern.compile("create table (\\w+)");
        Matcher tableNameMatcher = tableNamePattern.matcher(sqlCreateTable.toLowerCase());
        if (!tableNameMatcher.find()) {
            throw new IllegalArgumentException("Invalid SQL create table statement");
        }
        String tableName = tableNameMatcher.group(1);
        String className = toCamelCase(tableName, true);

        // Extract columns
        Pattern columnPattern = Pattern.compile("(\\w+)\\s+(\\w+)(?:\\(\\d+\\))?\\s+(not null)?\\s*comment\\s*'([^']+)'");
        Matcher columnMatcher = columnPattern.matcher(sqlCreateTable.toLowerCase());
        List<Column> columns = new ArrayList<>();
        while (columnMatcher.find()) {
            String columnName = columnMatcher.group(1);
            String columnType = columnMatcher.group(2);
            boolean isNotNull = columnMatcher.group(3) != null;
            String comment = columnMatcher.group(4);
            columns.add(new Column(columnName, columnType, isNotNull, comment));
        }

        // Generate Java class content
        StringBuilder javaClass = new StringBuilder();
        javaClass.append("package ").append(packageName).append(";\n\n");
        javaClass.append("import java.util.Date;\n\n");
        javaClass.append("public class ").append(className).append(" {\n");

        // Fields
        for (Column column : columns) {
            String fieldType = getJavaType(column.getType());
            javaClass.append("    private ").append(fieldType).append(" ").append(toCamelCase(column.getName(), false)).append(";\n");
        }

        // Getters and Setters
        for (Column column : columns) {
            String fieldName = toCamelCase(column.getName(), false);
            String fieldType = getJavaType(column.getType());
            String capitalizedFieldName = toCamelCase(column.getName(), true);

            // Getter
            javaClass.append("\n    public ").append(fieldType).append(" get").append(capitalizedFieldName).append("() {\n");
            javaClass.append("        return ").append(fieldName).append(";\n");
            javaClass.append("    }\n");

            // Setter
            javaClass.append("\n    public void set").append(capitalizedFieldName).append("(").append(fieldType).append(" ").append(fieldName).append(") {\n");
            javaClass.append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
            javaClass.append("    }\n");
        }

        javaClass.append("}\n");

//        // Write to file
//        String filePath = outputDir + className + ".java";
//        try (FileWriter fileWriter = new FileWriter(filePath)) {
//            fileWriter.write(javaClass.toString());
//        }
        System.out.println(javaClass.toString());
    }

    private static String toCamelCase(String input, boolean capitalizeFirstLetter) {
        StringBuilder result = new StringBuilder();
        boolean nextUpperCase = capitalizeFirstLetter;
        for (char c : input.toCharArray()) {
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    result.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        return result.toString();
    }

    private static String getJavaType(String sqlType) {
        switch (sqlType.toLowerCase()) {
            case "int":
            case "tinyint":
            case "smallint":
            case "mediumint":
            case "integer":
                return "Integer";
            case "bigint":
                return "Long";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "decimal":
            case "numeric":
                return "BigDecimal";
            case "varchar":
            case "char":
            case "text":
                return "String";
            case "datetime":
            case "timestamp":
                return "Date";
            case "boolean":
            case "tinyint(1)":
                return "Boolean";
            default:
                return "Object";
        }
    }

    private static class Column {
        private final String name;
        private final String type;
        private final boolean isNotNull;
        private final String comment;

        public Column(String name, String type, boolean isNotNull, String comment) {
            this.name = name;
            this.type = type;
            this.isNotNull = isNotNull;
            this.comment = comment;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public boolean isNotNull() {
            return isNotNull;
        }

        public String getComment() {
            return comment;
        }
    }
}