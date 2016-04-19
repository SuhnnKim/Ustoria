package util;

import model.Story;
import wox.serial.Easy;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by AshirwadTank on 4/11/2016.
 */
public class DataSerializer {


    public void serializeXML(Story myStory) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Story.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(myStory, new File("/Users/liuwei/Desktop/Temp/" + myStory.getTitle() + ".xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public void serializeXMLWithWox(Story myStory) {


        Easy.save(myStory,"/Users/liuwei/Desktop/Test/"+myStory.getTitle()+".xml");
    }


    public Story deserializeXMLWithWox(File myFile){

        Story myStory = (Story) Easy.load("/Users/liuwei/Desktop/Temp/"+myFile.getName());
        return  myStory;
    }

    public Story deSerializeXML(File myFile) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Story.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Story myStory = (Story) jaxbUnmarshaller.unmarshal(new File("/Users/liuwei/Desktop/Temp/" + myFile.getName()));

            return myStory;
        } catch (JAXBException e) {
            e.printStackTrace();


        }
        return null;
    }
}
