import java.io.*;
import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class main_file
{
	//java -Xmx3072M -Xms2048M main_file
	public static int page_count=0;
	//public static Set <String> stop_set=new HashSet<String>();
	public static void main(String argv[])
	{
		long start = System.currentTimeMillis();
		//StopRem.initialize();
	//	for(int i=0;i<437;i++)
      //{
        //stop_set.add(StopRem.stop_words[i]);
        
      //}

	  SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
      try 
      {
        
          SAXParser saxParser = saxParserFactory.newSAXParser();
          //ReadXMLFile handler = new ReadXMLFile();
         
      
        // String input = ""+argv[0]+"";
        // String output=""+argv[1]+"";
        
        //System.out.println(input+" "+output);
          System.out.println("Called");
         
         //saxParser.parse("wiki-search-small.xml", handler);
         // saxParser.parse("wiki-search-small.xml", new TitleIndex());
         //saxParser.parse("enwiki-latest-pages-articles.xml", handler);
        saxParser.parse("enwiki-latest-pages-articles.xml", new TitleIndex());
        //saxParser.parse(input, handler);
         // saxParser.parse("input1.xml", handler);
     // System.out.println(""+page_count);
      //   String output="index"+ReadXMLFile.index_count;
      //    if(page_count%25000!=0)
      //      Save_Index.save_index(output);

      // CreateDenseIndex.create_dense_ind();
      
      System.out.println("ENDED");
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " ms");
        // System.out.println("numm pages"+page_count);
      }
      catch (Exception e) 
      {
          e.printStackTrace();
      }


	}
}
