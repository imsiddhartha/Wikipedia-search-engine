import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

class term
{
	String word;
	long offset;
	 public term(String word, long offset) {
		 this.offset=offset;
		 this.word=word;
		 //		    this.id = id;
//		    this.name = name;
		  }
	public term() {
		// TODO Auto-generated constructor stub
	}
	
}

class titleterm
{
	int word;
	long offset;
	 public titleterm(int word, long offset) {
		 this.offset=offset;
		 this.word=word;
		 //		    this.id = id;
//		    this.name = name;
		  }
	public titleterm() {
		// TODO Auto-generated constructor stub
	}
	
}

public class Query {

	/**
	 * @param args
	 */
	public static List<term> ter_main_index=new ArrayList<term>();
	public static List<titleterm> ter_title_index=new ArrayList<titleterm>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long t1=System.currentTimeMillis();
		StopRem.initialize();
		ReadTerInd("indexes/TerrIndex");
		ReadTerInd("indexes/TerrTitInd");
		Comparator<term> c = new Comparator<term>() {
		      public int compare(term u1, term u2) {
		        return u1.word.compareTo(u2.word);
		      }
		    };
		    Comparator<titleterm> c1 = new Comparator<titleterm>() {
			      public int compare(titleterm u1, titleterm u2) {
			        return u1.word-u2.word;
			      }
			    };
		   long t2=System.currentTimeMillis();
		  
		while(true)
		{
			System.out.print("Enter Query:");		 
			try {
				
				Scanner sc=new Scanner(System.in);
				String query=sc.nextLine();
				query=query.replaceAll("[-+.^,!@#$%&;\\*\\+\\-\\/\\[\\]\\{\\}\\~`]"," ");		//colon should not here!
				//System.out.println(query);
				long StartTime=System.currentTimeMillis();
				
			    StringTokenizer st=new StringTokenizer(query," ");

			   Map<String,Integer> data=new HashMap<String, Integer>();
			    int wttitle=10000,wtinfo=25,wtcat=20,wtbody=5,wtref=2,wtext=2;
			   int totcount=0;
			   while(st.hasMoreTokens())
			   {
					String str=st.nextToken();
					str=str.trim();
					str=str.toLowerCase();
					//System.out.println(str);
					if(str.contains("t:"))
					{
						str=str.substring(str.indexOf(':')+1);
						wttitle=wttitle*1;
					}
					if(str.contains("i:"))
					{
						str=str.substring(str.indexOf(':')+1);
						wtinfo=wtinfo*1000;
						if(wtinfo>10000)
							wtinfo=9000;
					}
					if(str.contains("c:"))
					{
						str=str.substring(str.indexOf(':')+1);
						wtcat=wtcat*1000;
						if(wtcat>10000)
							wtcat=9000;
					}
					if(str.contains("b:"))
					{
						str=str.substring(str.indexOf(':')+1);
						//System.out.println("body "+str);
						wtbody=wtbody*1000;
							if(wtbody>10000)
								wtbody=9000;
					}
					if(str.contains("e:"))
					{
						str=str.substring(str.indexOf(':')+1);
						wtext=wtext*1000;
						if(wtext>10000)
							wtext=9000;
					}
					if(str.contains("r:"))
					{
						str=str.substring(str.indexOf(':')+1);
						wtref=wtref*1000;
						if(wtref>10000)
							wtref=9000;
					}
					
					{
					    Stemmer s= new Stemmer();
				    	s.add(str.toCharArray(),str.length());
				    	s.stem();
				    	str=s.toString();
				    	int index = Collections.binarySearch(ter_main_index, new term(str,0), c);		/*http://stackoverflow.com/questions/901944/implement-binary-search-in-objects*/
				    	long start=0;long end=0;
				    	//System.out.println(index);
				        if(index<0)
				        {
				        	start=ter_main_index.get(-1*index-2).offset;			//!! pe exception
				        }
				        else
				        {
				        	start=ter_main_index.get(index).offset;
				        }
				        long main_offset=-1;
				        RandomAccessFile secfile = new RandomAccessFile("indexes/SecIndex", "r");
				        secfile.seek(start);
				        for(int i=0;i<50;i++)
				        {
				        	String line=secfile.readLine();
				        	String w=line.substring(0,line.indexOf(' '));		//~ pe exception
				        	if(w.equals(str))
				        	{
				        		main_offset=Long.parseLong(line.substring(line.indexOf(' ')+1));
				        		break;
				        	}
				        }
				        if(main_offset==-1)
				        {
				        	//System.out.println("ERROR!!!! Not found in secondary index");
				        	//System.exit(0);
				        	continue;
				        }
				        RandomAccessFile mainfile = new RandomAccessFile("indexes/newindexBig", "r");
				        mainfile.seek(main_offset);
				        StringBuilder sb=new StringBuilder();
				        
				        for(int i=0;;i++)
				        {
				        	char ch=(char)mainfile.readByte();
				        	sb.append(ch);
				        	if((i>=45000 && ch==';') || ch=='\n')
				        		{
				        			//System.out.println(ch+i);
				        			break;
				        		}
				        }
				        // System.out.println("136");
				        String posting=sb.toString();
				        //System.out.println(posting);
				        String wordinposting=posting.substring(0,posting.indexOf('-'));
				        posting=posting.substring(posting.indexOf('-')+1);
				        StringTokenizer stt=new StringTokenizer(posting,";");
				     	int count=0;
				     	// System.out.println("143");
				        while(stt.hasMoreTokens())
				        {
				        	String t="",info="",cat="",b="",r="",e="",docid="";
					     	Obj o=new Obj();
				        	String onelist=stt.nextToken();
				        	onelist=onelist.trim();
				        	if(onelist.length()<1)
				        		break;
				        	if(onelist.equals(" ")|| onelist.equals('\n'))
				        		break;
				        	
				        	docid=onelist.substring(0, onelist.indexOf(':'));
				        	int rank=0;
				        	//System.out.println("151");
				        	for(int j=onelist.indexOf(':')+1;j<onelist.length();)
					     	{
					     		char ch;
					     		if(onelist.charAt(j)=='T')
					     		{
					     			j++;
					     			ch=onelist.charAt(j);
					     			while(j<onelist.length() && ch>=48 && ch<=57)
					     			{
					     				ch=onelist.charAt(j);
					     				t=t+ch;
					     				j++;
					     				if(j<onelist.length())
					     					ch=onelist.charAt(j);
					     			}
					     			//o.title=Integer.parseInt(t);
					     			o.title=1;
					     		}
					     		if(j<onelist.length() && onelist.charAt(j)=='I')
					     		{
					     			j++;
					     			ch=onelist.charAt(j);
					     			while(j<onelist.length() && ch>=48 && ch<=57)
					     			{
					     				info=info+ch;
					     				j++;
					     				if(j<onelist.length())
					     					ch=onelist.charAt(j);
					     			}
					     			o.info_box=Integer.parseInt(info);
					     			
					     		}
					     		if(j<onelist.length() && onelist.charAt(j)=='C')
					     		{
					     			j++;
					     			ch=onelist.charAt(j);
					     			while(j<onelist.length() && ch>=48 && ch<=57)
					     			{
					     				cat=cat+ch;
					     				j++;
					     				if(j<onelist.length())
					     					ch=onelist.charAt(j);
					     			}
					     			o.categories=Integer.parseInt(cat);
					     			
					     		}
					     		if(j<onelist.length() && onelist.charAt(j)=='B')
					     		{
					     			j++;
					     			ch=onelist.charAt(j);
					     			while(j<onelist.length() && ch>=48 && ch<=57)
					     			{
					     				b=b+ch;
					     				j++;
					     				if(j<onelist.length())
					     					ch=onelist.charAt(j);
					     			}
					     			o.body=Integer.parseInt(b);
					     		
					     		}
					     		if(j<onelist.length() && onelist.charAt(j)=='R')
					     		{
					     			j++;
					     			ch=onelist.charAt(j);
					     			while(j<onelist.length() && ch>=48 && ch<=57)
					     			{
					     				r=r+ch;
					     				j++;
					     				if(j<onelist.length())
					     					ch=onelist.charAt(j);
					     			}
					     			o.ref=Integer.parseInt(r);
					     			
					     		}
					     		if(j<onelist.length() && onelist.charAt(j)=='E')
					     		{
					     			j++;
					     			ch=onelist.charAt(j);
					     			while(j<onelist.length() && ch>=48 && ch<=57)
					     			{
					     				e=e+ch;
					     				j++;
					     				if(j<onelist.length())
					     					ch=onelist.charAt(j);
					     			}
					     			o.ext_links=Integer.parseInt(e);
					     			
					     		}
					     	}
				        	rank=o.title*wttitle+o.info_box*wtinfo+o.categories*wtcat+o.body*wtbody+o.ref*wtref+o.ext_links*wtext;
				        	if(data.containsKey(docid))
				        	{
				        		int cur=data.get(docid);
				        		data.put(docid, rank+cur);
				        	}
				        	else
				        		data.put(docid, rank);
				        	count++;
				        }
				        totcount = count+totcount;
					}
				}
				//System.out.println(data.size());
				if(data.size()==0)
				{
						System.out.println("No results found");
						continue;
				}
			   List<String> maxdocid=new ArrayList<String>();
			   List<String> maxtitle=new ArrayList<String>();
			   int size=10;
			   if(data.size()<10)
			   		size=data.size();
			   for(int i=0;i<size;i++)
			   {
				   Map.Entry<String, Integer> maxEntry = null;

				   for (Map.Entry<String, Integer> entry : data.entrySet())
				   {
				       if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
				       {
				           maxEntry = entry;
				       }
				   }
				   maxdocid.add(i, maxEntry.getKey());
				   data.remove(maxEntry.getKey());
			   }
			   
			   for(int i=0;i<size;i++)
			   {
				   int curmax=Integer.parseInt(maxdocid.get(i));
				   int index=Collections.binarySearch(ter_title_index, new titleterm(curmax,0),c1);
				   long start=0;
				   
				   //System.out.println(index);
			        if(index<0)
			        {
			        	start=ter_title_index.get(-1*index-2).offset;
			        	//System.out.println("but offset of start point ="+start);
			        }
			        else
			        {
			        	start=ter_title_index.get(index).offset;
			        }
				   RandomAccessFile f1=new RandomAccessFile("indexes/SecTitInd", "r");
				   f1.seek(start);long tit_main_offset=-1;
				   
				   for(int j=0;j<50;j++)
				   {
					   String s="";
					   if((s=f1.readLine())!=null)
					   {
						   //	System.out.println(s);
						   int curid=Integer.parseInt(s.substring(0,s.indexOf(' ')));
						   if(curid==curmax)
						   {
						   		tit_main_offset=Long.parseLong(s.substring(s.indexOf(' ')+1));
							   break;
						   }
					   }
				   }
				   
				   RandomAccessFile f2=new RandomAccessFile("indexes/TitleInd", "r");
				   f2.seek(tit_main_offset);
				   String s=f2.readLine();
				   //	System.out.println("title= "+s+" "+curmax);
				   maxtitle.add(s.substring(s.indexOf(' ')));
			   }
			   //System.out.println(maxdocid);
			   
			   
			   for(int i=0;i<size;i++)
			   		System.out.println(1+i+"."+maxtitle.get(i));

			   	long EndTime=System.currentTimeMillis();
			   	System.out.println((EndTime-StartTime+t2-t1)+"ms");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
    	
	}

	private static void ReadTerInd(String string) {
		// TODO Auto-generated method stub
		try {
			BufferedReader trrind=new BufferedReader(new FileReader(string));
			String str="";int i=0;
			if(string.equalsIgnoreCase("indexes/TerrIndex"))
			{
				while((str=trrind.readLine())!=null)
				{
					String ww=str.substring(0, str.indexOf(' '));
					long ll=Long.parseLong(str.substring(str.indexOf(' ')+1));
					term t=new term(ww,ll);
					ter_main_index.add(i,t);
					i++;
				}
			}
			else
			{	
				while((str=trrind.readLine())!=null)
				{
					titleterm t=new titleterm();
					t.word=Integer.parseInt(str.substring(0, str.indexOf(' ')));
					
					t.offset=Long.parseLong(str.substring(str.indexOf(' ')+1));
					//System.out.println(""+t.word+":"+t.offset);
					ter_title_index.add(i,t);
					i++;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

/*
gtx
9/11
he who must not be named
doom 3
t:sachin b:flipkart
t:two t:towers i:first i:edition
t:sun i:computer
*/
