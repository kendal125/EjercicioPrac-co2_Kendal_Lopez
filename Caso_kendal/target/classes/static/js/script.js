// Esperar a que todo el documento HTML cargue antes de ejecutar el código
document.addEventListener("DOMContentLoaded", function() {

    // 1. OCULTAR ALERTAS AUTOMÁTICAMENTE
    // Si hay un mensaje de éxito o error (como el de login), lo desaparece después de 4 segundos
    const alertas = document.querySelectorAll('.alert');
    alertas.forEach(function(alerta) {
        setTimeout(function() {
            // Usamos la opacidad para un efecto de desvanecimiento suave
            alerta.style.transition = "opacity 0.5s ease";
            alerta.style.opacity = "0";
            
            // Después de que se desvanece, lo quitamos del HTML
            setTimeout(() => alerta.remove(), 500); 
        }, 4000); // 4000 milisegundos = 4 segundos
    });

    // 2. VALIDACIÓN VISUAL DE FORMULARIOS (Estilo Bootstrap)
    // Hace que los campos en blanco se pongan rojos si intentas guardar sin llenarlos
    const formularios = document.querySelectorAll('form');
    formularios.forEach(function(form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault(); // Detiene el envío si hay errores
                event.stopPropagation();
            }
            form.classList.add('was-validated'); // Aplica las clases CSS de Bootstrap
        }, false);
    });

    // 3. CONFIRMACIÓN MEJORADA PARA ELIMINAR (Opcional)
    // Si prefieres usar esto en lugar del onclick="return confirm(...)" en el HTML
    const botonesEliminar = document.querySelectorAll('.btn-outline-danger');
    botonesEliminar.forEach(function(boton) {
        boton.addEventListener('click', function(event) {
            // Verifica si el botón tiene la palabra "Eliminar" o "Borrar" en su título
            if(boton.getAttribute('title') && boton.getAttribute('title').includes('Eliminar')) {
                if(!confirm("¿Estás completamente seguro de realizar esta acción? Los cambios no se pueden deshacer.")) {
                    event.preventDefault(); // Si el usuario cancela, no borra nada
                }
            }
        });
    });

});


