package edu.brandeis.cs.uima.dkpro;

import edu.brandeis.cs.json.XmlToJson;
import edu.brandeis.cs.uima.ServiceException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.lappsgrid.serialization.lif.Container;


public class OpenNlpParser extends AbstractDkProOpenNlpService {


    static AnalysisEngine aae;

    static {
        try {
            aae = uimaDkProInit(de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpParser.class);
            System.out.println("OpenNlpParser Init...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String dsl = null;

    public OpenNlpParser(){
        dsl = getTemplate();
    }

    @Override
    public String execute(Container json) throws ServiceException {
        String txt = json.getText();
        try {
            String xml = uimaDkProOpennlp(aae, txt);
            return XmlToJson.transform(xml, dsl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }
}
