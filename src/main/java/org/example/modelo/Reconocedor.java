package org.example.modelo;


import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.files.types.UploadedFile;
import com.assemblyai.api.resources.transcripts.types.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.Enums.EIdioma;
import org.example.Excepciones.UrlInvalidaException;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;

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

    public String vozATexto(String url) throws UrlInvalidaException, IOException {
        Transcript transcripcion=null;
        File archivo=null;
        String mensaje=null;

        int intentos=0;
        boolean success= false;

        //si no es un archivo de la web, lo subo al cliente ya que solo se aceptan urls con http o https
        if (!url.startsWith("http") && !url.startsWith("https")) {

            //a veces sucede que arroja SocketTimeoutException debido al tiempo de espera,
            //esto se debe a que el archivo a medida que es mas grande es mas pesado y por lo tanto
            //tardara mas en subirse, por lo que si sucede una excp de este tipo, intento de nuevo con un limite de 3 veces
            while (intentos < 3 && !success)
            {
                try{

                    archivo= new File(url);
                    UploadedFile uploadedFile = cliente.files().upload(
                            Files.readAllBytes(archivo.toPath()));
                    url=uploadedFile.getUploadUrl();
                    success = true; // Si la carga es exitosa, salimos del bucle
                }catch (SocketTimeoutException e)
                {
                    intentos++;
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("Escuchando...");

        //escucho el audio con los parametros enviados, y retorno toda la transcripcion, para poder resumirlo
        transcripcion = cliente.transcripts().transcribe(url,parametros);
        mensaje= String.valueOf(transcripcion.getText()); //transcripcion tiene muchos metodos mas pero solo necesito el texto

        mensaje= mensaje.replace("[",""); //
        mensaje= mensaje.replaceFirst("Optional","");


        //si sucede un error, obtenemos el estado y el mensaje
        if (transcripcion.getStatus().equals(TranscriptStatus.ERROR)) {
            System.err.println(transcripcion.getError());
        }

        return mensaje;
    }






}
