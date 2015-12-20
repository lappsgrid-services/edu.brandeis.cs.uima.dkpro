package edu.brandeis.cs.uima.dkpro;

import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;
import edu.brandeis.cs.json.XmlToJson;
import edu.brandeis.cs.uima.AbstractUimaService;
import edu.brandeis.cs.uima.UimaServiceException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.XmlCasSerializer;
import org.lappsgrid.serialization.lif.Container;

import java.io.ByteArrayOutputStream;


public class OpenNlpSplitter extends AbstractDkProOpenNlpService {


    static AnalysisEngine aae;

    static {
        try {
            aae = uimaDkProInit(OpenNlpSegmenter.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String dsl = null;

    public OpenNlpSplitter(){
        dsl = getTemplate();
    }

    @Override
    public String execute(Container json) throws UimaServiceException {
        String txt = json.getText();
        try {
            String xml = uimaDkProOpennlp(aae, txt);
            return XmlToJson.transform(xml, dsl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UimaServiceException(e.getMessage());
        }
    }
}
