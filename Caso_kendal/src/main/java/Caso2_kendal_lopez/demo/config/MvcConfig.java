/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_kendal_lopez.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Obtenemos la ruta absoluta hacia tu carpeta física src/main/resources/static/img
        String rutaFotos = Paths.get("src/main/resources/static/img").toAbsolutePath().toUri().toString();
        
        // Hacemos que cualquier solicitud web a /img/** vaya a buscar los archivos a esa ruta física
        registry.addResourceHandler("/img/**")
                .addResourceLocations(rutaFotos);
    }
}