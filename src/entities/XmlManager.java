package entities;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlManager {
    private final String FILE = "./src/Utils/saved_configuration.xml";

    // DOM
    Document doc;
    Transformer transformer;

    public static void main(String[] args) {
    	System.out.println("eNTRA");
    	XmlManager mng = new XmlManager();
    	mng.initialize();
    	System.out.println("inicializado");
    	ArrayList<SavedConfiguration> list = mng.getConfigurationList();
		for(SavedConfiguration item: list) {
			System.out.println(item.getName());
		}
    }
    public XmlManager(){
        this.initialize();
    }
    /*
    // Checks if the file exists
    private boolean exists(){
        boolean existe = false;
        try {
            context.openFileInput(FILE_NAME);
            context.openFileInput(FILE_NAME).close();
            existe = true;
        } catch (FileNotFoundException e) {
            existe =  false;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existe;
    }
*/
    
    private void initialize() {
    	if(exists()) {
    		try {
        		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        		DocumentBuilder db = dbf.newDocumentBuilder();
        		this.doc = db.parse(new File(FILE));
        	} catch(Exception e) {
        		e.printStackTrace();
        	}
        	try {
        		// Output configuration
        		this.transformer = TransformerFactory.newInstance().newTransformer();
        		this.transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        		this.transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        		} catch(Exception e) {
        			System.out.println(e.getMessage());
        		}
    	} else {
    		System.out.println("crea xml");
    		createXML();
    		initialize();
    	}
    	
    }
    private boolean exists() {
    	File f = new File(FILE);
    	if(f.isFile()) {
    		return true;
    	} else {
    		return false;
    	}
    }
    private void createXML() {
    	 try {
             OutputStreamWriter alarm = new OutputStreamWriter(new FileOutputStream(FILE));
             alarm.write("<configurations></configurations>");
             alarm.close();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
    }

    //	Método que añade un videojuego al XML
    private void addConfiguration(SavedConfiguration configuration) {
        // Root node
        Node nodeConfiguration = this.doc.createElement("configuration");
        // Name node
        Node nodeName = this.doc.createElement("name");
        nodeName.appendChild(this.doc.createTextNode(configuration.getName()));
        // Ip node
        Node nodeIp = this.doc.createElement("ip");
        nodeIp.appendChild(this.doc.createTextNode(configuration.getIp()));
        // Path node
        Node nodePath = this.doc.createElement("path");
        nodePath.appendChild(this.doc.createTextNode(configuration.getPath()));
        // Port node
        Node nodePort = this.doc.createElement("port");
        nodePort.appendChild(this.doc.createTextNode(configuration.getPort()));
        // Username node
        Node nodeUsername = this.doc.createElement("username");
        nodeUsername.appendChild(this.doc.createTextNode(configuration.getUsername()));
        // Password node
        Node nodePassword = this.doc.createElement("password");
        nodePassword.appendChild(this.doc.createTextNode(configuration.getPassword()));
        // Adding all nodes to root node
        nodeConfiguration.appendChild(nodeName);
        nodeConfiguration.appendChild(nodeIp);
        nodeConfiguration.appendChild(nodePort);
        nodeConfiguration.appendChild(nodePath);
        nodeConfiguration.appendChild(nodeUsername);
        nodeConfiguration.appendChild(nodePassword);
        // Append configuration to <configuration> root node
        this.doc.getLastChild().appendChild(nodeConfiguration);
    }
    //	Gets configuration node list
    private NodeList getNodeList() {
        return this.doc.getElementsByTagName("configuration");
    }
    // Returns ArrayList of SavedConfiguration objects
    public ArrayList<SavedConfiguration> getConfigurationList(){
        NodeList nList = this.getNodeList();
        ArrayList<SavedConfiguration> configurations = new ArrayList<SavedConfiguration>();
        SavedConfiguration configuration;
        for(int i = 0; i < nList.getLength(); i++){
            // Recorro la lista y obtengo el nodo en el que estoy actualmente
            configuration = new SavedConfiguration();
            Node node = nList.item(i);
            Element element = (Element)node;
            configuration.setName(element.getElementsByTagName("name").item(0).getTextContent());
            configuration.setIp(element.getElementsByTagName("ip").item(0).getTextContent());
            configuration.setPath(element.getElementsByTagName("path").item(0).getTextContent());
            configuration.setPort(element.getElementsByTagName("port").item(0).getTextContent());
            configuration.setUsername(element.getElementsByTagName("username").item(0).getTextContent());
            configuration.setPassword(element.getElementsByTagName("password").item(0).getTextContent());
            configurations.add(configuration);
        }
        return configurations;
    }
    // Saves file
    private void save(){
        DOMSource src = new DOMSource(this.doc);
        StreamResult file = new StreamResult(new File(FILE));
        try {
            this.transformer.transform(src, file);
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        }
    }
    // Add configuration
    public void newConfiguration(SavedConfiguration configuration){
        this.addConfiguration(configuration);
        this.save();
    }

   
    private Node getMatchingNode(String name) {
        NodeList nList = this.getNodeList();
        Node nodeTitulo = null;
        for(int cont = 0; cont < nList.getLength(); cont++ ) {
            Node node = nList.item(cont);
            Element element = (Element)node;
            nodeTitulo = element.getElementsByTagName("name").item(0);
            String thisTitulo = nodeTitulo.getTextContent();
            // Asigna el nuevo título
            if(thisTitulo.equals(name)) {
                return nodeTitulo;
            }
        }
        return nodeTitulo;
    }
    /*
     * Deletes configuration node from given name
     */
    private void deleteConfiguration(String name) {
    	// Gets configuration Node that matches given name
        Element elemento = (Element) this.getMatchingNode(name).getParentNode();
        // Removes matching <configuration></configuration>
        elemento.getParentNode().removeChild(elemento);
    }
    
    /*
     * Deletes given configuration and saves changes
     */
    public void deleteConfiguration(SavedConfiguration configuration){
    	deleteConfiguration(configuration.getName());
        save();
    }
}