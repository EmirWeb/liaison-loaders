package mobi.liaison.loaders.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Set;

import mobi.liaison.loaders.Path;
import mobi.liaison.loaders.database.annotations.PrimaryKey;
import mobi.liaison.loaders.database.annotations.TableColumn;
import mobi.liaison.loaders.database.annotations.TableColumns;
import mobi.liaison.loaders.database.annotations.TablePath;
import mobi.liaison.loaders.database.annotations.TablePaths;
import mobi.liaison.loaders.database.annotations.Unique;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


@RunWith(AndroidJUnit4.class)
public class ContentTableTest {

    public final Context mContext = InstrumentationRegistry.getContext();

    @Test
    public void getCreateWithOneParameter_returnsCorrectSqlTableCreationAndDrop(){
        final MockModel mockModel = new MockModel("EMIR", new Path("PATH1", "PATH2"));
        final Column modelColumn = new Column("CONTENT_NAME", "COLUMN_NAME", Column.Type.text);
        mockModel.setModelColums(modelColumn);

        final String sqlCreateQuery = mockModel.getCreate(mContext);
        assertThat(sqlCreateQuery, equalTo("CREATE TABLE IF NOT EXISTS EMIR ( COLUMN_NAME TEXT );"));

        final String sqlDropQuery = mockModel.getDrop(mContext);
        assertThat(sqlDropQuery, equalTo("DROP TABLE IF EXISTS EMIR;"));
    }

//    @Test
//    public void getCreateWithOneParameterAndUnique_returnsCorrectSqlTableCreationAndDrop(){
//        final MockModel mockModel = new MockModel("EMIR", new Path("PATH1", "PATH2"));
//        final ModelColumn modelColumn = new ModelColumn("CONTENT_NAME", "COLUMN_NAME", Column.Type.text);
//        mockModel.setModelColums(modelColumn);
//        mockModel.setUniqueModelColums(modelColumn);
//
//        final String sqlCreateQuery = mockModel.getCreate(mContext);
//        assertThat(sqlCreateQuery).isEqualTo("CREATE TABLE IF NOT EXISTS EMIR ( COLUMN_NAME TEXT, UNIQUE ( COLUMN_NAME ) ON CONFLICT REPLACE );");
//
//        final String sqlDropQuery = mockModel.getDrop(mContext);
//        assertThat(sqlDropQuery).isEqualTo("DROP TABLE IF EXISTS EMIR;");
//    }
//
//    @Test
//    public void getCreateWithMultipleParameters_returnsCorrectSqlTableCreationAndDrop(){
//        final MockModel mockModel = new MockModel("EMIR", new Path("PATH1", "PATH2"));
//        final ModelColumn modelColumn = new ModelColumn("CONTENT_NAME", "COLUMN_NAME", Column.Type.text);
//        final ModelColumn modelColumn1 = new ModelColumn("CONTENT_NAME1", "COLUMN_NAME1", Column.Type.integer);
//        mockModel.setModelColums(modelColumn, modelColumn1);
//
//        final String sqlCreateQuery = mockModel.getCreate(mContext);
//        assertThat(sqlCreateQuery).isEqualTo("CREATE TABLE IF NOT EXISTS EMIR ( COLUMN_NAME TEXT, COLUMN_NAME1 INTEGER );");
//
//        final String sqlDropQuery = mockModel.getDrop(mContext);
//        assertThat(sqlDropQuery).isEqualTo("DROP TABLE IF EXISTS EMIR;");
//    }
//
//
//    @Test
//    public void createShouldUseUniquesPrimaryKeyAndColumns(){
//        final MockAnnotationModel mockAnnotationModel = new MockAnnotationModel();
//        final String sqlCreateQuery = mockAnnotationModel.getCreate(mContext);
//        assertThat(sqlCreateQuery).isEqualTo("CREATE TABLE IF NOT EXISTS MockAnnotationModel " +
//                "(" +
//                    " MODEL_COLUMN_1 TEXT," +
//                    " MODEL_COLUMN_2 TEXT," +
//                    " MODEL_COLUMN_3 TEXT," +
//                    " UNIQUE ( MODEL_COLUMN_2 ) ON CONFLICT REPLACE," +
//                    " PRIMARY KEY ( MODEL_COLUMN_3 ) " +
//                ");");
//    }
//
//    @Test
//    public void createShouldUseForeignKeyAndColumns(){
//        final MockForeignKeyAnnotationModel mockForeignKeyAnnotationModel = new MockForeignKeyAnnotationModel();
//        final String sqlCreateQuery = mockForeignKeyAnnotationModel.getCreate(mContext);
//        assertThat(sqlCreateQuery).isEqualTo("CREATE TABLE IF NOT EXISTS MockForeignKeyAnnotationModel " +
//                "(" +
//                " MODEL_COLUMN_1 TEXT," +
//                " MODEL_COLUMN_2 TEXT," +
//                " FOREIGN KEY ( MODEL_COLUMN_1, MODEL_COLUMN_2 ) REFERENCES MockAnnotationModel( MODEL_COLUMN_1, MODEL_COLUMN_2 ) ON DELETE CASCADE ON UPDATE CASCADE " +
//                ");");
//    }
//
//    @Test
//    public void createThrowErrorForBadForeignKeyAndColumns(){
//        final MockBadForeignKeyAnnotationModel mockBadForeignKeyAnnotationModel = new MockBadForeignKeyAnnotationModel();
//        try {
//            final String sqlCreateQuery = mockBadForeignKeyAnnotationModel.getCreate(mContext);
//            fail("Expected Exception");
//        } catch (final Exception exception){
//
//        }
//
//    }
//
//
//    @Test
//    public void annotationsShouldBuildGetColumnsAndGetUniquesAndPathsAndGetId(){
//        final MockAnnotationModel mockAnnotationModel = new MockAnnotationModel();
//
//        final List<Column> columns = mockAnnotationModel.getColumns(mContext);
//        assertThat(columns).hasSize(3);
//
//        final Set<Column> uniqueColumns = mockAnnotationModel.getUniqueColumns(mContext);
//        assertThat(uniqueColumns).hasSize(1);
//
//        final List<Path> paths = mockAnnotationModel.getPaths(mContext);
//        assertThat(paths).hasSize(1);
//
//    }

    public static class MockModel extends  TableContent {

        public String mName;
        public List<Path> mPathSegments;
        private List<Column> mModelColums;
        private Set<Column> mUniqueModelColums;

        public MockModel(String name, Path... pathSegments) {
            mName = name;
            mPathSegments = Lists.newArrayList(pathSegments);
        }

        public void setModelColums(Column... modelColums){
            mModelColums = Lists.newArrayList(modelColums);
        }

        public void setUniqueModelColums(Column... modelColums){
            mUniqueModelColums = Sets.newHashSet(modelColums);
        }

        @Override
        public String getName(Context context) {
            return mName;
        }

        @Override
        public List<Path> getPaths(Context context) {
            return mPathSegments;
        }

        @Override
        public List<Column> getColumns(Context context) {
            return mModelColums;
        }

        @Override
        public Set<Column> getUniqueColumns(Context context) {
            return mUniqueModelColums;
        }
    }

    public static class MockAnnotationModel extends  TableContent {

        public static final String NAME = MockAnnotationModel.class.getSimpleName();

        @Override
        public String getName(Context context) {
            return NAME;
        }

        @TableColumns
        public static class Columns {
            @TableColumn
            public static final Column MODEL_COLUMN_1 = new Column(NAME, "MODEL_COLUMN_1", Column.Type.text);

            @Unique
            @TableColumn
            public static final Column MODEL_COLUMN_2 = new Column(NAME, "MODEL_COLUMN_2", Column.Type.text);

            @PrimaryKey
            @TableColumn
            public static final Column MODEL_COLUMN_3 = new Column(NAME, "MODEL_COLUMN_3", Column.Type.text);
        }

        @TablePaths
        public static class Paths {
            @TablePath
            public static final Path PATH = new Path(NAME);
        }

    }

    public static class MockForeignKeyAnnotationModel extends  TableContent {

        public static final String NAME = MockForeignKeyAnnotationModel.class.getSimpleName();

        @Override
        public String getName(Context context) {
            return NAME;
        }

        @TableColumns
        public static class Columns {
            @TableColumn
            public static final ForeignKeyColumn MODEL_COLUMN_1 = new ForeignKeyColumn(NAME, MockAnnotationModel.Columns.MODEL_COLUMN_1);
            @TableColumn
            public static final ForeignKeyColumn MODEL_COLUMN_2 = new ForeignKeyColumn(NAME, MockAnnotationModel.Columns.MODEL_COLUMN_2);
        }

        @TablePaths
        public static class Paths {
            @TablePath
            public static final Path PATH = new Path(NAME);
        }

    }

    public static class MockBadForeignKeyAnnotationModel extends  TableContent {

        public static final String NAME = MockForeignKeyAnnotationModel.class.getSimpleName();

        @Override
        public String getName(Context context) {
            return NAME;
        }

        @TableColumns
        public static class Columns {
            @TableColumn
            public static final ForeignKeyColumn MODEL_COLUMN_1 = new ForeignKeyColumn(NAME, MockAnnotationModel.Columns.MODEL_COLUMN_1);
            @TableColumn
            public static final ForeignKeyColumn MODEL_COLUMN_2 = new ForeignKeyColumn(NAME, MockForeignKeyAnnotationModel.Columns.MODEL_COLUMN_1);
        }

        @TablePaths
        public static class Paths {
            @TablePath
            public static final Path PATH = new Path(NAME);
        }

    }

}