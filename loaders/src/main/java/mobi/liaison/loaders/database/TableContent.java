package mobi.liaison.loaders.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mobi.liaison.loaders.Content;
import mobi.liaison.loaders.Path;

/**
 * Created by Emir Hasanbegovic on 13/05/14.
 */
public abstract class TableContent extends Content {

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS %s ( %s );";
    private static final String DROP = "DROP TABLE IF EXISTS %s;";
    private static final int VERSION = -1;
    private static final String UNIQUE = ", UNIQUE ( %s ) ON CONFLICT REPLACE";
    private static final String FOREIGN = ", FOREIGN KEY ( %s ) REFERENCES %s( %s ) ON DELETE CASCADE ON UPDATE CASCADE";
    private static final String PRIMARY_KEYS = ", PRIMARY KEY ( %s )";
    private static final String FOREIGN_KEY_ERROR = "Only allowed to reference one table for foreign keys.";;
    private final List<Column> mColumns = new ArrayList<Column>();
    private final Set<Column> mUniqueColumns = new HashSet<Column>();
    private final List<Path> mPaths = new ArrayList<Path>();
    private final Set<Column> mPrimaryKeys = new HashSet<Column>();

    private List<Column> getOrderedUniqueColumns(final Context context){
        final Set<Column> uniqueColumns = getUniqueColumns(context);
        if (uniqueColumns == null){
            return null;
        }
        return new ArrayList<Column>(uniqueColumns);
    }

    private List<Column> getOrderedPrimaryKeyColumns(final Context context){
        final Set<Column> primaryKeyColumns = getPrimaryKeyColumns(context);
        if (primaryKeyColumns == null){
            return null;
        }
        return new ArrayList<Column>(primaryKeyColumns);
    }

    @Override
    public String getCreate(final Context context) {
        final String name = getName(context);
        final List<Column> columns = getColumns(context);
        final List<Column> uniqueColumns = getOrderedUniqueColumns(context);
        final List<Column> primaryKeyColumns = getOrderedPrimaryKeyColumns(context);
        final String createColumns = createColumns(columns, uniqueColumns, primaryKeyColumns);
        final String create = String.format(CREATE, name, createColumns);
        return create;
    }

    @Override
    public String getDrop(final Context context) {
        final String name = getName(context);
        final String drop = String.format(DROP, name);
        return drop;
    }

    public Set<Column> getUniqueColumns(final Context context) {
        return mUniqueColumns;
    }

    @Override
    public int getVersion(final Context context) {
        return VERSION;
    }

    public List<Column> getColumns(Context context) {
        return mColumns;
    }

    public static String createColumns(final List<Column> columns, final List<Column> uniqueColumns, final List<Column> primaryKeyColumns) {
        final StringBuilder stringBuilder = new StringBuilder();
        final int size = columns.size();
        for (int index = 0; index < size; index++ ){
            final Column column = columns.get(index);
            final String columnName = column.getName();
            final Column.Type type = column.getType();
            final String typeString = Column.Type.getSqlType(type);
            stringBuilder.append(columnName);
            stringBuilder.append(' ');
            stringBuilder.append(typeString);

            if (index != size -1) {
                stringBuilder.append(", ");
            }
        }
        final String uniqueLine = getUniqueLine(uniqueColumns);
        stringBuilder.append(uniqueLine);

        final String foreignKeyLine = getForeignKeyLine(new ArrayList<Column>(columns));
        stringBuilder.append(foreignKeyLine);

        final String primaryKeyLine = getPrimaryKeyLine(primaryKeyColumns);
        stringBuilder.append(primaryKeyLine);


        return stringBuilder.toString();

    }

    private static String getUniqueLine(final List<Column> uniqueColumns){
        if (uniqueColumns == null || uniqueColumns.isEmpty()){
            return "";
        }

        return String.format(UNIQUE, getCommaSeparatedColumns(uniqueColumns));
    }

    private static String getForeignKeyLine(final List<Column> columns){

        final List<ForeignKeyColumn> foreignKeyColumns = new ArrayList<ForeignKeyColumn>();
        for (final Column column : columns){
            if (column instanceof  ForeignKeyColumn){
                final ForeignKeyColumn foreignKeyColumn = (ForeignKeyColumn) column;
                foreignKeyColumns.add(foreignKeyColumn);
            }
        }

        if (foreignKeyColumns.isEmpty()){
            return "";
        }

        final String commaSeparatedForeignKeyModelColumns = getCommaSeparatedForeignKeyModelColumns(foreignKeyColumns);
        final String foreignKeySqlName = getForeignKeySqlName(foreignKeyColumns);
        return String.format(FOREIGN, commaSeparatedForeignKeyModelColumns, foreignKeySqlName, commaSeparatedForeignKeyModelColumns);
    }


    private static String getPrimaryKeyLine(final List<Column> primaryKeyColumns){
        if (primaryKeyColumns == null || primaryKeyColumns.isEmpty()){
            return "";
        }

        return String.format(PRIMARY_KEYS, getCommaSeparatedColumns(primaryKeyColumns));
    }

    private static String getForeignKeySqlName(final List<ForeignKeyColumn> foreignKeyModelColumns) {

        String sqlName = null;
        for (final ForeignKeyColumn foreignKeyColumn : foreignKeyModelColumns){
            final String foreignSqlName = foreignKeyColumn.getForeignSqlName();
            if (sqlName == null){
                sqlName = foreignSqlName;
            } else if (!sqlName.equals(foreignSqlName)){
                throw new IllegalArgumentException(FOREIGN_KEY_ERROR);
            }
        }

        return sqlName;
    }

    private static String getCommaSeparatedForeignKeyModelColumns(final List<ForeignKeyColumn> foreignKeyColumns) {
        final List<Column> columns = new ArrayList<Column>(foreignKeyColumns);
        return getCommaSeparatedColumns(columns);
    }

    private static String getCommaSeparatedColumns(final List<Column> columns) {
        final StringBuilder stringBuilder = new StringBuilder();
        final int size = columns.size();
        int index = 0;
        for(final Column modelColumn : columns){
            final String columnName = modelColumn.getName();
            stringBuilder.append(columnName);

            if (index != size -1) {
                stringBuilder.append(", ");
            }
            index++;
        }
        return stringBuilder.toString();
    }


    @Override
    public List<Path> getPaths(Context context) {
        return mPaths;
    }

    public Set<Column> getPrimaryKeyColumns(final Context context){
        return mPrimaryKeys;
    }
}
