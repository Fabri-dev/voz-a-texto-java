package org.example.modelo;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.Enums.EIdioma;
import java.io.File;
import java.io.IOException;


public class Reconocedor {
    //atributos
    private final String API_KEY=Dotenv.load().get("API_KEY");
    private EIdioma idioma;
    private AssemblyAI cliente= AssemblyAI.builder()
            .apiKey(API_KEY)
            .build();
    private TranscriptOptionalParams parametros;

    //constructores

    public Reconocedor()
    {
        setParametros(EIdioma.OTHER);
    }

    public Reconocedor(EIdioma idioma)
    {
       setParametros(idioma);
    }

    //get y set


    public EIdioma getIdioma() {
        return idioma;
    }

    private void setParametros(EIdioma idiomaElegido) {
        //configuro los parametros utiles del reconocedor de audio mas el idioma

        if (idiomaElegido == EIdioma.ES)
        {

                parametros=TranscriptOptionalParams.builder()
                        .languageCode(TranscriptLanguageCode.ES) //elijo el idioma que sera en espaniol
                        .speakerLabels(true) //
                        .build();
                idioma=EIdioma.ES;
        }
        else if(idiomaElegido == EIdioma.EN)
        {
            parametros=TranscriptOptionalParams.builder()
                    .languageCode(TranscriptLanguageCode.EN)
                    .speakerLabels(true)
                    .sentimentAnalysis(true)
                    .build();
            idioma=EIdioma.EN;
        }
        else {
            parametros=TranscriptOptionalParams.builder()
                    .speakerLabels(true)
                    .languageDetection(true)
                    .build();
            idioma=EIdioma.OTHER;
        }

    }


    //metodos


    //Utilizando una url de la web
    public String vozATexto(String url){
        Transcript transcripcion=null;


        String mensaje="La url debe empezar con https o http";


        //Si es una archivo de la web, y tiene una url utilizamos este metodo
        if (url.startsWith("http") && url.startsWith("https")) {
            System.out.println("Escuchando...");


            //escucho el audio con los parametros enviados, y retorno toda la transcripcion, para poder resumirlo
            transcripcion = cliente.transcripts().transcribe(url,parametros);


            mensaje= String.valueOf(transcripcion.getText()); //transcripcion tiene muchos metodos mas pero solo necesito el texto

            //limpiamos el mensaje
            mensaje= mensaje.replace("[]","");
            mensaje= mensaje.replaceFirst("Optional","");


            //si sucede un error, obtenemos el estado y el mensaje
            if (transcripcion.getStatus().equals(TranscriptStatus.ERROR)) {
                System.err.println(transcripcion.getError());
            }

        }

        return mensaje;
    }


    //utilizando un archivo
    public String vozATexto(File archivoRecibido) throws IOException {

        Transcript transcripcion=null;
        String mensaje="El archivo recibido no existe";
        int intentos=0;
        boolean exito=false;

        //si es un archivo local, utilizo el metodo sobrecargado del API para poder subirlo y escucharlo
        if (archivoRecibido.exists()) {

            System.out.println("Escuchando...");

            //escucho el audio con los parametros enviados, y retorno toda la transcripcion, para poder resumirlo
            while (intentos < 3 && !exito) {
                try {

                    transcripcion = cliente.transcripts().transcribe(archivoRecibido, parametros);
                    exito = true;
                } catch (RuntimeException e) {
                    intentos++;
                    System.out.println(e.getMessage());
                }
            }

            if (exito)
            {

                mensaje= String.valueOf(transcripcion.getText()); //transcripcion tiene muchos metodos mas pero solo necesito el texto

                //limpiamos el mensaje
                mensaje= mensaje.replace("[","");
                mensaje= mensaje.replace("]","");
                mensaje= mensaje.replaceFirst("Optional","");


                //si sucede un error, obtenemos el estado y el mensaje
                if (transcripcion.getStatus().equals(TranscriptStatus.ERROR)) {
                    System.err.println(transcripcion.getError());
                }
            }
            else {
                mensaje="error";
            }
        }

        return mensaje;
    }

}
