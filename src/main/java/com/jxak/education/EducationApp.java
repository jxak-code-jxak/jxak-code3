package com.jxak.education;


import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Properties;

@MapperScan(basePackages = {"com.jxak.education.dao"})
@SpringBootApplication
@EnableTransactionManagement
public class EducationApp extends SpringBootServletInitializer implements WebMvcConfigurer {
    /**
     * 项目运行启动
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EducationApp.class, args);
    }

    /**
     * 构建war包
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        System.out.println("构建war包........");
        return super.configure(builder);
    }

    /**
     * 开启mybatis plus分页功能
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }

    /**
     * 打印mybatis plus sql语句
     * @return
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        //格式化sql语句
        Properties properties = new Properties();
        properties.setProperty("format", "false");
        performanceInterceptor.setProperties(properties);
        return performanceInterceptor;
    }

    /**
     * mybatis plus解决乐观锁,实体字段必须增加version字段
     * @return
     */
//    @Bean
//    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
//        return new OptimisticLockerInterceptor();
//    }
    /**
     * 视图请求错误自定义
     * @return
     */
    @Bean
    @SuppressWarnings("all")
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/error/404"));
                factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error/500"));
            }
        };
    }
}
