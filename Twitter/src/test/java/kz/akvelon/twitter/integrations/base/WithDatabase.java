package kz.akvelon.twitter.integrations.base;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.springframework.test.context.jdbc.Sql;

@AutoConfigureEmbeddedDatabase(
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES,
        beanName = "dataSource",
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        refresh = AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"classpath:test-data.sql"})

public class WithDatabase extends BaseIntegrationTest {
}
