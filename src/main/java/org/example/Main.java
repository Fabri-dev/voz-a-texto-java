package org.example;

import com.mashape.unirest.http.exceptions.UnirestException;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
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
        String texto= null;
        Resumidor resumidor= new Resumidor(reconocedor.getIdioma());
        String respuesta= null;
        try {
            texto = reconocedor.vozATexto("src/main/resources/pruebaEn4.mp3");
           respuesta= resumidor.resumirTexto(texto);

        } catch (UrlInvalidaException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        assert texto != null;
        System.out.println(texto);
        System.out.println(respuesta);


    }
}