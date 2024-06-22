package org.example.modelo;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.lemur.requests.LemurTaskParams;
import com.assemblyai.api.resources.lemur.types.LemurTaskResponse;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

public class Resumidor {
    //atributos
    private String prompt;
    private AssemblyAI cliente= AssemblyAI.builder()
            .apiKey(Dotenv.load().get("API_KEY"))
            .build();

    //constructores

    public Resumidor(){
        prompt="Provide a brief summary of the transcript.";
    }
    public Resumidor(String prompt){
        this.prompt= prompt;
    }

    //get y set

    public String getPrompt() {
        return prompt;
    }

    //metodos
    public LemurTaskResponse obtenerResumenDeTranscripcion(Transcript transcripcion) {
        LemurTaskParams parametrosIA= LemurTaskParams.builder()
                .prompt(prompt)
                .transcriptIds(List.of(transcripcion.getId()))
                .build();

        return cliente.lemur().task(parametrosIA);


    }
}
