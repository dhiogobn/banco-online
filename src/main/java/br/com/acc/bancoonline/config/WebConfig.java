package br.com.acc.bancoonline.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Configura CORS para todos os endpoints
                .allowedOriginPatterns("*") // Permite qualquer origem (substitua por URLs específicas se necessário)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Métodos permitidos
                .allowedHeaders("*") // Permite todos os cabeçalhos
                .allowCredentials(true); // Permite o envio de credenciais (cookies, headers de autorização, etc.)
    }
}