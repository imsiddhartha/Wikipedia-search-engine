import java.util.*;

public class StopRem
{
	//public static String stop_words[]= {"aw","awa","ba","ca","da","ea","fa","ga","ha","ja","ka","ma","na","pa","qa","ra","sa","ta","wa","cam","nam","coor","br","com","bac","che","chi","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","aw","av","au","at",,"ar","an","af","aft","al","acc","acco","abo","aa","a2","ab","abb","aca","acr","ag","bu","coord","gr","com","tr","td","nbsp","http","https","www","about","above","across","after","again","against","all","almost","alone","along","already","also","although","always","among","an","and","another","any","anybody","anyone","anything","anywhere","are","area","areas","around","as","ask","asked","asking","asks","at","away","b","back","backed","backing","backs","be","became","because","become","becomes","been","before","began","behind","being","beings","best","better","between","big","both","but","by","c","came","can","cannot","case","cases","certain","certainly","clear","clearly","come","could","d","did","differ","different","differently","do","does","done","down","down","downed","downing","downs","during","e","each","early","either","end","ended","ending","ends","enough","even","evenly","ever","every","everybody","everyone","everything","everywhere","f","face","faces","fact","facts","far","felt","few","find","finds","first","for","four","from","full","fully","further","furthered","furthering","furthers","g","gave","general","generally","get","gets","give","given","gives","go","going","good","goods","got","great","greater","greatest","group","grouped","grouping","groups","h","had","has","have","having","he","her","here","herself","high","high","high","higher","highest","him","himself","his","how","however","i","if","important","in","interest","interested","interesting","interests","into","is","it","its","itself","j","just","k","keep","keeps","kind","knew","know","known","knows","l","large","largely","last","later","latest","least","less","let","lets","like","likely","long","longer","longest","m","made","make","making","man","many","may","me","member","members","men","might","more","most","mostly","mr","mrs","much","must","my","myself","n","necessary","need","needed","needing","needs","never","new","new","newer","newest","next","no","nobody","non","noone","not","nothing","now","nowhere","number","numbers","o","of","off","often","old","older","oldest","on","once","one","only","open","opened","opening","opens","or","order","ordered","ordering","orders","other","others","our","out","over","p","part","parted","parting","parts","per","perhaps","place","places","point","pointed","pointing","points","possible","present","presented","presenting","presents","problem","problems","put","puts","q","quite","r","rather","really","right","right","room","rooms","s","said","same","saw","say","says","second","seconds","see","seem","seemed","seeming","seems","sees","several","shall","she","should","show","showed","showing","shows","side","sides","since","small","smaller","smallest","so","some","somebody","someone","something","somewhere","state","states","still","still","such","sure","t","take","taken","than","that","the","their","them","then","there","therefore","these","they","thing","things","think","thinks","this","those","though","thought","thoughts","three","through","thus","to","today","together","too","took","toward","turn","turned","turning","turns","two","u","under","until","up","upon","us","use","used","uses","v","very","w","want","wanted","wanting","wants","was","way","ways","we","well","wells","went","were","what","when","where","whether","which","while","who","whole","whose","why","will","with","within","without","work","worked","working","works","would","x","y","year","years","yet","you","young","younger","youngest","your","yours","z"};
	public static String stop_words[]= {"ac","aw","awa","ba","ca","da","ea","fa","ga","ha","ja","ka","ma","na","pa","qa","ra","sa","ta","wa","cam","nam","coor","br","com","bac","che","chi","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","aw","av","au","at","ar","an","af","aft","al","acc","acco","abo","aa","a2","ab","abb","aca","acr","ag","bu","coord","gr","com","tr","td","nbsp","http","https","www","about","above","across","after","again","against","all","almost","alone","along","already","also","although","always","among","an","and","another","any","anybody","anyone","anything","anywhere","are","area","areas","around","as","ask","asked","asking","asks","at","away","b","back","backed","backing","backs","be","became","because","become","becomes","been","before","began","behind","being","beings","best","better","between","big","both","but","by","c","came","can","cannot","case","cases","certain","certainly","clear","clearly","come","could","d","did","differ","different","differently","do","does","done","down","down","downed","downing","downs","during","e","each","early","either","end","ended","ending","ends","enough","even","evenly","ever","every","everybody","everyone","everything","everywhere","f","face","faces","fact","facts","far","felt","few","find","finds","first","for","four","from","full","fully","further","furthered","furthering","furthers","g","gave","general","generally","get","gets","give","given","gives","go","going","good","goods","got","great","greater","greatest","group","grouped","grouping","groups","h","had","has","have","having","he","her","here","herself","high","high","high","higher","highest","him","himself","his","how","however","i","if","important","in","interest","interested","interesting","interests","into","is","it","its","itself","j","just","k","keep","keeps","kind","knew","know","known","knows","l","large","largely","last","later","latest","least","less","let","lets","like","likely","long","longer","longest","m","made","make","making","man","many","may","me","member","members","men","might","more","most","mostly","mr","mrs","much","must","my","myself","n","necessary","need","needed","needing","needs","never","new","new","newer","newest","next","no","nobody","non","noone","not","nothing","now","nowhere","number","numbers","o","of","off","often","old","older","oldest","on","once","one","only","open","opened","opening","opens","or","order","ordered","ordering","orders","other","others","our","out","over","p","part","parted","parting","parts","per","perhaps","place","places","point","pointed","pointing","points","possible","present","presented","presenting","presents","problem","problems","put","puts","q","quite","r","rather","really","right","right","room","rooms","s","said","same","saw","say","says","second","seconds","see","seem","seemed","seeming","seems","sees","several","shall","she","should","show","showed","showing","shows","side","sides","since","small","smaller","smallest","so","some","somebody","someone","something","somewhere","state","states","still","still","such","sure","t","take","taken","than","that","the","their","them","then","there","therefore","these","they","thing","things","think","thinks","this","those","though","thought","thoughts","three","through","thus","to","today","together","too","took","toward","turn","turned","turning","turns","two","u","under","until","up","upon","us","use","used","uses","v","very","w","want","wanted","wanting","wants","was","way","ways","we","well","wells","went","were","what","when","where","whether","which","while","who","whole","whose","why","will","with","within","without","work","worked","working","works","would","x","y","year","years","yet","you","young","younger","youngest","your","yours","z"};
//Set<String> stop_set=new HashSet<String>();
public static Set <String> stop_set=new HashSet<String>();

public static void initialize()
{
	
	for(int i=0;i<stop_words.length;i++)
	      {
	      	//System.out.println(stop_words[i]);
	        stop_set.add(StopRem.stop_words[i]);
	        
	      }
}

//for(int i=0;i<305;i++)


  static void in_title(String str)
{
	//space pe spilt maar k stop word se check krwao

	str=str.trim();
    
    if(str.length()==0||str.isEmpty())
        return;
    //System.out.println(str+"  "+stop_set.contains(str));
    // if(stop_set.contains(str))
    // {
    // 	//System.out.println(str+" is a stop word");
    // 	return;
    // }
    // else
    {
	   	str=str.toLowerCase();
	    if(ReadXMLFile.index.get(str)==null)
	    {
	    //	System.out.println("New index entry for "+str);
	        HashMap<String,Obj> lmap=new HashMap<String,Obj>();

	        lmap.put(ReadXMLFile.doc_id, new Obj());
	        ReadXMLFile.index.put(str, lmap);
	        //System.out.println("Size: "+index.size());
	     
	    }
	    
	    //index already contains string, but doc id is different
	    //if(ReadXMLFile.index.get(str)==null||!ReadXMLFile.index.get(str).containsKey(ReadXMLFile.doc_id))
	    if(!ReadXMLFile.index.get(str).containsKey(ReadXMLFile.doc_id))
	    {
	    	// System.out.println("Entry for "+str+" already exist but doc_id is new");
	        ReadXMLFile.index.get(str).put(ReadXMLFile.doc_id, new Obj());
	        
	    }
	    ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).title++;         

    }
    // else
    // {
    // 	System.out.println("both str and doc_idare already present");
    // 	
    // }
    
//System.out.println("title m word ccount " +ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).title);
}
    static void in_info_box(String str)
{
	
	str=str.trim();
    
    if(str.length()==0||str.isEmpty())
        return;

    str=str.toLowerCase();
    
    if(stop_set.contains(str))
    {
    	//System.out.println(str+" is a stop word");
    	return;
    }
    else
    {

       //System.out.println("info_box m word is = "+str);
        //System.out.println(str);
        Stemmer s= new Stemmer();
    	s.add(str.toCharArray(),str.length());
    	s.stem();
    	str=s.toString();
    	
    	//if(str.length()<3)		//br,na,eu etc focat k words aa rhe h par dates m prob aayegi fir
    	//		return;
    	//System.out.println(str);
    //System.out.println("Info box m string h "+str);
	    if(ReadXMLFile.index.get(str)==null)
	    {
	        HashMap<String,Obj> lmap=new HashMap<String,Obj>();

	        lmap.put(ReadXMLFile.doc_id, new Obj());
	        ReadXMLFile.index.put(str, lmap);
	    }
	      //index already contains string, but doc id is different
	    if(!ReadXMLFile.index.get(str).containsKey(ReadXMLFile.doc_id))
	    {
	        ReadXMLFile.index.get(str).put(ReadXMLFile.doc_id, new Obj());
	   
			// System.out.println(str+"  ka categories m word ccount " +ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).categories);
		}

		ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).info_box++;
		 // System.out.println(ReadXMLFile.doc_id+" "+str+" "+ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).info_box);  
		//System.out.println(str+"  ka m word ccount in doc id "+ReadXMLFile.doc_id+" "+ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).info_box);
    }

   		 //System.out.println(str+" occured in categories entered in index for doc id= "+ReadXMLFile.doc_id);
} 
  static void in_categories(String str)
{
	//System.out.println("In Categories");
	str=str.trim();
     if(str.length()==0||str.isEmpty())
        return;

    str=str.toLowerCase();
    
    if(stop_set.contains(str))
    {
    	//System.out.println(str+" is a stop word");
    	return;
    }
    else
    {
    	
        Stemmer s= new Stemmer();
    	s.add(str.toCharArray(),str.length());
    	s.stem();
    	str=s.toString();
    	//if(str.length()<3)		//br,na,eu etc focat k words aa rhe h
    	//		return;
    	
	    if(ReadXMLFile.index.get(str)==null)
	    {
	        HashMap<String,Obj> lmap=new HashMap<String,Obj>();

	        lmap.put(ReadXMLFile.doc_id, new Obj());
	        ReadXMLFile.index.put(str, lmap);
	    }
	      //index already contains string, but doc id is different
	    if(!ReadXMLFile.index.get(str).containsKey(ReadXMLFile.doc_id))
	    {
	        ReadXMLFile.index.get(str).put(ReadXMLFile.doc_id, new Obj());
	   
			// System.out.println(str+"  ka categories m word ccount " +ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).categories);
		}
		ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).categories++;
             // System.out.println(ReadXMLFile.doc_id+" "+str+" "+ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).categories);    
	 }
   		 //System.out.println(str+" occured in categories entered in index for doc id= "+ReadXMLFile.doc_id);
    }

  static void in_ref(String str)
{
	
	str=str.trim();
    
    if(str.length()==0||str.isEmpty())
        return;
str=str.toLowerCase();
     if(stop_set.contains(str))
    {
    	
    	return;
    }
    else
    {
    	
    	// System.out.println("Ref m word is = "+str);
    	Stemmer s= new Stemmer();
    	s.add(str.toCharArray(),str.length());
    	s.stem();
    	str=s.toString();
    	//if(str.length()<3)		//br,na,eu etc focat k words aa rhe h
    	//		return;

	    if(ReadXMLFile.index.get(str)==null)
	    {
	        HashMap<String,Obj> lmap=new HashMap<String,Obj>();

	        lmap.put(ReadXMLFile.doc_id, new Obj());
	        ReadXMLFile.index.put(str, lmap);
	    }
	      //index already contains string, but doc id is different
	    if(!ReadXMLFile.index.get(str).containsKey(ReadXMLFile.doc_id))
	    {
	        ReadXMLFile.index.get(str).put(ReadXMLFile.doc_id, new Obj());
	   
			// System.out.println(str+"  ka categories m word ccount " +ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).categories);
		}
		ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).ref++;
 // System.out.println(ReadXMLFile.doc_id+" "+str+" "+ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).ref);    
	}

   		 //System.out.println(str+" occured in categories entered in index for doc id= "+ReadXMLFile.doc_id);
    }   
 
   static void in_body(String str)
{
	
	str=str.trim();
    
    if(str.length()==0||str.isEmpty())
        return;
      str=str.toLowerCase();
    if(stop_set.contains(str))
    {
    	//System.out.println(str+" is a stop word");
    	// if(str.equals("ac"))
    	// 	System.out.println(str+" is a stop word in refernce");
    	return;
    }
    else
    {
      // if(str.equals("ac"))
    		// System.out.println(str+" ELSE m is a stop word in refernce");
        //System.out.println(str);
        // System.out.println("Body m word is = "+str);
        Stemmer s= new Stemmer();
    	s.add(str.toCharArray(),str.length());
    	s.stem();
    	str=s.toString();
    	
    	//if(str.length()<3)		//br,na,eu etc focat k words aa rhe h par dates m prob aayegi fir
    	//		return;
    	//System.out.println(str);
    //System.out.println("Info box m string h "+str);
	    if(ReadXMLFile.index.get(str)==null)
	    {
	        HashMap<String,Obj> lmap=new HashMap<String,Obj>();

	        lmap.put(ReadXMLFile.doc_id, new Obj());
	        ReadXMLFile.index.put(str, lmap);
	        //System.out.print(str+" was not in index of docid= "+ReadXMLFile.doc_id);
	    }
	      //index already contains string, but doc id is different
	    if(!ReadXMLFile.index.get(str).containsKey(ReadXMLFile.doc_id))
	    {
	        ReadXMLFile.index.get(str).put(ReadXMLFile.doc_id, new Obj());
	   		  //System.out.print(str+" was in index but not in docid= "+ReadXMLFile.doc_id);
			// System.out.println(str+"  ka categories m word ccount " +ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).categories);
		}
		ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).body++;
// System.out.println(ReadXMLFile.doc_id+" "+str+" "+ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).body);
		//System.out.println(" "+str+" count = "+ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).body);
    }
     // System.out.println(ReadXMLFile.doc_id+" "+str+" "+ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).body);    
   		 //System.out.println(str+" occured in categories entered in index for doc id= "+ReadXMLFile.doc_id);
}
 
 static void in_extLinks(String str)
{
	
	str=str.trim();
    
    if(str.length()==0||str.isEmpty())
        return;
      str=str.toLowerCase();
    if(stop_set.contains(str))
    {
    	return;
    }
    else
    {
    	//System.out.println("External Links m word is = "+str);
        Stemmer s= new Stemmer();
    	s.add(str.toCharArray(),str.length());
    	s.stem();
    	str=s.toString();
    	
    	//if(str.length()<3)		//br,na,eu etc focat k words aa rhe h par dates m prob aayegi fir
    	//		return;
    	//System.out.println(str);
    //System.out.println("Info box m string h "+str);
	    if(ReadXMLFile.index.get(str)==null)
	    {
	        HashMap<String,Obj> lmap=new HashMap<String,Obj>();

	        lmap.put(ReadXMLFile.doc_id, new Obj());
	        ReadXMLFile.index.put(str, lmap);
	    }
	      //index already contains string, but doc id is different
	    if(!ReadXMLFile.index.get(str).containsKey(ReadXMLFile.doc_id))
	    {
	        ReadXMLFile.index.get(str).put(ReadXMLFile.doc_id, new Obj());
	   
			// System.out.println(str+"  ka categories m word ccount " +ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).categories);
		}
		ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).ext_links++;	
 // System.out.println(ReadXMLFile.doc_id+" "+str+" "+ReadXMLFile.index.get(str).get(ReadXMLFile.doc_id).ext_links);    
    }

   		 //System.out.println(str+" occured in categories entered in index for doc id= "+ReadXMLFile.doc_id);
}

   /*
	
	public int  body;
	
	
	public int ext_links;
	
   */
}