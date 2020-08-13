package kr.co.shineware.komoran.tutorials;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

import java.util.List;

public class App {
    public static void main(String[] content) {
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        String strToAnalyze = "대구 동성로 토요정! 친구 따라 찾아간 토요정~ 가게가 동성로 골목으로 들어가야돼서 찾기 어려울 수도 있을것 같다. 가게는 작았지만 분위기가 너무 귀여웠다.";
        // String strToAnalyze = content;

        KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);

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

        System.out.println(tags);

    }
}