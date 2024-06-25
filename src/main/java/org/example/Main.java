package org.example;

import com.mashape.unirest.http.exceptions.UnirestException;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import org.example.Enums.EIdioma;

import org.example.modelo.Reconocedor;
import org.example.modelo.Resumidor;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;

/*
    tipos de archivos de audios que soporta:
    [.3ga, .8svx, .aac, .ac3, .aif, .aiff, .alac, .amr, .ape, .au, .dss, .flac, .flv,
     .m4a, .m4b, .m4p, .m4r, .mp3, .mpga, .ogg, .oga, .mogg, .opus, .qcp, .tta, .voc, .wav, .wma, .wv]
*/
public class Main {
    public static void main(String[] args){

        String texto= null;
        String respuesta= null;


        //Se debe especificar anteriormente el idioma (ES o EN), si el idioma es otro, elija OTHER
        Reconocedor reconocedor= new Reconocedor(EIdioma.ES);
        Resumidor resumidor= new Resumidor(reconocedor.getIdioma());

        try {
            texto = reconocedor.vozATexto(new File("src/main/resources/pruebaEs4.ogg"));

            if (!(texto == "error") || !(texto == "El archivo recibido no existe\n"))
            {
                respuesta= resumidor.resumirTexto(texto);

            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (UnirestException e) {
            e.printStackTrace();
        }


        System.out.println("reconocedor: ");
        System.out.println(texto);
        System.out.println("resumen: ");
        System.out.println(respuesta);


    }
}