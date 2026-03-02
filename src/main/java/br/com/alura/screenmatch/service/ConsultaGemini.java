package br.com.alura.screenmatch.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class ConsultaGemini {
    private static Client client = new Client();

    public static String obterTraducao(String sinopse){
    String prompt = "Traduza a sinopse (" + sinopse + ") para português brasileiro.";
        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null);

        return response.text();
    }
}