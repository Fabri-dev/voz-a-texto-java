package org.example;

import com.assemblyai.api.resources.transcripts.types.Transcript;
import org.example.Excepciones.UrlInvalidaException;
import org.example.modelo.Reconocedor;

import java.io.IOException;


public class Main {
    public static void main(String[] args){
        Reconocedor reconocedor= new Reconocedor("es");
        Transcript texto= null;
        try {
            /*
            tipos de archivos de audios que soporta:
                [.3ga, .8svx, .aac, .ac3, .aif, .aiff, .alac, .amr, .ape, .au, .dss, .flac, .flv,
                .m4a, .m4b, .m4p, .m4r, .mp3, .mpga, .ogg, .oga, .mogg, .opus, .qcp, .tta, .voc, .wav, .wma, .wv]
             */
            texto = reconocedor.vozATranscript("src/main/resources/WhatsApp Ptt 2024-06-21 at 7.53.40 AM.ogg");
        } catch (UrlInvalidaException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert texto != null;
        System.out.println(texto.getText());
    }
}