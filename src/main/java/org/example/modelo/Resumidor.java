package org.example.modelo;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.lemur.requests.LemurTaskParams;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

public class Resumidor {
    //atributos
    private String prompt;
    private AssemblyAI cliente= AssemblyAI.builder()
            .apiKey(Dotenv.load().get("API_KEY"))
            .build();
    private LemurTaskParams parametrosIA;
    //constructores

    public Resumidor(){
        prompt="Provide a brief summary of the transcript.";
    }
    public Resumidor(String prompt){
        this.prompt= prompt;
    }

    //get y set

    public void setParametrosIA() {
        parametrosIA= LemurTaskParams.builder()
                .prompt(prompt)
                .transcriptIds(List.of(transcript.getId()))
                .build();
    }


    //metodos
}
