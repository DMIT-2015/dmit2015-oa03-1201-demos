package dmit2015.config;

import javax.enterprise.context.ApplicationScoped;
import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.faces.annotation.FacesConfig;

@DataSourceDefinitions({
	@DataSourceDefinition(
		name="java:app/datasources/h2databaseDS",
		className="org.h2.jdbcx.JdbcDataSource",
		url="jdbc:h2:file:~/courseassignments-singleuser-db",
		user="sa",
		password="sa"),
	
//	@DataSourceDefinition(
//		name="java:app/datasources/mssqlDS",
//		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
//		url="jdbc:sqlserver://localhost;databaseName=DMIT2015DB",
//		user="user2015",
//		password="Password2015"),
	
//	@DataSourceDefinition(
//		name="java:app/datasources/oracleDS",
//		className="oracle.jdbc.pool.OracleDataSource",
//		url="jdbc:oracle:thin:@192.168.101.201:11521/xepdb1",
//		user="user2015",
//		password="Password2015"),	
	
//	@DataSourceDefinition(
//		name="java:app/datasources/mysqlDS",
//		className="com.mysql.cj.jdbc.MysqlDataSource",
//		url="jdbc:mysql://192.168.101.201/DMIT2015DB",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/mariadbDS",
//		className="org.mariadb.jdbc.MariaDbDataSource",
//		url="jdbc:mariadb://192.168.101.129:13306/dmit2015DB",
//		user="user2015",
//		password="Password2015"),
	
//	@DataSourceDefinition(
//		name="java:app/datasources/postgresqlDS",
//		className="org.postgresql.ds.PGPoolingDataSource",
//		url="jdbc:postgresql://192.168.101.201/DMIT2015DB",
//		user="user2015",
//		password="Password2015"),
	
})

@FacesConfig
@ApplicationScoped
public class ApplicationConfig {

}
