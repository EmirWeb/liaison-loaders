package mobi.liaison.loaders.database;

/**
 * Created by Emir Hasanbegovic on 12/05/14.
 */
public class ForeignKeyColumn extends Column{

    private final String mForeignSqlName;

    public ForeignKeyColumn(final String sqlName, final Column column) {
        super(sqlName, column.getName(), column.getType());
        mForeignSqlName = column.getSqlName();
    }

    public ForeignKeyColumn(final String sqlName, final String name, final Type type, final String stringType, final String foreignSqlName) {
        super(sqlName, name, type, stringType);
        mForeignSqlName = foreignSqlName;
    }

    public ForeignKeyColumn(final String tableName, final String name, final Type type, final String foreignSqlName) {
        super(tableName, name, type);
        mForeignSqlName = foreignSqlName;
    }

    public String getForeignSqlName(){
        return mForeignSqlName;
    }
}
