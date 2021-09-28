package br.com.pratudo.recipe.service;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.pt.PortugueseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyzerService {

    List<String> analyze(String text) throws IOException {
        try (PortugueseAnalyzer portugueseAnalyzer = new PortugueseAnalyzer()) {
            List<String> result = new ArrayList<>();
            TokenStream tokenStream = portugueseAnalyzer.tokenStream("ingredient", text);
            CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while(tokenStream.incrementToken()) {
                result.add(attr.toString());
            }
            tokenStream.close();
            return result;
        }
    }
}
