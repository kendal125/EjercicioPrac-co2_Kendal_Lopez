/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_kendal_lopez.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CorreoService { 

    private final JavaMailSender despachadorDeCorreos; 

    public CorreoService(JavaMailSender despachadorDeCorreos) {
        this.despachadorDeCorreos = despachadorDeCorreos;
    }

    public void enviarBienvenida(String emailDestino, String nombreUsuario) {
        try {
            // Creamos el mensaje estilo Mime para permitir HTML
            MimeMessage mensajePreparado = despachadorDeCorreos.createMimeMessage();
            MimeMessageHelper configurador = new MimeMessageHelper(mensajePreparado, true, "UTF-8");

            // Diseño básico del correo en HTML
            String cuerpoDelMensaje = "<h3>¡Bienvenido a la Plataforma de Eventos, " + nombreUsuario + "!</h3>"
                    + "<p>Nos alegra informarte que tu cuenta ha sido creada exitosamente.</p>"
                    + "<p>Ya puedes iniciar sesión y explorar nuestros eventos disponibles.</p>"
                    + "<br><p>Atentamente,<br>El equipo de administración.</p>";

            // Configuramos los datos del correo 
            configurador.setTo(emailDestino);
            configurador.setSubject("Confirmación de Registro - Plataforma de Eventos");
            configurador.setText(cuerpoDelMensaje, true); // El 'true' indica que es HTML

            despachadorDeCorreos.send(mensajePreparado);
            
        } catch (MessagingException e) {
            // Manejo de errores por si falla el envío
            System.err.println("Error al intentar enviar el correo de bienvenida: " + e.getMessage());
        }
    }
}