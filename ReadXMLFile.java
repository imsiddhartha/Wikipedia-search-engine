import java.util.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class ReadXMLFile extends DefaultHandler 
{
	public static Map<String, HashMap<String, Obj>> index = new TreeMap<String,HashMap<String,Obj>>();   

	boolean spage = false;
	boolean stitle = false;
   boolean stext = false;
   boolean sid = false;
   boolean srevision = false;

   boolean sinfo_box = false;
   boolean scategories = false;
   boolean sext_links = false;
   boolean sref = false;
static int index_count=0;
   public static String title,doc_id;
	StringBuilder ltitle=new StringBuilder();	//l-local strig for titile
	StringBuilder lbody=new StringBuilder();
	StringBuilder ltext=new StringBuilder();
	StringBuilder linfo_box=new StringBuilder();
	StringBuilder lcategories=new StringBuilder();
	StringBuilder lext_links=new StringBuilder();;
	StringBuilder lref=new StringBuilder();
	
	public void startElement(String uri, String localName,String qName, 
                Attributes attributes) throws SAXException {

		//System.out.println("Start Element :" + qName);
		try
		{
			if (qName.equalsIgnoreCase("page")) {
				main_file.page_count++;
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

			if (qName.equalsIgnoreCase("text")) {
				stext=true;

			}
		}
		catch(Exception e){
			System.out.println("Exception in start ele");
			e.printStackTrace();
		}
	}





	public void characters(char ch[], int start, int length) throws SAXException 
	{
		try
		{
			if (stitle) {
				title=new String(ch, start, length);
				
				//title=title.toLowerCase();
				

	            //title=ltitle.toString();
	            //title=title.trim();
				//System.out.println("title= "+title);
				
				//System.out.println("First Name : " + ; String(ch, start, length));
				//bfname = false;
			}

			if (sid && !srevision) {
				doc_id=new String(ch, start, length);
				
				//object.body.toLowerCase();
				doc_id=doc_id.toLowerCase();
				//System.out.println(length);
				// title comes earilier than id so calling from here
				for(int i=0;i<title.length();i++)
          		{	
		            char current = title.charAt(i);
		            if((current>=65&&current<=90)||(current>=97&&current<=122)||(current>=48&&current<=57))
		            {
		              ltitle.append(current);
		            }
		            else 				//ya tou har space pe cal kro ya sp character pe refer to function
		            {
		             	StopRem.in_title(ltitle.toString());
		             	ltitle.setLength(0);
		              //.handle_title(ltitle.toString());
		              //loc_title.setLength(0);
		            }
	            }
	            if(ltitle.length()!=0)
	            {
	            	StopRem.in_title(ltitle.toString());
	            	ltitle.setLength(0);
	            }
				//System.out.println("ID = "+doc_id);
				//stop words removal
				//special characters
				//System.out.println("Last Name : " + new String(ch, start, length));
				//blname = false;
			}

			if (stext) {
				//object.info_box=new String(ch, start, length);

				ltext.append(ch, start, length);
				//System.out.println(length);
				//object.info_box.toLowerCase();
				//object.info_box=rem(object.info_box);
				//String test=ltext.toString();
				//test=test.toLowerCase();
				//System.out.println("text= "+test);
				//stop words removal
				//special characters
				//System.out.println("length= "+ltest.length());
				//bnname = false;
			}
		}catch(Exception e)
		{
			System.out.println("Exception in readoing Elements");
			e.printStackTrace();
		}	
	}




	public void endElement(String uri, String localName,
		String qName) throws SAXException 
	{
		//System.out.println("End Element :" + qName);
		try
		{
			if (qName.equalsIgnoreCase("page")) {
				spage=false;
				if(main_file.page_count%25000==0)
				{
					Save_Index.save_index("index"+index_count);
					index_count++;
				}
				//System.out.println(main_file.page_count);
			}
			if (qName.equalsIgnoreCase("revision")) {
				srevision=false;
			}

			if (qName.equalsIgnoreCase("title")) {
				stitle=false;	
			}

			if (qName.equalsIgnoreCase("id")) {
				sid=false;
			}

			if (qName.equalsIgnoreCase("text")) {
				stext=false;
				//String test=ltext.toString();
				//System.out.println(test);
				parse_text();
				
				//System.out.println("------------References-------------------------");
				//System.out.println(lref);
				//System.out.println("------------Category-------------------------");
				//System.out.println(lcategories);
				//System.out.println("End ele \n Length= "+ltext.length());
				ltext.setLength(0);
				lcategories.setLength(0);
				ltitle.setLength(0);	//l-local strig for titile
				lbody.setLength(0);
				linfo_box.setLength(0);
				lext_links.setLength(0);
				lref.setLength(0);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in End ele");
			e.printStackTrace();
		}	
		//System.out.println("End Element :" + qName);

	}
	public void parse_text()
	{

		try
		{

			char ch,ch1;
			int len = ltext.length();
			int i;
			//System.out.println(ltext.toString());
			for(i=0;i<len;i++)
			{
				if(ltext.charAt(i)=='{')
				{
					//System.out.println("char is {");
					if(i+9<len && ltext.substring(i,i+9).equalsIgnoreCase("{{infobox"))
					{
						i=i+9;
						//System.out.println("-----------INFO box-----------");
						int count_par=2;
						boolean infoflag=true,addtext=false;
						while(i<len && infoflag)
						{
							ch=ltext.charAt(i);
							
							if(ch=='{')
								count_par++;
							else if(ch=='}')
								count_par--;
							if(count_par==0 || i>=len )
							{
								infoflag=false;
								i++;
								break;
							}
							if(i+1 < len && ltext.charAt(i)=='[' && ltext.charAt(i+1)=='[' )
							{
								addtext=true;
								// i=i+2;
								//ch=ltext.charAt(i);
								// while(ch!=']' && ltext.charAt(i+1)!=']')
								// {
								// 	// System.out.print(ch);
									
			     //                	i++;
			     //                	ch=ltext.charAt(i);
								// }
							}
							else if(i+1 <len && ltext.charAt(i)==']' && ltext.charAt(i+1)==']')
							{
								addtext=false;
								StopRem.in_info_box(linfo_box.toString());
			                    linfo_box.setLength(0);
							}
							if(addtext)
							{
								//if( (ch>=65&&ch<=90)||(ch>=97&&ch<=122) || (ch>=48&&ch<=57) )
									if( (ch>=65&&ch<=90)||(ch>=97&&ch<=122))
	                  				{
					             		linfo_box.append(ch);
	                		    	}
			                    	else
			                    	{
			                    		//System.out.println("Word sent "+linfo_box);
			                    		StopRem.in_info_box(linfo_box.toString());
			                    		linfo_box.setLength(0);
			                    	}
							}
							i++;
						}
						if(linfo_box.length()!=0)
						{
							StopRem.in_info_box(linfo_box.toString());
			                linfo_box.setLength(0);
						}
					}
					if(i+6<len&&ltext.substring(i, i+6).equalsIgnoreCase("{{cite"))
					{
						//System.out.println("------------cite-------------------------");
						i=i+6;
						boolean img_flag=true;
						int count=0;
						if(i>=len)
	                    		break;
						
						//System.out.println("CAtegory m ky h "+ch);
						while(img_flag && i<len )
						{
							if(ltext.charAt(i)=='[')
								count++;
							else if(ltext.charAt(i)==']')
								count--;
							if(count==-2)
							{
								i++;
								img_flag=false;
								break;
							}
	                    	i++;
	                    	if(i>=len)
	                    		break;
						}
					}	
						if(i+6<len&&ltext.substring(i, i+6).equalsIgnoreCase("{{coord"))
					{
						//System.out.println("------------coord-------------------------");
						i=i+6;
						boolean img_flag=true;
						int count=0;
						if(i>=len)
	                    		break;
						
						//System.out.println("CAtegory m ky h "+ch);
						while(img_flag && i<len )
						{
							if(ltext.charAt(i)=='[')
								count++;
							else if(ltext.charAt(i)==']')
								count--;
							if(count==-2)
							{
								i++;
								img_flag=false;
								break;
							}
	                    	i++;
	                    	if(i>=len)
	                    		break;
						}
					}	
				}	
				else if(ltext.charAt(i)=='=')
				{
					//System.out.println("char is =");
					if( (i+16<len&&(ltext.substring(i, i+16).equalsIgnoreCase("== References =="))) || (i+16<len&&(ltext.substring(i, i+16).equalsIgnoreCase("===References===")))||(i+14<len&&(ltext.substring(i, i+14).equalsIgnoreCase("==References==")))||(i+18<len&&(ltext.substring(i, i+18).equalsIgnoreCase("=== References ==="))))
					{
						//System.out.println("-----------------References--------------");
							if( (ltext.substring(i, i+16).equalsIgnoreCase("== References ==")) || (ltext.substring(i, i+16).equalsIgnoreCase("===References===")) )
							i=i+16;
						else if(ltext.substring(i, i+14).equalsIgnoreCase("==References=="))	
							i+=14;
						else if(1+18<len && ltext.substring(i, i+18).equalsIgnoreCase("=== References ==="))	
							i+=18;
						if(i>=len)
	                    		break;
							ch=ltext.charAt(i);
						//System.out.println(ch);		
						//ch1=ltext.charAt(i+1);
						//System.out.println(ch1);		
						while(ch!='='  && i+2< len)
						{
									
								if(i+11<len&&ltext.substring(i, i+11).equalsIgnoreCase("[[Category:"))
							{
										//System.out.println("Category in References");
										//System.out.println("------------Category-------------------------");
										i=i+11;
										if(i>=len)
					                    		break;
										ch=ltext.charAt(i);
										//System.out.println("CAtegory m ky h "+ch);
										while(ch!=']' && i<len )
										{
											//if( (ch>=65&&ch<=90)||(ch>=97&&ch<=122) || (ch>=48&&ch<=57) )
											if( (ch>=65&&ch<=90)||(ch>=97&&ch<=122))
					                  		{
												//System.out.println(ch);
					                    		
					                    		lcategories.append(ch);
					                    	}
					                    	else
					                    	{
					                    		//System.out.println("Word sent "+lcategories);
					                    		StopRem.in_categories(lcategories.toString());
					                    		lcategories.setLength(0);
					                    	}
					                    	i++;
					                    	if(i>=len)
					                    		break;
					                    		ch=ltext.charAt(i);
										}
										if(lcategories.length()!=0)
										{
											StopRem.in_categories(lcategories.toString());
											lref.setLength(0);
										}
										
								}
							if(i+6<len&&(ltext.substring(i, i+6).equalsIgnoreCase("[http:"))) //if [http then skip letters till space occures	
							{
								i=i+6;
								if(i>=len)
	                    			break;
								ch=ltext.charAt(i);
							//	System.out.println("http: k baAd"+ch);
								while((ch!=' ' || ch!=']') && i< len )
								{
									i++;
									if(i<len)
										ch=ltext.charAt(i);
									//ch1=ltext.charAt(i);
								}			
							}
							//if((ch>=65&&ch<=90)||(ch>=97&&ch<=122) || (ch>=48&&ch<=57))
							if((ch>=65&&ch<=90)||(ch>=97&&ch<=122))
	                  		{
								//System.out.println(ch);
	                    		lref.append(ch);
	                    	}
	                    	else
	                    	{
	                    		StopRem.in_ref(lref.toString());
	                    		lref.setLength(0);
	                    	}
	                    	i++;
	                    	if(i>=len)
	                    		break;
		                    	ch=ltext.charAt(i);
	                    	//ch1=ltext.charAt(i);
						}
						if(lref.length()!=0)
						{
							StopRem.in_ref(lref.toString());
							lref.setLength(0);
						}
						//System.out.println(lref.toString());		
					}
					if((i+20<len&&(ltext.substring(i, i+20).equalsIgnoreCase("== External links ==")))||((i+20<len&&(ltext.substring(i, i+18).equalsIgnoreCase("===External links==="))))||(i+18<len&&(ltext.substring(i, i+18).equalsIgnoreCase("==External links==")))||(i+22<len&&(ltext.substring(i, i+22).equalsIgnoreCase("==External links=="))))
					{
						//System.out.println("-----------------Links--------------");
						if(i+18<len&&(ltext.substring(i, i+18).equalsIgnoreCase("==External links==")))
							i=i+18;
						else if(i+20<len&&(ltext.substring(i, i+20).equalsIgnoreCase("== External links ==")) || (i+20<len&&(ltext.substring(i, i+18).equalsIgnoreCase("===External links==="))) )
							i=i+20;
						else if(i+22<len&&(ltext.substring(i, i+22).equalsIgnoreCase("=== External links ===")))
							i=i+22;
						if(i>=len)
	                    		break;
							ch=ltext.charAt(i);
					
						while(ch!='='  && i+2< len)
						{
							
							if(i+11<len&&ltext.substring(i, i+11).equalsIgnoreCase("[[Category:"))
							{
							//			System.out.println("Category in Ext Links");
							//			System.out.println("------------Category-------------------------");
										i=i+11;
										if(i>=len)
					                    		break;
										ch=ltext.charAt(i);
										//System.out.println("CAtegory m ky h "+ch);
										while(ch!=']' && i<len )
										{
											//if( (ch>=65&&ch<=90)||(ch>=97&&ch<=122) || (ch>=48&&ch<=57) )
											if( (ch>=65&&ch<=90)||(ch>=97&&ch<=122))
					                  		{
												//System.out.println(ch);
					                    		
					                    		lcategories.append(ch);
					                    	}
					                    	else
					                    	{
					                    		//System.out.println("Word sent "+lcategories);
					                    		StopRem.in_categories(lcategories.toString());
					                    		lcategories.setLength(0);
					                    	}
					                    	i++;
					                    	if(i>=len)
					                    		break;
					                    		ch=ltext.charAt(i);
										}
										if(lcategories.length()!=0)
										{
											StopRem.in_categories(lcategories.toString());
											lref.setLength(0);
										}
										
								}

							if(i+6<len&&(ltext.substring(i, i+6).equalsIgnoreCase("[http:"))) //if [http then skip letters till space occures	
							{
								i=i+6;
								if(i>=len)
	                    			break;
								ch=ltext.charAt(i);
							//	System.out.println("http: k baAd"+ch);
								while((ch!=' ' || ch!=']') && i< len )
								{
									i++;
									if(i<len)
										ch=ltext.charAt(i);
									//ch1=ltext.charAt(i);
								}			
							}
							//if((ch>=65&&ch<=90)||(ch>=97&&ch<=122) || (ch>=48&&ch<=57))
							if((ch>=65&&ch<=90)||(ch>=97&&ch<=122))
	                  		{
								//System.out.println(ch);
	                    		lext_links.append(ch);
	                    	}
	                    	else
	                    	{
	                    		StopRem.in_extLinks(lext_links.toString());
	                    		lext_links.setLength(0);
	                    	}
	                    	i++;
	                    	if(i>=len)
	                    		break;
		                    	ch=ltext.charAt(i);
	                    	//ch1=ltext.charAt(i);
						}
						if(lext_links.length()!=0)
						{
							StopRem.in_ref(lext_links.toString());
							lext_links.setLength(0);
						}
						//System.out.println(lext_links.toString());		
					}
				}
				else if(ltext.charAt(i)=='[')
				{
					// System.out.println("char is [");
					// System.out.println(ltext.charAt(i)+""+ltext.charAt(i+1)+""+ltext.charAt(i+2)+""+ltext.charAt(i+3));
					if(i+12<len&&ltext.substring(i, i+12).equalsIgnoreCase("[[Category:"))
					{
						System.out.println("------------Category-------------------------");
						i=i+11;
						if(i>=len)
	                    		break;
						ch=ltext.charAt(i);
						//System.out.println("CAtegory m ky h "+ch);
						while(ch!=']' && i<len )
						{
							//if( (ch>=65&&ch<=90)||(ch>=97&&ch<=122) || (ch>=48&&ch<=57) )
							if( (ch>=65&&ch<=90)||(ch>=97&&ch<=122))
	                  		{
								//System.out.println(ch);
	                    		
	                    		lcategories.append(ch);
	                    	}
	                    	else
	                    	{
	                    		//System.out.println("Word sent "+lcategories);
	                    		StopRem.in_categories(lcategories.toString());
	                    		lcategories.setLength(0);
	                    	}
	                    	i++;
	                    	if(i>=len)
	                    		break;
	                    		ch=ltext.charAt(i);
						}
						if(lcategories.length()!=0)
						{
							StopRem.in_categories(lcategories.toString());
							lref.setLength(0);
						}
						//System.out.println(lcategories.toString());
					}
					if(i+8<len&&ltext.substring(i, i+8).equalsIgnoreCase("[[image:"))
					{
						//System.out.println("------------Image-------------------------");
						i=i+8;
						boolean img_flag=true;
						int count=0;
						if(i>=len)
	                    		break;
						
						//System.out.println("CAtegory m ky h "+ch);
						while(img_flag && i<len )
						{
							if(ltext.charAt(i)=='[')
								count++;
							else if(ltext.charAt(i)==']')
								count--;
							if(count==-2)
							{
								i++;
								img_flag=false;
								break;
							}
	                    	i++;
	                    	if(i>=len)
	                    		break;
						}
					}	
						if(i+7<len&&ltext.substring(i, i+7).equalsIgnoreCase("[[file:"))
						{
							//System.out.println("------------file-------------------------");
							i=i+7;
							boolean img_flag=true;
							int count=0;
							if(i>=len)
		                    		break;
							
							//System.out.println("CAtegory m ky h "+ch);
							while(img_flag && i<len )
							{
								if(ltext.charAt(i)=='[')
									count++;
								else if(ltext.charAt(i)==']')
									count--;
								if(count==-2)
								{
									i++;
									img_flag=false;
									break;
								}
		                    	i++;
		                    	if(i>=len)
		                    		break;
							}
						}
						if(i+6<len&&(ltext.substring(i, i+6).equalsIgnoreCase("[http:"))) //if [http then skip letters till space occures	
							{
								//ext ref m aate http wale
								//System.out.println("--------------------http-----------");
								i=i+6;
								if(i>=len)
	                    			break;
								ch=ltext.charAt(i);
							//	System.out.println("http: k baAd"+ch);
								while((ch!=' ' || ch!=']') && i< len )
								{
									i++;
									if(i<len)
										ch=ltext.charAt(i);
									//ch1=ltext.charAt(i);
								}			
							}
					}
					
					else 
					{
						if(i<len && ltext.charAt(i)=='<')
						{
							if (i+4<len&&ltext.substring(i+1,i+4).equals("!--")) 
					           {
					             //Check and Eliminate comments
					             i=i+4;
					             int locate_close = ltext.indexOf("-->",i);
					             if(locate_close+2<len&&locate_close>0)
					               i=locate_close+2;
					           }
							if(i+8<len && ltext.substring(i+1,i+8).equalsIgnoreCase("gallery"))
				           {
				             //System.out.print("\nGALLERY START at i ="+i+"\n");
				             //Check and eliminate gallery
				             i=i+8;
				             int locate_close = ltext.indexOf("</gallery>" , i+1);
				             if(locate_close+10<len&&locate_close>0)
				               	i=locate_close+10;
				             //System.out.print("\nGALLERY END at i ="+i+"\n");
				           }
						}
						if(i<len && ltext.charAt(i)=='#')
						{
							ch=ltext.charAt(i);
							while(i<len && ch!=' ')
							{
								i++;
								if(i>=len)
									break;
								ch=ltext.charAt(i);
							}
						}
						 else
						 {
						 	//System.out.println("-----------Body----------");
						 	//if(i<len && (ltext.charAt(i)>=65&&ltext.charAt(i)<=90)||(ltext.charAt(i)>=97&&ltext.charAt(i)<=122))
            				if(i<len && (ltext.charAt(i)>=65&&ltext.charAt(i)<=90)||(ltext.charAt(i)>=97&&ltext.charAt(i)<=122))
            					lbody.append(ltext.charAt(i));
            
				             else
				             {
				             	//System.out.println("------------body-------------------------");
				               	StopRem.in_body(lbody.toString());
				             	lbody.setLength(0);
				             }
				             // if(lbody.length()!=0)
				             // {
				             // StopRem.in_body(lbody.toString());
				             // 	lbody.setLength(0);	
				             // }
						 }
					}
			}	
		}
		catch(Exception e)
		{	System.out.println("Error in parsing");
			e.printStackTrace();}

	}

}








// import java.util.*;
// import org.xml.sax.Attributes;
// import org.xml.sax.SAXException;
// import org.xml.sax.helpers.DefaultHandler;
 
// public class ReadXMLFile extends DefaultHandler 
// {
// 	public static Map<String, HashMap<String, Obj>> index = new TreeMap<String,HashMap<String,Obj>>();   

// 	boolean spage = false;
// 	boolean stitle = false;
//    boolean stext = false;
//    boolean sid = false;
//    boolean srevision = false;

//    boolean sinfo_box = false;
//    boolean scategories = false;
//    boolean sext_links = false;
//    boolean sref = false;
// 	static int index_count=0;
//    public static String title,doc_id;
// 	StringBuilder ltitle=new StringBuilder();	//l-local strig for titile
// 	StringBuilder lbody=new StringBuilder();
// 	StringBuilder ltext=new StringBuilder();
// 	StringBuilder linfo_box=new StringBuilder();
// 	StringBuilder lcategories=new StringBuilder();
// 	StringBuilder lext_links=new StringBuilder();;
// 	StringBuilder lref=new StringBuilder();
	
// 	public void startElement(String uri, String localName,String qName, 
//                 Attributes attributes) throws SAXException {

// 		//System.out.println("Start Element :" + qName);
// 		try
// 		{
// 			if (qName.equalsIgnoreCase("page")) {
// 				main_file.page_count++;
// 				spage=true;
// 			}

// 			if (qName.equalsIgnoreCase("title")) {
// 				stitle=true;	
// 			}
// 			if (qName.equalsIgnoreCase("revision")) {
// 				srevision=true;
// 			}

// 			if (qName.equalsIgnoreCase("id")) {
// 				sid=true;
// 			}

// 			if (qName.equalsIgnoreCase("text")) {
// 				stext=true;

// 			}
// 		}
// 		catch(Exception e){
// 			System.out.println("Exception in start ele");
// 			e.printStackTrace();
// 		}
// 	}





// 	public void characters(char ch[], int start, int length) throws SAXException 
// 	{
// 		try
// 		{
// 			if (stitle) {
// 				title=new String(ch, start, length);
				
// 				//title=title.toLowerCase();
				

// 	            //title=ltitle.toString();
// 	            //title=title.trim();
// 				//System.out.println("title= "+title);
				
// 				//System.out.println("First Name : " + ; String(ch, start, length));
// 				//bfname = false;
// 			}

// 			if (sid && !srevision) {
// 				doc_id=new String(ch, start, length);
				
// 				//object.body.toLowerCase();
// 				doc_id=doc_id.toLowerCase();
// 				//System.out.println(length);
// 				// title comes earilier than id so calling from here
// 				for(int i=0;i<title.length();i++)
//           		{	
// 		            char current = title.charAt(i);
// 		            if((current>=65&&current<=90)||(current>=97&&current<=122)||(current>=48&&current<=57))
// 		            {
// 		              ltitle.append(current);
// 		            }
// 		            else 				//ya tou har space pe cal kro ya sp character pe refer to function
// 		            {
// 		             	StopRem.in_title(ltitle.toString());
// 		             	ltitle.setLength(0);
// 		              //.handle_title(ltitle.toString());
// 		              //loc_title.setLength(0);
// 		            }
// 	            }
// 	            if(ltitle.length()!=0)
// 	            {
// 	            	StopRem.in_title(ltitle.toString());
// 	            	ltitle.setLength(0);
// 	            }
// 				//System.out.println("ID = "+doc_id);
// 				//stop words removal
// 				//special characters
// 				//System.out.println("Last Name : " + new String(ch, start, length));
// 				//blname = false;
// 			}

// 			if (stext) {
// 				//object.info_box=new String(ch, start, length);

// 				ltext.append(ch, start, length);
// 				//System.out.println(length);
// 				//object.info_box.toLowerCase();
// 				//object.info_box=rem(object.info_box);
// 				//String test=ltext.toString();
// 				//test=test.toLowerCase();
// 				//System.out.println("text= "+test);
// 				//stop words removal
// 				//special characters
// 				//System.out.println("length= "+ltest.length());
// 				//bnname = false;
// 			}
// 		}catch(Exception e)
// 		{
// 			System.out.println("Exception in readoing Elements");
// 			e.printStackTrace();
// 		}	
// 	}




// 	public void endElement(String uri, String localName,
// 		String qName) throws SAXException 
// 	{
// 		//System.out.println("End Element :" + qName);
// 		try
// 		{
// 			if (qName.equalsIgnoreCase("page")) {
// 				spage=false;
// 				if(main_file.page_count%2500==0)
// 				{
// 					Save_Index.save_index("index"+index_count);
// 					index_count++;
// 				}
// 			}
// 			if (qName.equalsIgnoreCase("revision")) {
// 				srevision=false;
// 			}

// 			if (qName.equalsIgnoreCase("title")) {
// 				stitle=false;	
// 			}

// 			if (qName.equalsIgnoreCase("id")) {
// 				sid=false;
// 			}

// 			if (qName.equalsIgnoreCase("text")) {
// 				stext=false;
// 				//String test=ltext.toString();
// 				//System.out.println(test);
// 				parse_text();
				
// 				//System.out.println("------------References-------------------------");
// 				//System.out.println(lref);
// 				//System.out.println("------------Category-------------------------");
// 				//System.out.println(lcategories);
// 				//System.out.println("End ele \n Length= "+ltext.length());
// 				ltext.setLength(0);
// 				lcategories.setLength(0);
// 				ltitle.setLength(0);	//l-local strig for titile
// 				lbody.setLength(0);
// 				linfo_box.setLength(0);
// 				lext_links.setLength(0);
// 				lref.setLength(0);
// 			}
// 		}
// 		catch(Exception e)
// 		{
// 			System.out.println("Exception in End ele");
// 			e.printStackTrace();
// 		}	
// 		//System.out.println("End Element :" + qName);

// 	}
// 	public void parse_text()
// 	{
// 		try
// 		{
// 			char ch,ch1;
// 			int len = ltext.length();
// 			int i;
// 			//System.out.println(ltext.toString());
// 			for(i=0;i<len;i++)
// 			{
// 				if(ltext.charAt(i)=='{')
// 				{
// 					//System.out.println("char is {");
// 					if(i+9<len && ltext.substring(i,i+9).equalsIgnoreCase("{{infobox"))
// 					{
// 						i=i+9;
// 						//System.out.println("-----------INFO box-----------");
// 						int count_par=2;
// 						boolean infoflag=true,addtext=false;
// 						while(i<len && infoflag)
// 						{
// 							ch=ltext.charAt(i);
							
// 							if(ch=='{')
// 								count_par++;
// 							else if(ch=='}')
// 								count_par--;
// 							if(count_par==0 || i>=len )
// 							{
// 								infoflag=false;
// 								i++;
// 								break;
// 							}
// 							if(i+1 < len && ltext.charAt(i)=='[' && ltext.charAt(i+1)=='[' )
// 							{
// 								addtext=true;
// 								// i=i+2;
// 								//ch=ltext.charAt(i);
// 								// while(ch!=']' && ltext.charAt(i+1)!=']')
// 								// {
// 								// 	// System.out.print(ch);
									
// 			     //                	i++;
// 			     //                	ch=ltext.charAt(i);
// 								// }
// 							}
// 							else if(i+1 <len && ltext.charAt(i)==']' && ltext.charAt(i+1)==']')
// 							{
// 								addtext=false;
// 								StopRem.in_info_box(linfo_box.toString());
// 			                    linfo_box.setLength(0);
// 							}
// 							if(addtext)
// 							{
// 									if( (ch>=65&&ch<=90)||(ch>=97&&ch<=122))
// 	                  				{
// 					             		linfo_box.append(ch);
// 	                		    	}
// 			                    	else
// 			                    	{
// 			                    		//System.out.println("Word sent "+linfo_box);
// 			                    		StopRem.in_info_box(linfo_box.toString());
// 			                    		linfo_box.setLength(0);
// 			                    	}
// 							}
// 							i++;
// 						}
// 						// if(linfo_box.length()!=0)
// 						// {
// 						// 	StopRem.in_info_box(linfo_box.toString());
// 			   //              linfo_box.setLength(0);
// 						// }
// 					}
// 					else if(i+6<len&&ltext.substring(i, i+6).equalsIgnoreCase("{{cite"))
// 					{
// 						//System.out.println("------------cite-------------------------");
// 						i=i+6;
// 						boolean img_flag=true;
// 						int count=0;
// 						if(i>=len)
// 	                    		break;
						
// 						//System.out.println("CAtegory m ky h "+ch);
// 						while(img_flag && i<len )
// 						{
// 							if(ltext.charAt(i)=='[')
// 								count++;
// 							else if(ltext.charAt(i)==']')
// 								count--;
// 							if(count==-2)
// 							{
// 								i++;
// 								img_flag=false;
// 								break;
// 							}
// 	                    	i++;
// 	                    	if(i>=len)
// 	                    		break;
// 						}
// 					}	
// 					else if(i+6<len&&ltext.substring(i, i+6).equalsIgnoreCase("{{coord"))
// 					{
// 						//System.out.println("------------coord-------------------------");
// 						i=i+6;
// 						boolean img_flag=true;
// 						int count=0;
// 						if(i>=len)
// 	                    		break;
						
// 						//System.out.println("CAtegory m ky h "+ch);
// 						while(img_flag && i<len )
// 						{
// 							if(ltext.charAt(i)=='[')
// 								count++;
// 							else if(ltext.charAt(i)==']')
// 								count--;
// 							if(count==-2)
// 							{
// 								i++;
// 								img_flag=false;
// 								break;
// 							}
// 	                    	i++;
// 	                    	if(i>=len)
// 	                    		break;
// 						}
// 					}	
// 				}	
// 				else if(ltext.charAt(i)=='=')
// 				{
// 					//System.out.println("char is =");
// 					if( (i+16<len&&(ltext.substring(i, i+16).equalsIgnoreCase("== References =="))) || (i+16<len&&(ltext.substring(i, i+16).equalsIgnoreCase("===References===")))||(i+14<len&&(ltext.substring(i, i+14).equalsIgnoreCase("==References==")))||(i+18<len&&(ltext.substring(i, i+18).equalsIgnoreCase("=== References ==="))))
// 					{
// 						//System.out.println("-----------------References--------------");
// 							if( (ltext.substring(i, i+16).equalsIgnoreCase("== References ==")) || (ltext.substring(i, i+16).equalsIgnoreCase("===References===")) )
// 							i=i+16;
// 						else if(ltext.substring(i, i+14).equalsIgnoreCase("==References=="))	
// 							i+=14;
// 						else if(1+18<len && ltext.substring(i, i+18).equalsIgnoreCase("=== References ==="))	
// 							i+=18;
// 						if(i>=len)
// 	                    		break;
// 							ch=ltext.charAt(i);
// 						while(ch!='='  && i+2< len)
// 						{
									
// 								if(i+11<len&&ltext.substring(i, i+11).equalsIgnoreCase("[[Category:"))
// 								{
// 										//System.out.println("Category in References");
// 										//System.out.println("------------Category-------------------------");
// 										break;
// 								}
// 							else if(i+6<len&&(ltext.substring(i, i+6).equalsIgnoreCase("[http:"))) //if [http then skip letters till space occures	
// 							{
// 								i=i+6;
// 								if(i>=len)
// 	                    			break;
// 								ch=ltext.charAt(i);
// 							//	System.out.println("http: k baAd"+ch);
// 								while((ch!=' ' || ch!=']') && i< len )
// 								{
// 									i++;
// 									if(i<len)
// 										ch=ltext.charAt(i);
// 									//ch1=ltext.charAt(i);
// 								}			
// 							}
// 							//if((ch>=65&&ch<=90)||(ch>=97&&ch<=122) || (ch>=48&&ch<=57))
// 							if((ch>=65&&ch<=90)||(ch>=97&&ch<=122))
// 	                  		{
// 								//System.out.println(ch);
// 	                    		lref.append(ch);
// 	                    	}
// 	                    	else
// 	                    	{
// 	                    		StopRem.in_ref(lref.toString());
// 	                    		lref.setLength(0);
// 	                    	}
// 	                    	i++;
// 	                    	if(i>=len)
// 	                    		break;
// 		                    	ch=ltext.charAt(i);
// 	                    	//ch1=ltext.charAt(i);
// 						}
// 						if(lref.length()!=0)
// 						{
// 							StopRem.in_ref(lref.toString());
// 							lref.setLength(0);
// 						}
// 						//System.out.println(lref.toString());		
// 					}
// 					if((i+20<len&&(ltext.substring(i, i+20).equalsIgnoreCase("== External links ==")))||((i+20<len&&(ltext.substring(i, i+18).equalsIgnoreCase("===External links==="))))||(i+18<len&&(ltext.substring(i, i+18).equalsIgnoreCase("==External links==")))||(i+22<len&&(ltext.substring(i, i+22).equalsIgnoreCase("==External links=="))))
// 					{
// 						//System.out.println("-----------------Links--------------");
// 						if(i+18<len&&(ltext.substring(i, i+18).equalsIgnoreCase("==External links==")))
// 							i=i+18;
// 						else if(i+20<len&&(ltext.substring(i, i+20).equalsIgnoreCase("== External links ==")) || (i+20<len&&(ltext.substring(i, i+18).equalsIgnoreCase("===External links==="))) )
// 							i=i+20;
// 						else if(i+22<len&&(ltext.substring(i, i+22).equalsIgnoreCase("=== External links ===")))
// 							i=i+22;
// 						if(i>=len)
// 	                    		break;
// 							ch=ltext.charAt(i);
					
// 						while(ch!='='  && i+2< len)
// 						{
							
// 							if(i+11<len&&ltext.substring(i, i+11).equalsIgnoreCase("[[Category:"))
// 							{
// 							//			System.out.println("Category in Ext Links");
// 							//			System.out.println("------------Category-------------------------");
// 										break;
										
// 							}

// 							else if(i+6<len&&(ltext.substring(i, i+6).equalsIgnoreCase("[http:"))) //if [http then skip letters till space occures	
// 							{
// 								i=i+6;
// 								if(i>=len)
// 	                    			break;
// 								ch=ltext.charAt(i);
// 							//	System.out.println("http: k baAd"+ch);
// 								while((ch!=' ' || ch!=']') && i< len )
// 								{
// 									i++;
// 									if(i<len)
// 										ch=ltext.charAt(i);
// 									//ch1=ltext.charAt(i);
// 								}			
// 							}
// 							//if((ch>=65&&ch<=90)||(ch>=97&&ch<=122) || (ch>=48&&ch<=57))
// 							if((ch>=65&&ch<=90)||(ch>=97&&ch<=122))
// 	                  		{
// 								//System.out.println(ch);
// 	                    		lext_links.append(ch);
// 	                    	}
// 	                    	else
// 	                    	{
// 	                    		StopRem.in_extLinks(lext_links.toString());
// 	                    		lext_links.setLength(0);
// 	                    	}
// 	                    	i++;
// 	                    	if(i>=len)
// 	                    		break;
// 		                    	ch=ltext.charAt(i);
// 	                    	//ch1=ltext.charAt(i);
// 						}
// 						if(lext_links.length()!=0)
// 						{
// 							StopRem.in_ref(lext_links.toString());
// 							lext_links.setLength(0);
// 						}
// 						//System.out.println(lext_links.toString());		
// 					}
// 				}
// 				else if(ltext.charAt(i)=='[')
// 				{
// 					// System.out.println("char is [");
// 					// System.out.println(ltext.charAt(i)+""+ltext.charAt(i+1)+""+ltext.charAt(i+2)+""+ltext.charAt(i+3));
// 					if(i+12<len&&ltext.substring(i, i+12).equalsIgnoreCase("[[Category:"))
// 					{
// 						System.out.println("------------Category-------------------------");
// 						i=i+11;
// 						if(i>=len)
// 	                    		break;
// 						ch=ltext.charAt(i);
// 						//System.out.println("CAtegory m ky h "+ch);
// 						while(ch!=']' && i<len )
// 						{
// 							//if( (ch>=65&&ch<=90)||(ch>=97&&ch<=122) || (ch>=48&&ch<=57) )
// 							if( (ch>=65&&ch<=90)||(ch>=97&&ch<=122))
// 	                  		{
// 								//System.out.println(ch);
	                    		
// 	                    		lcategories.append(ch);
// 	                    	}
// 	                    	else
// 	                    	{
// 	                    		//System.out.println("Word sent "+lcategories);
// 	                    		StopRem.in_categories(lcategories.toString());
// 	                    		lcategories.setLength(0);
// 	                    	}
// 	                    	i++;
// 	                    	if(i>=len)
// 	                    		break;
// 	                    		ch=ltext.charAt(i);
// 						}
// 						if(lcategories.length()!=0)
// 						{
// 							StopRem.in_categories(lcategories.toString());
// 							lref.setLength(0);
// 						}
// 						//System.out.println(lcategories.toString());
// 					}
// 					if(i+8<len&&ltext.substring(i, i+8).equalsIgnoreCase("[[image:"))
// 					{
// 						//System.out.println("------------Image-------------------------");
// 						i=i+8;
// 						boolean img_flag=true;
// 						int count=0;
// 						if(i>=len)
// 	                    		break;
						
// 						//System.out.println("CAtegory m ky h "+ch);
// 						while(img_flag && i<len )
// 						{
// 							if(ltext.charAt(i)=='[')
// 								count++;
// 							else if(ltext.charAt(i)==']')
// 								count--;
// 							if(count==-2)
// 							{
// 								i++;
// 								img_flag=false;
// 								break;
// 							}
// 	                    	i++;
// 	                    	if(i>=len)
// 	                    		break;
// 						}
// 					}	
// 						if(i+7<len&&ltext.substring(i, i+7).equalsIgnoreCase("[[file:"))
// 						{
// 							//System.out.println("------------file-------------------------");
// 							i=i+7;
// 							boolean img_flag=true;
// 							int count=0;
// 							if(i>=len)
// 		                    		break;
// 							while(img_flag && i<len )
// 							{
// 								if(ltext.charAt(i)=='[')
// 									count++;
// 								else if(ltext.charAt(i)==']')
// 									count--;
// 								if(count==-2)
// 								{
// 									i++;
// 									img_flag=false;
// 									break;
// 								}
// 		                    	i++;
// 		                    	if(i>=len)
// 		                    		break;
// 							}
// 						}
// 						if(i+6<len&&(ltext.substring(i, i+6).equalsIgnoreCase("[http:"))) //if [http then skip letters till space occures	
// 							{
// 								//ext ref m aate http wale
// 								//System.out.println("--------------------http-----------");
// 								i=i+6;
// 								if(i>=len)
// 	                    			break;
// 								ch=ltext.charAt(i);
// 							//	System.out.println("http: k baAd"+ch);
// 								while((ch!=' ' || ch!=']') && i< len )
// 								{
// 									i++;
// 									if(i<len)
// 										ch=ltext.charAt(i);
// 									//ch1=ltext.charAt(i);
// 								}			
// 							}
// 					}
					
// 					else 
// 					{
// 						if(i<len && ltext.charAt(i)=='<')
// 						{
// 							if (i+4<len&&ltext.substring(i+1,i+4).equals("!--")) 
// 					           {
// 					             //Check and Eliminate comments
// 					             i=i+4;
// 					             int locate_close = ltext.indexOf("-->",i);
// 					             if(locate_close+2<len&&locate_close>0)
// 					               i=locate_close+2;
// 					           }
// 							if(i+8<len && ltext.substring(i+1,i+8).equalsIgnoreCase("gallery"))
// 				           {
// 				             //System.out.print("\nGALLERY START at i ="+i+"\n");
// 				             //Check and eliminate gallery
// 				             i=i+8;
// 				             int locate_close = ltext.indexOf("</gallery>" , i+1);
// 				             if(locate_close+10<len&&locate_close>0)
// 				               	i=locate_close+10;
// 				             //System.out.print("\nGALLERY END at i ="+i+"\n");
// 				           }
// 						}
// 						if(i<len && ltext.charAt(i)=='#')
// 						{
// 							ch=ltext.charAt(i);
// 							while(i<len && ch!=' ')
// 							{
// 								i++;
// 								if(i>=len)
// 									break;
// 								ch=ltext.charAt(i);
// 							}
// 						}
// 						 else
// 						 {
//             				if(i<len && (ltext.charAt(i)>=65&&ltext.charAt(i)<=90)||(ltext.charAt(i)>=97&&ltext.charAt(i)<=122))
//             					lbody.append(ltext.charAt(i));
            
// 				             else
// 				             {
// 				             	//System.out.println("------------body-------------------------");
// 				               	StopRem.in_body(lbody.toString());
// 				             	lbody.setLength(0);
// 				             }
// 				             if(lbody.length()!=0)
// 				             {
// 				             StopRem.in_body(lbody.toString());
// 				             	lbody.setLength(0);	
// 				             }
// 						 }
// 					}
// 			}	
// 		}
// 		catch(Exception e)
// 		{	System.out.println("Error in parsing");
// 			e.printStackTrace();}

// 	}
// }
