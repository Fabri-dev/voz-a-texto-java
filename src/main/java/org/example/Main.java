package org.example;

import com.assemblyai.api.resources.lemur.types.LemurTaskResponse;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import org.example.Excepciones.UrlInvalidaException;
import org.example.modelo.Reconocedor;
import org.example.modelo.Resumidor;

import java.io.IOException;

/*
    tipos de archivos de audios que soporta:
    [.3ga, .8svx, .aac, .ac3, .aif, .aiff, .alac, .amr, .ape, .au, .dss, .flac, .flv,
     .m4a, .m4b, .m4p, .m4r, .mp3, .mpga, .ogg, .oga, .mogg, .opus, .qcp, .tta, .voc, .wav, .wma, .wv]
*/
public class Main {
    public static void main(String[] args){

        Reconocedor reconocedor= new Reconocedor("en");
        Transcript texto= null;
        Resumidor resumidor= new Resumidor();
        LemurTaskResponse respuesta= null;
        try {
            texto = reconocedor.vozATranscript("src/main/resources/prueba1.wav");
            respuesta= resumidor.obtenerResumenDeTranscripcion(texto);

        } catch (UrlInvalidaException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert texto != null;
        System.out.println(texto.getText());
        System.out.println(respuesta);
    }
}