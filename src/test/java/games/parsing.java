package games;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by royba on 14/06/2016.
 */
public class parsing {

    String Active = new String ("active");
    String gameurl = "https://mobile.williamhillcasino.com/igaming/";
    String url = "https://mobile.williamhillcasino.com/library/XML/GameInfo/game-info-true-v24.xml";
    String[] NgmgamesUrl = new String[0];
    String[] MgpgamesUrl = new String[0];

    public  String[] NgmgameParser()
    {
        try
        {
            DocumentBuilderFactory f =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(url);

            doc.getDocumentElement().normalize();

            // loop through each game

            NodeList games = doc.getElementsByTagName("game");
            NgmgamesUrl = new String[games.getLength()];
            for (int i = 0; i < games.getLength(); i++)
            {
                Node n = games.item(i);
                if (n.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                Element e = (Element) n;

                // get the gamecode elem
                NodeList gameList =
                        e.getElementsByTagName("gameCode");
                Element gameElem = (Element) gameList.item(0);
                // get the tag elem
                NodeList gametags =
                        e.getElementsByTagName("tag");
                // get the gametype elem
                NodeList gametypes =
                        e.getElementsByTagName("mobileGameType");
                Element typeElem = (Element) gametypes.item(0);
                Node gametype = typeElem.getChildNodes().item(0);
                boolean isNgmGame = gametype.getNodeValue().equals("NGM");

                int count = 0;

                // loop through each game tag to check if game is active

                for( int j = 0; j < gametags.getLength(); j++){
                    Element tagsElem = (Element) gametags.item(j);
                    Node gameTag = tagsElem.getChildNodes().item(0);
                    String isActiveTag = new String(gameTag.getNodeValue());
                    if(isActiveTag.equals(Active)){
                        count++;
                    }
                }

                //  if active, store game url into gameUrl[] Array
                if(count == 1 && isNgmGame){
                    Node gameNode = gameElem.getChildNodes().item(0);
                    NgmgamesUrl[i] = gameurl + gameNode.getNodeValue();
                    //System.out.println(gamesUrl[i]);
                }
                else{
                    NgmgamesUrl[i] = "Not In List";
                }

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return NgmgamesUrl;
    }

    public  String[] MgpgameParser()
    {
        try
        {
            DocumentBuilderFactory f =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(url);

            doc.getDocumentElement().normalize();

            // loop through each game

            NodeList games = doc.getElementsByTagName("game");
            MgpgamesUrl = new String[games.getLength()];
            for (int i = 0; i < games.getLength(); i++)
            {
                Node n = games.item(i);
                if (n.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                Element e = (Element) n;

                // get the gamecode elem
                NodeList gameList =
                        e.getElementsByTagName("gameCode");
                Element gameElem = (Element) gameList.item(0);
                // get the tag elem
                NodeList gametags =
                        e.getElementsByTagName("tag");
                // get the gametype elem
                NodeList gametypes =
                        e.getElementsByTagName("mobileGameType");
                Element typeElem = (Element) gametypes.item(0);
                Node gametype = typeElem.getChildNodes().item(0);
                boolean isMgpGame = gametype.getNodeValue().equals("MGP");

                int count = 0;

                // loop through each game tag to check if game is active

                for( int j = 0; j < gametags.getLength(); j++){
                    Element tagsElem = (Element) gametags.item(j);
                    Node gameTag = tagsElem.getChildNodes().item(0);
                    String isActiveTag = new String(gameTag.getNodeValue());
                    if(isActiveTag.equals(Active)){
                        count++;
                    }
                }

                //  if active, store game url into gameUrl[] Array
                if(count == 1 && isMgpGame){
                    Node gameNode = gameElem.getChildNodes().item(0);
                    MgpgamesUrl[i] = gameurl + gameNode.getNodeValue();
                    //System.out.println(gamesUrl[i]);
                }
                else{
                        MgpgamesUrl[i] = "Not In List";
                }

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return MgpgamesUrl;
    }

    public int NgmgamesUrlSize() { return NgmgameParser().length; }

    public int MgpgamesUrlSize() { return MgpgameParser().length; }


}

