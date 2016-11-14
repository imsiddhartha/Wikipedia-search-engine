import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TitleIndex extends DefaultHandler {

	boolean spage = false;
	boolean stitle = false;
   boolean sid = false;
   boolean srevision = false;
   public static String title,doc_id;
   static long pagecount=0;
   static StringBuilder ltitle=new StringBuilder();
	
   @Override
   public void startElement(String uri, String localName,String qName, 
           Attributes attributes) throws SAXException {

	//System.out.println("Start Element :" + qName);
	try
	{
		if (qName.equalsIgnoreCase("page")) {
			spage=true;
		}

		if (qName.equalsIgnoreCase("title")) {
			stitle=true;	
		}
		if (qName.equalsIgnoreCase("revision")) {
			srevision=true;
		}

		if (qName.equalsIgnoreCase("id")) {
			sid=true;
		}
//
//		if (qName.equalsIgnoreCase("text")) {
//			//stext=true;
//
//		}
	}
	catch(Exception e){
		System.out.println("Exception in start ele");
		e.printStackTrace();
	}
}


   @Override
   public void endElement(String uri, 
   String localName, String qName) throws SAXException {
	  try {
		
		  if (qName.equalsIgnoreCase("page")) {
				spage=false;
				// if(pagecount%30==0)
				{
					File file=new File("TitleInd");
					if(!file.exists())
					{
						file.createNewFile();
					}
					FileWriter fw=new FileWriter(file.getAbsoluteFile(),true);
					BufferedWriter bw=new BufferedWriter(fw);
					bw.write(ltitle.toString());
					bw.close();
					//System.out.println(ltitle.toString());
					ltitle.setLength(0);
				}
				if(pagecount%100000==0)
					System.out.println(pagecount);
				pagecount++;
			}
		  else  if (qName.equalsIgnoreCase("revision")) {
				srevision=false;
			}

		  else if (qName.equalsIgnoreCase("title")) {
				stitle=false;	
			}

		  else if (qName.equalsIgnoreCase("id")) {
				sid=false;
			}
			 else if(qName.equalsIgnoreCase("mediawiki"))
		      {
		      	//System.out.println(ltitle);
				 if(ltitle.length()>0)
				 {
				 	File file=new File("TitleInd");
					if(!file.exists())
					{
						file.createNewFile();
					}
					FileWriter fw=new FileWriter(file.getAbsoluteFile(),true);
					BufferedWriter bw=new BufferedWriter(fw);
					bw.write(ltitle.toString());
					ltitle.setLength(0);
				 }
				 System.out.println(pagecount);
		      }
		  
	} catch (Exception e) {
		// TODO: handle exception
	}
   }

   @Override
   public void characters(char ch[], 
      int start, int length) throws SAXException {
	   
	   try {
		   
		   
		   if (stitle) {
				title=new String(ch, start, length);
			}

			if (sid && !srevision) {
				doc_id=new String(ch, start, length);
				
				doc_id=doc_id.toLowerCase();
				
				ltitle.append(doc_id+" "+title+'\n');
				
			}
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	   
	   
   }
}