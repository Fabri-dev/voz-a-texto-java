# Voz a Texto en Java

Este proyecto es una implementación de un sistema de reconocimiento de voz a texto utilizando la API de AssemblyAI. El programa permite convertir archivos de audio en texto de manera eficiente y precisa.

## Características

- **Conversión de audio a texto:** Utiliza la API de AssemblyAI para transcribir archivos de audio.
- **Utilizar IA para resumir el texto:** Utiliza Access token de la pagina Hugging Face. Url: https://huggingface.co/settings/tokens
- **Manejo de excepciones:** Implementación robusta para manejar excepciones de red y tiempos de espera.
- **Interfaz de usuario sencilla:** Interfaz de consola para cargar archivos y mostrar resultados.

## Requisitos

- Java 8 o superior
- Maven
- Cuenta y clave API de AssemblyAI

## Configurar variables de entorno

- Se debe crear un archivo .env con las siguientes credenciales y guardarlo en el repositorio del app:
    - API_KEY=tu_clave_api_aqui
    - IA_API_KEY=tu_ia_clave_api_aqui


## Uso

-Voz a texto:

  String texto = reconocedor.vozATexto(new File("src/main/resources/pruebaEn4.mp3")); 

-Resumir texto:

  String resumen= resumidor.resumirTexto(texto);
          
## Contribuciones
¡Las contribuciones son bienvenidas! Por favor, sigue estos pasos para contribuir:
