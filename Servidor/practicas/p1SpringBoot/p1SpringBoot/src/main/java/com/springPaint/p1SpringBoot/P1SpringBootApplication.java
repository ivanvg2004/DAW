package com.springPaint.p1SpringBoot;

import com.mysql.cj.Session;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.interceptors.SessionAssociationInterceptor;
import com.springPaint.p1SpringBoot.interceptor.sessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@SpringBootApplication
@Configuration
public class P1SpringBootApplication implements WebMvcConfigurer {

    @Value("${spring.datasource.url}")
    String datasourceUrl;

	public static void main(String[] args) {
		SpringApplication.run(P1SpringBootApplication.class, args);
	}

    @Autowired
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Autowired
    sessionInterceptor interceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns(
                        "/private",
                        "/canvas/**",
                        "/dibuixos/**",
                        "/visualitzar/**",
                        "/compartir/**",
                        "/api/**",
                        "/esborrar",
                        "/restaurar",
                        "/eliminarDefinitiu",
                        "/clonar",
                        "/dejar-de-compartir"
                )
                .excludePathPatterns("/login", "/register", "/", "/css/**", "/js/**");
    }
}
