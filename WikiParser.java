import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


class WikiParser extends DefaultHandler {

	   //PageInfo pinfo=new PageInfo();
	
	
	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub
		
		//super.fatalError(e);
		
	}

	public static Map <String,HashMap<String,PageInfo> > index_table=new TreeMap<String,HashMap <String,PageInfo> >();
	public static int doc_cnt=0;
	public static int file_cnt=3260;
	int split_cnt=5000;
	StringBuilder id=new StringBuilder();
	StringBuilder title=new StringBuilder();
	StringBuilder body=new StringBuilder();
	StringBuilder infobox=new StringBuilder();
	StringBuilder cat=new StringBuilder();
	StringBuilder ref=new StringBuilder();
	StringBuilder ext=new StringBuilder();
	StringBuilder wrd=new StringBuilder();
	StringBuilder s=new StringBuilder();
	
	   boolean f_page = false;
	   boolean f_title = false;
	   boolean f_revision = false;
	   boolean f_text = false;
	   boolean f_id = false;
	   int count=0,c=0;
	   int body_len=0;
	   StringBuilder text1=new StringBuilder();  



	   @Override
	   public void startElement(String uri, 
	      String localName, String qName, Attributes attributes)
	          throws SAXException 
	    {
	    	  if (qName.equalsIgnoreCase("page")) 
	    	  {
	        	 f_page=true;
	        	 doc_cnt++;
	      	  } 
	    	  else if (qName.equalsIgnoreCase("id")) 
	      	  {
		         f_id = true;
		      }
	    	  else if (qName.equalsIgnoreCase("text")) 
	      	  {
	            f_text = true;
	      	  } 
	      	  else if ((!f_revision) && qName.equalsIgnoreCase("title") ) 
	      	  {
	         	f_title = true;
	      	  }
	      	  else if (qName.equalsIgnoreCase("revision")) 
	      	  {
	            f_revision = true;
	      	  } 
	      	  
	      	 
	      
	   }

	   @Override
	   public void endElement(String uri, 
	      String localName, String qName) throws SAXException {
	     
	      if (qName.equalsIgnoreCase("page")) 
	      {
	    	 if(doc_cnt%split_cnt==0)
	    	 {
	    		 write_file.write_to_file(doc_cnt/split_cnt);
	    		 file_cnt++;
	    	 }
	         f_page=false;
	         f_revision=false;
	         title.setLength(0);
	         body.setLength(0);
	         ext.setLength(0);
	         infobox.setLength(0);
	         ref.setLength(0);
	         wrd.setLength(0);
	         s.setLength(0);
	         id.setLength(0);
	      }
	      else if (qName.equalsIgnoreCase("id")) {
	    	  
		         f_id = false;
		      }
	      else if (qName.equalsIgnoreCase("text")) {
	    	   	parseBody();
		         f_text = false;
		      }
	      else if (qName.equalsIgnoreCase("title")) {
		         f_title = false;
		      }
	      else if(qName.equalsIgnoreCase("mediawiki"))
	      { 
	    	  if(doc_cnt%split_cnt>0)
	    	  {
	    	  write_file.write_to_file((doc_cnt/split_cnt) + 1);
	    	  file_cnt++;
	    	  }
	    	  Merger mm=new Merger();
	    	  mm.merge();
	      }
	      
	   }

	   @Override
	   public void characters(char ch[], 
	      int start, int length){
		   try
		   {
	      if (f_page && f_title && (!f_revision)) {
	         title.append(ch, start, length);
	         //f_title = false;
	      } else if (f_page && f_id && (!f_revision)) {
	         id.append(ch, start, length);
	         parseTitle();
	         title.setLength(0);
	         s.setLength(0);
	         f_id = false;
	      } else if (f_page && f_revision && f_text) {
	    	 
	    	  body.append(ch,start,length);
	       
	      }
		   }
		   catch(Exception e)
		   {
			   System.out.println("Chinese Character Exception Caught");
		   }
	   }
	   
	   void parseTitle()
	   {
		 int title_len=title.toString().length();
		//System.out.println("============="+id.toString());
		 for(int i=0;i<title_len;i++)
		   {
			 if((title.charAt(i)>='0' && title.charAt(i)<='9') 
					 || (title.charAt(i)>='A' && title.charAt(i)<='Z')
					 || (title.charAt(i)>='a' && title.charAt(i)<='z')) //|| (title.charAt(i)=='-')||(title.charAt(i)=='/')
			 {
				 s.append(title.charAt(i));
				// System.out.print(title.charAt(i));
			 }
			 else
			 {
				 if(s.length()>0)
				 {
					// if(s.toString().contains("-")||s.toString().contains("/"))
					// System.out.println(s.toString().toLowerCase().trim());
					 WordCleaner.cleanword(s.toString().toLowerCase().trim(),'T',id.toString().trim() );
					 s.setLength(0);
				 }
				// System.out.println();
			 }
		   }
		 if(s.length()>0)
		 WordCleaner.cleanword(s.toString().toLowerCase().trim(),'T',id.toString().trim() );
		 s.setLength(0);
		   
		   
		   
	   }
	   
	   
	   void parseBody()
	   {
		   body_len=body.length();
		   //System.out.println(body);
		   try
		   {
			   for(int i=0;i<body_len;)
			   {
				   if(body.charAt(i)=='<')
				   {
					   /*
					    *For removing comments & ref tag content
					    *
					    */
					   
					   if(((i+4)<=body_len) && (body.substring(i+1, i+4).equals("!--")))
					   {
						   i=parse_comment(i);   //returs index of element after comment,ignores all chars in between
					   }
					  else if((i+4)<=body_len && (body.substring(i+1, i+4).equals("ref")))
					   {
						   i=parse_ref(i);      //returs index of element after ref tag,ignores all chars in between
					   }
					   else
					   {
						   i++;
					   }
				   }  //  if(body.charAt(i)=='<') ends here
				   else if(body.charAt(i)=='{')
				   {
					   if((i+1)<body_len && body.charAt(i+1)=='{')
					   {
					   if( ( (i+9)<=body_len ) && (body.substring(i+2, i+9).equalsIgnoreCase(("Infobox"))) )
					   {
						  
						   try
						   {
							   i=parse_infobox(i);
						   }
						   catch(Exception e)
						   {
							   System.out.println("Exception in Body while parsing infobox");
						   }
					   }
					   else if(( (i+6)<=body_len ) && (body.substring(i+2, i+6).equalsIgnoreCase(("cite"))))
					   {
						   /*
						     * For removing everything in {{cite }} 
						    */
						    
						   i+=6;
						   try
						   {
					    	i=ignore_text(i);
						   }
						   catch(Exception e)
						   {
							   System.out.println("Exception in Body while parsing coord");
						   }
						  // System.out.println("}");
					   }
					   if(( (i+7)<=body_len ) && (body.substring(i+2, i+7).equalsIgnoreCase(("coord"))))
					   {
						   /*
						     * For removing everything in {{coord }} 
						    */
						    i+=7;
						    
						    try
							   {
						    	i=ignore_text(i);
							   }
							   catch(Exception e)
							   {
								   System.out.println("Exception in Body while parsing coord");
							   }
							//System.out.println("}");
					   }
					   else
					   {
						   i++;
					   }
					   }
					   else
					   {
						   i++;
					   }
				   }
				   else if( body.charAt(i)=='[' )
				   {
					   if((i+1)<body_len && body.charAt(i+1)=='[')
					   {
					   if(( (i+10)<=body_len ) && (body.substring(i+2, i+10).equalsIgnoreCase(("category"))))
					   {
						   int par_count=2;
						   i+=10;
						   StringBuilder cat_text=new StringBuilder();
						   //System.out.print(id.toString()+"{");
						   while(i<body_len && par_count>0)
						   {
							if(body.charAt(i)=='[')
							{
								par_count++;
							}
							else if(body.charAt(i)==']')
							{
								par_count--;
							}
							else if( (body.charAt(i)>='A' && body.charAt(i)<='Z')|| (body.charAt(i)>='a' && body.charAt(i)<='z') )
							{
							  cat_text.append(body.charAt(i));	
							}
							else
							{
								if(cat_text.toString().length()>1)
								   {
								//	System.out.println(cat_text.toString());
								   WordCleaner.cleanword(cat_text.toString().toLowerCase().trim(),'C',id.toString().trim() );
								   cat_text.setLength(0);
								   }
							}
							i++;
						   }
						   
						   if(cat_text.toString().length()>1)
						   {
							//   System.out.println(cat_text.toString());
						   WordCleaner.cleanword(cat_text.toString().toLowerCase().trim(),'C',id.toString().trim() );
						   cat_text.setLength(0);
						   }
					   }
					   else if(( (i+7)<=body_len ) && (body.substring(i+2, i+7).equalsIgnoreCase(("file:"))))
					   {
						   i+=7;
						   try
						   {
						   i=ignore_text2(i);
						   }
						   catch(Exception e)
						   {
							   System.out.println("Exception in Body while parsing file");
						   }
						  // System.out.println("}");
					   }
					   else if(( (i+8)<=body_len ) && (body.substring(i+2, i+8).equalsIgnoreCase(("image:"))))
					   {
						   i+=8;
						   try
						   {
						   i=ignore_text2(i);
						   }
						   catch(Exception e)
						   {
							   System.out.println("Exception in Body while parsing image");
						   }
					   }
					   else if((i+1)<body_len && body.charAt(i+1)=='[')
					   {
						   i+=2;
						   i+=parse_sq_br(i);
					   }
					   else
					   {
						   i++;
					   }
					   }
					   else
					   {
						   i++;
					   }
				   }
				   else if( i+1<body_len && body.charAt(i)=='=' && body.charAt(i+1)=='='  )
				   {
				   if(( (i+14)<=body_len ) && (body.substring(i, i+14).equalsIgnoreCase(("==References=="))))
				   {
					   i+=14;
					  
					   try
					   {
					   i=parse_reference(i);
					   }
					   catch(Exception e)
					   {
						   System.out.println("Error in parsing ref");
					   }
				   }
				   else if(( (i+16)<=body_len ) && (body.substring(i, i+16).equalsIgnoreCase(("== References =="))))
				   {
					   i+=16;
					  
					   try
					   {
					   i=parse_reference(i);
					   }
					   catch(Exception e)
					   {
						   System.out.println("Error in parsing ref");
					   }
				   }
				   
				   else if(( (i+18)<=body_len ) && (body.substring(i, i+18).equalsIgnoreCase(("==External links=="))))
				   {
					   i+=18;
					   i++; //for 1 /n
					   try
					   {
					   i=parse_ext(i);
					   }
					   catch(Exception e)
					   {
						   System.out.println("Error in parsing external ref");
					   }
				   }
				   else if(( (i+20)<=body_len ) && (body.substring(i, i+20).equalsIgnoreCase(("== External links =="))))
				   {
					   i+=20;
					   i++; //for 1 /n
					   try
					   {
					   i=parse_ext(i);
					   }
					   catch(Exception e)
					   {
						   System.out.println("Error in parsing external ref");
					   }
				   }
				   
				   else if(i+2<body_len && body.charAt(i+2)=='=')
				   {
					   if(( (i+16)<=body_len ) && (body.substring(i, i+16).equalsIgnoreCase(("===References==="))))
					   {
						   i+=16;
						  
						   try
						   {
						   i=parse_reference(i);
						   }
						   catch(Exception e)
						   {
							   System.out.println("Error in parsing ref");
						   }
					   }
					   else if(( (i+18)<=body_len ) && (body.substring(i, i+18).equalsIgnoreCase(("=== References ==="))))
					   {
						   i+=18;
						  
						   try
						   {
						   i=parse_reference(i);
						   }
						   catch(Exception e)
						   {
							   System.out.println("Error in parsing ref");
						   }
					   }
					   else if(( (i+20)<=body_len ) && (body.substring(i, i+20).equalsIgnoreCase(("===External links==="))))
					   {
						   i+=20;
						   i++; //for 1 /n
						   try
						   {
						   i=parse_ext(i);
						   }
						   catch(Exception e)
						   {
							   System.out.println("Error in parsing external ref");
						   }
					   }
					   else if(( (i+22)<=body_len ) && (body.substring(i, i+22).equalsIgnoreCase(("=== External links ==="))))
					   {
						   i+=22;
						   i++; //for 1 /n
						   try
						   {
						   i=parse_ext(i);
						   }
						   catch(Exception e)
						   {
							   System.out.println("Error in parsing external ref");
						   }
					   }
					   else
					   {
						   i+=3;
					   }
				   }
				   else
				   {
					   i+=2;;
				   }
			   }
				   else
				   {
					   boolean flag=false;
					   try
					   {
					  
					   while((i<body_len) && ((body.charAt(i)>='A' && body.charAt(i)<='Z')||(body.charAt(i)>='a' && body.charAt(i)<='z')))
					   {
						//   System.out.print(body.charAt(i));
						   wrd.append(body.charAt(i));
						   flag=true;
						   i++;
					   }
					   }
					   catch(Exception e)
					   {
						   System.out.println("Exception in Body" + e.getMessage());
					   }
					   //System.out.println();
					   WordCleaner.cleanword(wrd.toString().toLowerCase().trim(),'B',id.toString().trim() );
					   wrd.setLength(0);
					   if(!flag)
					   i++;
				   }
				   
			   }
			   
		   }
		   catch(Exception e)
		   {
			   System.out.println("Error while Parsing Body: "+ id.toString());
		   }
	   }
	   
  int parse_comment(int i)
   { 
 	    i+=4;
		int comment_close=body.indexOf("-->",i);
		if(comment_close>0)
		   {
		      i=comment_close+3;
		   }

		   return i;
	 }	   

  /*
   * Parses square bracket and returns the index '|', take care of value of i
   * */
   int parse_ref(int i)
    {
 	    i+=4;
		int ref_close=body.indexOf("</ref>",i);
		if(ref_close>0)
		   i=ref_close+6;

		return i;
    }
	 

	 int parse_infobox(int i)
	 {
	 	StringBuilder info=new StringBuilder();
		int par_count=2; 
		boolean read_info=true;
		boolean f_write=false;
		i+=8;
		try
		{
		while( (par_count>0)&&(i<body_len) )
		   {
			   f_write=false;
			   if(body.charAt(i)=='{')
			   {
				   par_count++;
				   f_write=true;
			   }
			   else if(body.charAt(i)=='}')
			   {
				   par_count--;
				   f_write=true;
			   }
			   else if(i+1<body_len && body.charAt(i)=='[' && body.charAt(i+1)=='[')
			   {
				   read_info=true;
				   f_write=true;
				   i+=1;
				   i+=parse_sq_br(i);
				   
			   }
			   else if(i+1<body_len && body.charAt(i)==']' && body.charAt(i+1)==']')
				{
				   //read_info=false;
				   f_write=true;
				   i++;
				 }
				 else if( (i<body_len) &&(read_info &&((body.charAt(i)>='A' && body.charAt(i)<='Z')
										 || (body.charAt(i)>='a' && body.charAt(i)<='z'))))  //(body.charAt(i)>='0' && body.charAt(i)<='9') 
				  {
				     info.append(body.charAt(i));
				  }
				 else if(body.charAt(i)=='\n' && (i+1<body_len && body.charAt(i+1)=='|') )
				 {
					 f_write=true;					 
					 int j=body.indexOf("=", i);
					 int j1=body.indexOf("}", i);
					 
					 if(j!=-1 && j1!=-1 && j<j1)
					 {
						 i=j;
					 }
					 else
					 {
						 i++;
					 }
				 }
				 else if(body.charAt(i)=='<')
				 {
					 f_write=true;
					
					 if((i+4<=body_len) && (body.substring(i+1, i+4).equals("!--")))
					   {
						   i=parse_comment(i);   //returs index of element after comment,ignores all chars in between
						   i--;
					   }
					  else if(i+4<=body_len && (body.substring(i+1, i+4).equals("ref")))
					   {
						   i=parse_ref(i);      //returs index of element after ref tag,ignores all chars in between
						   i--;
					   }
					 /*else if(i+6<body_len && body.substring(i, i+6).equalsIgnoreCase("<br />"))
					 {
						 i+=5;
					 }
					 else if(i+5<body_len && body.substring(i, i+5).equalsIgnoreCase("<br/>"))
					 {
						 i+=4;
					 }*/

				 }
				 else if(read_info)
				   {
					 f_write=true; 
				   }
					i++;
					
					if(f_write)
					{
						 if(info.toString().length()>1)
						   {
					    	  // System.out.println("id : "+id.toString()+" : "+info.toString());
						     WordCleaner.cleanword(info.toString().toLowerCase().trim(),'I',id.toString().trim() );
						     info.setLength(0);
					   	   }
						   else
						   {
						    	info.setLength(0);
						   }
					}
				}
				 
				if(info.toString().length()>1)
				  {
				//	System.out.println("id : "+id.toString()+" : "+info.toString());
				    WordCleaner.cleanword(info.toString().toLowerCase().trim(),'I',id.toString().trim() );
				    info.setLength(0);
				  }
				else
				   {
				   	info.setLength(0);
				   }
		}
		catch(Exception e)
		{
			System.out.println("Error in Infobox parsing");
		}
				return i;
	 }  
	 
	 int parse_sq_br(int i)
	 {
		 int j=body.indexOf("]", i);
		   int tmp_j=0;
		   String tmp=body.substring(i, j);
		   j=0;
		   while(j!=-1 && j<tmp.length())
		   {
			   
			 //  System.out.println(tmp.substring(j));
			   j=tmp.indexOf("|", j);
			   if(j!=-1)
			   {
				   tmp_j=j;
				   j++;
			   }
				   
			   
		   }
		   
		   return tmp_j;
	 }
	 
	 int ignore_text(int i)
	 {
		 int par_count=2;
		   //System.out.println(id.toString()+"{");
		   while(par_count>0 && i<body_len)
		   {
			   if(body.charAt(i)=='{')
			   {
				   par_count++;
			   }
			   else if(body.charAt(i)=='}')
			   {
				   par_count--;
			   }
			   else
			   {
				//   System.out.print(body.charAt(i));
			   }
			   i++;
		   }
		   return i;
	 }
	  
	 
	 int ignore_text2(int i)
	 {
		 int par_count=2;
		   //System.out.println(id.toString()+"{");
		   while(par_count>0 && i<body_len)
		   {
			   if(body.charAt(i)=='[')
			   {
				   par_count++;
			   }
			   else if(body.charAt(i)==']')
			   {
				   par_count--;
			   }
			   else
			   {
				//   System.out.print(body.charAt(i));
			   }
			   i++;
		   }
		   return i;
	 }
	 
	 int parse_ext(int i)
	 {
		 boolean new_line=true;
		 int end_index1=body.indexOf("[[Category:",i);
		 
		 if(end_index1==-1)
			 end_index1=body_len;
		   while(true && i<end_index1)
		   {
			   if(i+1< body_len && new_line && body.charAt(i)=='\n' && body.charAt(i+1)!='*')
			   {
				  // System.out.println("Breaking :"+ id.toString());
				   i++;
				   break;
				   
			   }
			   else if(body.charAt(i)=='<')
			   {
				   /*
				    *For removing comments
				    *
				    */
				   
				   if((i+4<=body_len) && (body.substring(i+1, i+4).equals("!--")))
				   {
					   i=parse_comment(i);   //returns index of element after comment,ignores all chars in between
				   }
				  else if(i+4<=body_len && (body.substring(i+1, i+4).equals("ref")))
				   {
					   i=parse_ref(i);      //returns index of element after ref tag,ignores all chars in between
				   }
				   else
				   {
					   i++;
				   }
			   }
			   else if(i+1<body_len && body.charAt(i)=='[' && body.charAt(i+1)=='[')
			   {
				   if(ext.toString().length()>1)
				   {
				  // System.out.println(id.toString()+" : "+ext.toString());
				   WordCleaner.cleanword(ext.toString().toLowerCase().trim(),'E',id.toString().trim() );
				   ext.setLength(0);
				   } 
				   i+=2;
				   i+=parse_sq_br(i);
				   
			   }
			  else
			   {
				   //System.out.print(body.charAt(i));
				   if( (body.charAt(i)>='A' && body.charAt(i)<='Z')|| (body.charAt(i)>='a' && body.charAt(i)<='z') )
				   {
				
					   ext.append(body.charAt(i));
				   
				   }
				   else
				   {
					   if(ext.toString().length()>1)
					   {
					//   System.out.println(id.toString()+" : "+ext.toString());
					   WordCleaner.cleanword(ext.toString().toLowerCase().trim(),'E',id.toString().trim() );
					   ext.setLength(0);
					   } 
				   }
				  /* if(new_line && body.charAt(i)!='*')
				   {
					   new_line=false;
					   break;
					   
				   }*/
				   new_line=false;
				   if(body.charAt(i)=='\n')
				   {
					   new_line=true;
				   }
				   //i++;
			   }
			   i++;
		   }
		   
		   if(ext.toString().length()>1)
		   {
		 //  System.out.println(id.toString()+" : "+ext.toString());
		   WordCleaner.cleanword(ext.toString().toLowerCase().trim(),'E',id.toString().trim() );
		   ext.setLength(0);
		   } 
		 return i;
	 }
	 
	 int parse_reference(int i)
	 {
		   int end_index=body.indexOf("==",i);
		   int end_index1=body.indexOf("[[Category:",i);
		   int end_index2=body_len-1;
		   if(end_index1==-1)
		   {
			   end_index1=end_index2;
		   }
		  
		   if(end_index==-1 || end_index>end_index1)
		   {
			   end_index=end_index1;
		   }
		   
		   //System.out.println("-------------------------------"+end_index+"-----------------------");
		   
		   StringBuilder ref=new StringBuilder();
		 
		   while(i<=end_index)
		   {
			   if( (body.charAt(i)>='A' && body.charAt(i)<='Z')|| (body.charAt(i)>='a' && body.charAt(i)<='z') )
			   {
				   ref.append(body.charAt(i));
			   }
			   else
			   {
				   if(ref.toString().length()>1)
				   {
				//   System.out.println("id : "+ id.toString()+" : "+ref.toString());
				   WordCleaner.cleanword(ref.toString().toLowerCase().trim(),'R',id.toString().trim() );
				   ref.setLength(0);
				   } 
			   }
			   i++;
		   }
		   
		   if(ref.toString().length()>1)
		   {
			  // System.out.print(ref.toString());
		   WordCleaner.cleanword(ref.toString().toLowerCase().trim(),'R',id.toString().trim() );
		   ref.setLength(0);
		   }
		   
		   return end_index;
	 }
	 
	 
	 
	}