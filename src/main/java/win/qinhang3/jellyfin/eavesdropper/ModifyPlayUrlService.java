package win.qinhang3.jellyfin.eavesdropper;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: hang
 * @create: 2022/6/30
 **/
@Service
public class ModifyPlayUrlService {

    @Value("${jellyfin.apiKey}")
    private String apiKey;

    public String apply(String s) {
        try {
            SAXReader reader = new SAXReader(false);
            Document document = reader.read(new InputSource(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8))));
            Node resultNode = document.selectSingleNode("/*[name()='SOAP-ENV:Envelope']/*[name()='SOAP-ENV:Body']/*[name()='u:BrowseResponse']/*[name()='Result']");
            if (resultNode == null){
                return s;
            }
            String result = resultNode.getText();

            Document resultDocument = reader.read(new InputSource(new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8))));
            List<Node> list = resultDocument.selectNodes("/*[name()='DIDL-Lite']/*[name()='item']/*[name()='res']");
            for (Node node : list) {
                node.setText(replace(node.getText()));
            }
            resultNode.setText(resultDocument.asXML());
            return document.asXML();
        } catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }

    Pattern pattern = Pattern.compile("/videos/([a-z0-9\\-]+)/stream");

    private String replace(String s) {
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()){
            URI uri = URI.create(s);
            String group = matcher.group(1);
            return uri.getScheme() + "://" + uri.getAuthority() + "/Items/" + group + "/Download?api_key=" + apiKey;
        } else {
            return s;
        }
    }

}
