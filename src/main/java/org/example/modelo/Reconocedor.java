package org.example.modelo;


import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.files.types.UploadedFile;
import com.assemblyai.api.resources.transcripts.types.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.Excepciones.UrlInvalidaException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Reconocedor {
    //atributos
    private final String API_KEY;
    private AssemblyAI cliente;
    private TranscriptOptionalParams parametros;

    //constructores

    public Reconocedor(String idioma)
    {
        API_KEY=Dotenv.load().get("API_KEY");

        cliente = AssemblyAI.builder()
                .apiKey(API_KEY)
                .build();

        setParametros(idioma);
    }

    //get y set

    private void setParametros(String idiomaElegido) {
        //configuro los parametros utiles del reconocedor de audio mas el idioma
        if (idiomaElegido != null)
        {
            if (idiomaElegido.equalsIgnoreCase("spanish") || idiomaElegido.equalsIgnoreCase("español") || idiomaElegido.equalsIgnoreCase("es"))
            {
                parametros=TranscriptOptionalParams.builder()
                        .languageCode(TranscriptLanguageCode.ES)
                        .speakerLabels(true)
                        .build();
            }
            else if(idiomaElegido.equalsIgnoreCase("ingles") || idiomaElegido.equalsIgnoreCase("english") || idiomaElegido.equalsIgnoreCase("en"))
            {
                parametros=TranscriptOptionalParams.builder()
                        .languageCode(TranscriptLanguageCode.EN)
                        .speakerLabels(true)
                        .sentimentAnalysis(true)
                        .build();
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

    public Transcript vozATranscript(String url) throws UrlInvalidaException, IOException {
        Transcript transcript=null;
        File archivo=null;

        //si no es un archivo de la web, lo subo al cliente ya que solo se aceptan urls con http o https
        if (!url.startsWith("http") || !url.startsWith("https"))
        {
            archivo= new File(url);
            UploadedFile uploadedFile = cliente.files().upload(Files.readAllBytes(archivo.toPath()));
            url=uploadedFile.getUploadUrl();
        }

        System.out.println("Escuchando...");

        //escucho el audio con los parametros enviados, y retorno toda la transcripcion
        transcript = cliente.transcripts().transcribe(url,parametros);

        //si sucede un error, obtenemos el estado y el mensaje
        if (transcript.getStatus().equals(TranscriptStatus.ERROR)) {
            System.err.println(transcript.getError());
        }

        return transcript;
    }






}
