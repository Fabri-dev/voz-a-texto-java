package org.example.modelo;



import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.Enums.EIdioma;

public class Resumidor {
    //atributos
    private String prompt;
    private static final String IA_API_KEY= Dotenv.load().get("IA_API_KEY");

    //constructores

    public Resumidor(EIdioma idioma) {

        if (idioma.equals("es"))
        {
            prompt="Proporcione un resumen del texto recibido. El texto es el siguiente:";

        }
        if (idioma.equals("en"))
        {

            prompt="Provide a summary of the text received. The text is the following: ";
        }
    }

    //get y set

    public String getPrompt() {
        return prompt;
    }

    //metodos

    public String resumirTexto(String textoAResumir) throws UnirestException {

        HttpResponse<String> response = Unirest.post("https://api-inference.huggingface.co/models/mistralai/Mixtral-8x7B-Instruct-v0.1")

                .header("Authorization", "Bearer " + IA_API_KEY)
                .header("Content-Type", "application/json")
                .body("{\"inputs\":\"" + prompt + textoAResumir + "\"}")
                .asString();

        return limpiarRespuesta(prompt+textoAResumir,response.getBody());
    }

    private String limpiarRespuesta(String texto,String stringALimpiar){

        stringALimpiar = stringALimpiar.replace(texto,"");

        return stringALimpiar;
    }


}
