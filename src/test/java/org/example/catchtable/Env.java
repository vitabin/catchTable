package org.example.catchtable;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;

public class Env {

    @BeforeAll
    static void loadEnv() {
        Dotenv env = Dotenv.load();
        System.setProperty("JWT_SECRET", env.get("JWT_SECRET"));
        System.setProperty("MYSQL_PASSWORD", env.get("MYSQL_PASSWORD"));
        System.setProperty("MYSQL_USER", env.get("MYSQL_USER"));
        System.setProperty("MYSQL_DATABASE", env.get("MYSQL_DATABASE"));
        System.setProperty("AWS_ACCESS_KEY", env.get("AWS_ACCESS_KEY"));
        System.setProperty("AWS_SECRET_KEY", env.get("AWS_SECRET_KEY"));
        System.setProperty("AWS_ENDPOINT", env.get("AWS_ENDPOINT"));
        System.setProperty("AWS_REGION", env.get("AWS_REGION"));
        System.setProperty("S3_BUCKET_NAME", env.get("S3_BUCKET_NAME"));
    }
}
