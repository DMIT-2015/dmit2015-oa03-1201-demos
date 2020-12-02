package dmit2015.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.enterprise.context.ApplicationScoped;

@DataSourceDefinitions({
	@DataSourceDefinition(
		name="java:app/datasources/h2databaseDS",
		className="org.h2.jdbcx.JdbcDataSource",
		url="jdbc:h2:file:~/dmit2015-batch-processing-demo-db",
		user="sa",
		password="sa"),

	@DataSourceDefinition(
		name="java:app/datasources/mssqlDS",
		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
		url="jdbc:sqlserver://192.168.101.130;databaseName=DMIT2015DB",
		user="user2015",
		password="Password2015"),
	
})

@ApplicationScoped
public class ApplicationConfig {

}
