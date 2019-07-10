package com.legatir.mylearnenglish;

/**
 * Created by lital on 16/09/2017.
 */
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.app.Activity;
import android.util.Log;


public class XmlParser {


    /**
     * Example:
     *
     * for this input:
     *
     * <?xml version="1.0"?>
     * <records>
     *     <office1>
     *         <employee>Eli<employee>
     *         <employee>Lital<employee>
     *         <employee>Guyemployee>
     *     </office1>
     *     <office2>
     *         <employee>aa<employee>
     *         <employee>bb<employee>
     *     </office2>
     *  </records>
     *
     * And these parameters:
     *
     * @param topContainerXmlElementName - "records"
     * @param optionalSubContainerXmlElementName - "office1"
     * @param optionalSubSubContainerXmlElementName - null
     * @param repeatedXmlElementNameStartsWith -  "employee"
     *
     *  will return array:
     *
     *       "Eli", "Lital", "Guy"
     *
     */
    public ArrayList<String> parseSectionFromFileToArray(Activity context,
                                                String fileName,
                                                String topContainerXmlElementName,
                                                String optionalSubContainerXmlElementName,
                                                String optionalSubSubContainerXmlElementName,
                                                String repeatedXmlElementNameStartsWith) throws Exception {

        ArrayList<String> valuesFromXml = new ArrayList<String>();

        InputStream inputStream = null;

        try {

            inputStream = context.getAssets().open(fileName);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);

            Element element=doc.getDocumentElement();
            element.normalize();

            Node containerElement = doc;

            Node topContainerElement = doc;

            if (null!=topContainerXmlElementName) {
                topContainerElement = doc.getElementsByTagName(topContainerXmlElementName).item(0);
            }

            if (null!=optionalSubContainerXmlElementName) {
                if (null!=topContainerElement) {

                    NodeList subContainerElements = topContainerElement.getChildNodes();
                    for (int i=0; i<subContainerElements.getLength(); i++) {
                        if (subContainerElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
                            if ( subContainerElements.item(i).getNodeName().equalsIgnoreCase(optionalSubContainerXmlElementName) ) {
                                containerElement = subContainerElements.item(i);
                                break;
                            }
                        }
                    }
                }
                else {
                    containerElement = doc.getElementsByTagName(optionalSubContainerXmlElementName).item(0);
                }
            }


            if (null!=optionalSubSubContainerXmlElementName) {
                if (null!=topContainerElement) {

                    NodeList subContainerElements = containerElement.getChildNodes();
                    for (int i=0; i<subContainerElements.getLength(); i++) {
                        if (subContainerElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
                            if ( subContainerElements.item(i).getNodeName().equalsIgnoreCase(optionalSubSubContainerXmlElementName) ) {
                                containerElement = subContainerElements.item(i);
                                break;
                            }
                        }
                    }
                }
                else {
                    containerElement = doc.getElementsByTagName(optionalSubSubContainerXmlElementName).item(0);
                }
            }


            NodeList repeatedElements = containerElement.getChildNodes();

            for (int i=0; i<repeatedElements.getLength(); i++) {
                if (repeatedElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    if (repeatedElements.item(i).getNodeName().toUpperCase().startsWith(repeatedXmlElementNameStartsWith.toUpperCase())) {
                        valuesFromXml.add(repeatedElements.item(i).getFirstChild().getNodeValue());
                    }
                }
            }

        } catch (Exception e)   {
            throw new Exception("Failed to read data.",e);
        }
        finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }


        return valuesFromXml;
    }



    /**
     * Example:
     *
     * for this input:
     *
     * <?xml version="1.0"?>
     * <records>
     *     <office1>
     *         <employee>
     *             <name>Eli</name>
     *             <surname>Isaak</surname>
     *             <salary>8000</salary>
     *          </employee>
     *          <employee>
     *             <name>Lital</name>
     *             <surname>Isaak</surname>
     *             <salary>6000</salary>
     *          </employee>
     *       </office1>
     *       <office2>
     *          <employee>
     *             <name>anther</name>
     *             <surname>none</surname>
     *             <salary>0</salary>
     *          </employee>
     *      </office2>
     *  </records>
     *
     * And these parameters:
     *
     * @param topContainerXmlElementName - null
     * @param subContainerXmlElementName - "office1"
     * @param repeatedXmlElementNameStartsWith -  "employee"
     * @param subXmlElementNames  -  "name", "surename", "salary"
     *
     *  will return hashmap:
     *
     *       "Eli"  :  ( "surename:"Isaak", "salary":"8000" )
     *       "Lital"   ( "surename:"Isaak", "salary":"6000" )
     *
     *
     */
    public HashMap<String,HashMap<String,String>> parseSectionFromFileToHashMapOfStructures(Activity context, String fileName,
                                                            String topContainerXmlElementName,
                                                            String subContainerXmlElementName,
                                                            String repeatedXmlElementNameStartsWith,
                                                            ArrayList<String> subXmlElementNames) throws Exception{

        HashMap<String,HashMap<String,String>> xmlDataHashMap =
                new HashMap<String,HashMap<String,String>>();

        InputStream inputStream = null;


        try {

            inputStream = context.getAssets().open(fileName);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);

            Element element=doc.getDocumentElement();
            element.normalize();

            Node containerElement = doc;

            Node topContainerElement = doc;

            if (null!=topContainerXmlElementName) {
                topContainerElement = doc.getElementsByTagName(topContainerXmlElementName).item(0);
            }

            if (null!=subContainerXmlElementName) {
                if (null!=topContainerElement) {

                    NodeList subContainerElements = topContainerElement.getChildNodes();
                    for (int i=0; i<subContainerElements.getLength(); i++) {
                        if (subContainerElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
                            if ( subContainerElements.item(i).getNodeName().equalsIgnoreCase(subContainerXmlElementName) ) {
                                containerElement = subContainerElements.item(i);
                            }
                        }
                    }
                }
                else {
                    containerElement = doc.getElementsByTagName(subContainerXmlElementName).item(0);
                }
            }

            NodeList repeatedElements = containerElement.getChildNodes();

            //if ( !repeatedElements.item(0).getNodeName().startsWith(repeatedXmlElementNameStartsWith)) {
            //    throw new Exception( "Expected repeated element name to start with '" + repeatedXmlElementNameStartsWith + "' in XML file: " + fileName );
            //}

            for (int i=0; i<repeatedElements.getLength(); i++) {

                if ( !repeatedElements.item(0).getNodeName().toUpperCase().startsWith(repeatedXmlElementNameStartsWith.toUpperCase())) {
                    continue;
                }

                Node node = repeatedElements.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element currentRepeatedElement = (Element) node;

                    String keyInMajorMap =
                         currentRepeatedElement.getElementsByTagName(subXmlElementNames.get(0)).item(0).getNodeValue();

                    HashMap internalHashMap  = new HashMap<String, String>();

                    for (int j=1; j<subXmlElementNames.size(); i++) {

                        String keyInInternalHasMap = currentRepeatedElement.getElementsByTagName(subXmlElementNames.get(j)).item(0).getNodeName();
                        String valueInInternalHasMap = currentRepeatedElement.getElementsByTagName(subXmlElementNames.get(j)).item(0).getNodeValue();
                        internalHashMap.put(keyInInternalHasMap, valueInInternalHasMap);
                    }

                    xmlDataHashMap.put( keyInMajorMap, internalHashMap );
                }
            }

        } catch (Exception e)   {
            throw new Exception("Failed to read data.",e);
        }
        finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }


        return xmlDataHashMap;
    }

//    private static String getValue(String tag, Element element) {
//            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
//            Node node = nodeList.item(0);
//            return node.getNodeValue();
//    }


    public ArrayList<HashMap<String,String>> parseSectionFromFileToArrayOfHashMaps(Activity context,
                                                         String fileName,
                                                         String optionalTopContainerXmlElementName,
                                                         String optionalSubContainerXmlElementName,
                                                         String optionalSubSubContainerXmlElementName,
                                                         String repeatedContainerXmlElementNameStartsWith) throws Exception {

        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();

        InputStream inputStream = null;

        HashMap<String,String> currentHashMap = null;

        try {

            inputStream = context.getAssets().open(fileName);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);

            Element element=doc.getDocumentElement();
            element.normalize();

            Node containerElement = doc;

            Node topContainerElement = doc;

            if (null!=optionalTopContainerXmlElementName) {
                topContainerElement = doc.getElementsByTagName(optionalTopContainerXmlElementName).item(0);
                containerElement = topContainerElement;
            }

            if (null!=optionalSubContainerXmlElementName) {
                if (null!=topContainerElement) {

                    NodeList subContainerElements = topContainerElement.getChildNodes();
                    for (int i=0; i<subContainerElements.getLength(); i++) {
                        if (subContainerElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
                            if ( subContainerElements.item(i).getNodeName().equalsIgnoreCase(optionalSubContainerXmlElementName) ) {
                                containerElement = subContainerElements.item(i);
                                break;
                            }
                        }
                    }
                }
                else {
                    containerElement = doc.getElementsByTagName(optionalSubContainerXmlElementName).item(0);
                }
            }


            if (null!=optionalSubSubContainerXmlElementName) {
                if (null!=topContainerElement) {

                    NodeList subContainerElements = containerElement.getChildNodes();
                    for (int i=0; i<subContainerElements.getLength(); i++) {
                        if (subContainerElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
                            if ( subContainerElements.item(i).getNodeName().equalsIgnoreCase(optionalSubSubContainerXmlElementName) ) {
                                containerElement = subContainerElements.item(i);
                                break;
                            }
                        }
                    }
                }
                else {
                    containerElement = doc.getElementsByTagName(optionalSubSubContainerXmlElementName).item(0);
                }
            }


            NodeList repeatedElements = containerElement.getChildNodes();

            for (int i=0; i<repeatedElements.getLength(); i++) {
                if (repeatedElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    if (repeatedElements.item(i).getNodeName().toUpperCase().startsWith(repeatedContainerXmlElementNameStartsWith.toUpperCase())) {
                        //valuesFromXml.add(repeatedElements.item(i).getFirstChild().getNodeValue());
                        Node currentContainerElement = repeatedElements.item(i);

                        currentHashMap = new HashMap<String,String>();

                        for (int j=0; j<currentContainerElement.getChildNodes().getLength(); j++) {
                            Node currentFieldElement = currentContainerElement.getChildNodes().item(j);
                            if (currentFieldElement.getNodeType() == Node.ELEMENT_NODE) {
                                //Log.i( "MyTest", "---- " +
                                //        currentFieldElement.getNodeName() + " : " +
                                //        currentFieldElement.getFirstChild().getNodeValue() );
                                currentHashMap.put(currentFieldElement.getNodeName(),currentFieldElement.getFirstChild().getNodeValue());
                            }
                        }

                        result.add(currentHashMap);

                    }
                }
            }

        } catch (Exception e)   {
            throw new Exception("Failed to read data.",e);
        }
        finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }


        return result;
    }

    public Set<HashMap<String,String>> parseSectionFromFileToSetOfHashMaps(Activity context,
                                                                           String fileName,
                                                                           String optionalTopContainerXmlElementName,
                                                                           String optionalSubContainerXmlElementName,
                                                                           String optionalSubSubContainerXmlElementName,
                                                                           String repeatedContainerXmlElementNameStartsWith) throws Exception {

        Set<HashMap<String,String>> result = new HashSet<HashMap<String,String>>();

        InputStream inputStream = null;

        HashMap<String,String> currentHashMap = null;

        try {

            inputStream = context.getAssets().open(fileName);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);

            Element element=doc.getDocumentElement();
            element.normalize();

            Node containerElement = doc;

            Node topContainerElement = doc;

            if (null!=optionalTopContainerXmlElementName) {
                topContainerElement = doc.getElementsByTagName(optionalTopContainerXmlElementName).item(0);
                containerElement = topContainerElement;
            }

            if (null!=optionalSubContainerXmlElementName) {
                if (null!=topContainerElement) {

                    NodeList subContainerElements = topContainerElement.getChildNodes();
                    for (int i=0; i<subContainerElements.getLength(); i++) {
                        if (subContainerElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
                            if ( subContainerElements.item(i).getNodeName().equalsIgnoreCase(optionalSubContainerXmlElementName) ) {
                                containerElement = subContainerElements.item(i);
                                break;
                            }
                        }
                    }
                }
                else {
                    containerElement = doc.getElementsByTagName(optionalSubContainerXmlElementName).item(0);
                }
            }


            if (null!=optionalSubSubContainerXmlElementName) {
                if (null!=topContainerElement) {

                    NodeList subContainerElements = containerElement.getChildNodes();
                    for (int i=0; i<subContainerElements.getLength(); i++) {
                        if (subContainerElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
                            if ( subContainerElements.item(i).getNodeName().equalsIgnoreCase(optionalSubSubContainerXmlElementName) ) {
                                containerElement = subContainerElements.item(i);
                                break;
                            }
                        }
                    }
                }
                else {
                    containerElement = doc.getElementsByTagName(optionalSubSubContainerXmlElementName).item(0);
                }
            }


            NodeList repeatedElements = containerElement.getChildNodes();

            for (int i=0; i<repeatedElements.getLength(); i++) {
                if (repeatedElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    if (repeatedElements.item(i).getNodeName().toUpperCase().startsWith(repeatedContainerXmlElementNameStartsWith.toUpperCase())) {
                        //valuesFromXml.add(repeatedElements.item(i).getFirstChild().getNodeValue());
                        Node currentContainerElement = repeatedElements.item(i);

                        currentHashMap = new HashMap<String,String>();

                        for (int j=0; j<currentContainerElement.getChildNodes().getLength(); j++) {
                            Node currentFieldElement = currentContainerElement.getChildNodes().item(j);
                            if (currentFieldElement.getNodeType() == Node.ELEMENT_NODE) {
                                //Log.i( "MyTest", "---- " +
                                //        currentFieldElement.getNodeName() + " : " +
                                //        currentFieldElement.getFirstChild().getNodeValue() );
                                currentHashMap.put(currentFieldElement.getNodeName(),currentFieldElement.getFirstChild().getNodeValue());
                            }
                        }

                        result.add(currentHashMap);

                    }
                }
            }

        } catch (Exception e)   {
            throw new Exception("Failed to read data.",e);
        }
        finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }


        return result;
    }

}


