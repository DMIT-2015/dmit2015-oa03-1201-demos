package dmit2015.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.enterprise.context.ApplicationScoped;

@DataSourceDefinitions({
	@DataSourceDefinition(
		name="java:app/datasources/h2databaseDS",
		className="org.h2.jdbcx.JdbcDataSource",
		url="jdbc:h2:file:~/dmit2015-jaxs-demo-db",
		user="sa",
		password="sa"),
	
})

@ApplicationScoped
public class ApplicationConfig {

}
