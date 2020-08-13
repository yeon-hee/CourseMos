package kr.co.shineware;

import java.util.List;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

public class TagGenerator {

    String contents;

    public TagGenerator(String contents) {
        this.contents = contents;
    }

    public String getTags() {
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

        KomoranResult analyzeResultList = komoran.analyze(contents);

        String tags = "";
        int tagNum = 0;

        List<Token> tokenList = analyzeResultList.getTokenList();
        for (Token token : tokenList) {
            if ((token.getPos().startsWith("NNG") || token.getPos().startsWith("NNP")) && token.getMorph().length() >= 2
                    && !tags.contains(token.getMorph())) {
                tags = tags + token.getMorph() + " ";
                tagNum += 1;
            }
            if (tagNum == 5) {
                break;
            }
        }

        return tags;

    }

}