package org.example.modelo;


import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.files.types.UploadedFile;
import com.assemblyai.api.resources.transcripts.types.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.Excepciones.UrlInvalidaException;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;

public class Reconocedor {
    //atributos
    private final String API_KEY=Dotenv.load().get("API_KEY");
    private String idioma;
    private AssemblyAI cliente= AssemblyAI.builder()
            .apiKey(API_KEY)
            .build();
    private TranscriptOptionalParams parametros;

    //constructores

    public Reconocedor()
    {
        setParametros(null);
    }

    public Reconocedor(String idioma)
    {
       setParametros(idioma);
    }

    //get y set


    public String getIdioma() {
        return idioma;
    }

    private void setParametros(String idiomaElegido) {
        //configuro los parametros utiles del reconocedor de audio mas el idioma
        idioma=null;
        if (idiomaElegido != null)
        {
            if (idiomaElegido.equalsIgnoreCase("spanish") || idiomaElegido.equalsIgnoreCase("español") || idiomaElegido.equalsIgnoreCase("es"))
            {
                parametros=TranscriptOptionalParams.builder()
                        .languageCode(TranscriptLanguageCode.ES)
                        .speakerLabels(true)
                        .build();
                idioma="es";
            }
            else if(idiomaElegido.equalsIgnoreCase("ingles") || idiomaElegido.equalsIgnoreCase("english") || idiomaElegido.equalsIgnoreCase("en"))
            {
                parametros=TranscriptOptionalParams.builder()
                        .languageCode(TranscriptLanguageCode.EN)
                        .speakerLabels(true)
                        .sentimentAnalysis(true)
                        .build();
                idioma="en";
            }
        }
        else {
            parametros=TranscriptOptionalParams.builder()
                    .languageDetection(true)
                    .speakerLabels(true)
                    .sentimentAnalysis(true)
                    .build();
        }


    }


    //metodos

    public String vozATexto(String url) throws UrlInvalidaException, IOException {
        Transcript transcript=null;
        File archivo=null;
        String mensaje=null;

        int intentos=0;
        boolean success= false;

        //si no es un archivo de la web, lo subo al cliente ya que solo se aceptan urls con http o https
        if (!url.startsWith("http") && !url.startsWith("https")) {

            while (intentos < 3 && !success)
            {
                try{

                    archivo= new File(url);
                    UploadedFile uploadedFile = cliente.files().upload(
                            Files.readAllBytes(archivo.toPath()));
                    url=uploadedFile.getUploadUrl();
                    success = true; // Si la carga es exitosa, salimos del bucle
                }catch (RuntimeException e)
                {
                    intentos++;
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("Escuchando...");

        //escucho el audio con los parametros enviados, y retorno toda la transcripcion, con la ID, para poder resumirlo
        transcript = cliente.transcripts().transcribe(url,parametros);
        mensaje= String.valueOf(transcript.getText());

        mensaje= mensaje.replaceAll("\\[","");
        mensaje= mensaje.replaceFirst("Optional","");


        //si sucede un error, obtenemos el estado y el mensaje
        if (transcript.getStatus().equals(TranscriptStatus.ERROR)) {
            System.err.println(transcript.getError());
        }

        return mensaje;
    }






}
