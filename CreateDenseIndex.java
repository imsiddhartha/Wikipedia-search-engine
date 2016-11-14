import java.io.*;
import java.util.*;

class Comp implements Comparator<Item> 
{ 
    public int compare(Item x, Item y) 
    { 
        return x.word.compareTo(y.word);
    } 
}
public class CreateDenseIndex {

	
	static BufferedReader br[] = new BufferedReader[ReadXMLFile.index_count+2];
	static Comparator<Item> com = new Comp();
	static PriorityQueue<Item> pq =  new PriorityQueue<Item>(ReadXMLFile.index_count+2,com);
	public static Map<String, HashMap<String, Obj>> index = new TreeMap<String,HashMap<String,Obj>>();

	static void create_dense_ind()
	{
			readFirstLine();
			while(true)
			{
				if(pq.isEmpty())
					break;
				if(index.size()==60)
				{
					write_ind_tofile();					
					index.clear();
				}
				getmin();
			}
			if(index.size()!=0)
			{
				write_ind_tofile();
					index.clear();
			}
	}
	private static void write_ind_tofile() {
		// TODO Auto-generated method stub
		try {
			Save_Index.save_Dense_index("MainIndex");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void getmin() 
	{
		// TODO Auto-generated method stub
		if(pq.isEmpty())
			return;
//		Item ss=pq.peek();
		while(true)
		{
			if(pq.isEmpty())
				break;
			Item s=pq.poll();
			if(s!=null)
				puttoqueue(s.filenum);
			
			if(index.get(s.word)==null)
		    {
		    //	System.out.println("New index entry for "+str);
		        HashMap<String,Obj> lmap=new HashMap<String,Obj>();
		        parsePList(s,lmap);
		    }
			else
			{
				for(int i=0;i<s.doc_id.size();i++)
			     {
			     	String t="",info="",c="",b="",r="",e="";
			     	Obj o=new Obj();
			     	String str=s.list.get(i);
			     
			     	for(int j=0;j<str.length();)
			     	{
			     		char ch;
			     		if(str.charAt(j)=='T')
			     		{
			     			j++;
			     			ch=str.charAt(j);
			     			while(j<str.length() && ch>=48 && ch<=57)
			     			{
			     				ch=str.charAt(j);
			     				t=t+ch;
			     				j++;
			     				if(j<str.length())
			     					ch=str.charAt(j);
			     			}
			     			o.title=Integer.parseInt(t);
			     			
			     		}
			     		if(j<str.length() && str.charAt(j)=='I')
			     		{
			     			j++;
			     			ch=str.charAt(j);
			     			while(j<str.length() && ch>=48 && ch<=57)
			     			{
			     				info=info+ch;
			     				j++;
			     				if(j<str.length())
			     					ch=str.charAt(j);
			     			}
			     			o.info_box=Integer.parseInt(info);
			     			
			     		}
			     		if(j<str.length() && str.charAt(j)=='C')
			     		{
			     			j++;
			     			ch=str.charAt(j);
			     			while(j<str.length() && ch>=48 && ch<=57)
			     			{
			     				c=c+ch;
			     				j++;
			     				if(j<str.length())
			     					ch=str.charAt(j);
			     			}
			     			o.categories=Integer.parseInt(c);
			     			
			     		}
			     		if(j<str.length() && str.charAt(j)=='B')
			     		{
			     			j++;
			     			ch=str.charAt(j);
			     			while(j<str.length() && ch>=48 && ch<=57)
			     			{
			     				b=b+ch;
			     				j++;
			     				if(j<str.length())
			     					ch=str.charAt(j);
			     			}
			     			o.body=Integer.parseInt(b);
			     		
			     		}
			     		if(j<str.length() && str.charAt(j)=='R')
			     		{
			     			j++;
			     			ch=str.charAt(j);
			     			while(j<str.length() && ch>=48 && ch<=57)
			     			{
			     				r=r+ch;
			     				j++;
			     				if(j<str.length())
			     					ch=str.charAt(j);
			     			}
			     			o.ref=Integer.parseInt(r);
			     			
			     		}
			     		if(j<str.length() && str.charAt(j)=='E')
			     		{
			     			j++;
			     			ch=str.charAt(j);
			     			while(j<str.length() && ch>=48 && ch<=57)
			     			{
			     				e=e+ch;
			     				j++;
			     				if(j<str.length())
			     					ch=str.charAt(j);
			     			}
			     			o.ext_links=Integer.parseInt(e);
			     			
			     		}
			     	}
			     	//System.out.println(s.filenum+" "+s.word+" rank of doc "+it.list+" "+s.doc_id.get(i)+" "+it.rank+" ");
			     	index.get(s.word).put(s.doc_id.get(i), o);
			     	//lmap.put(s.doc_id.get(i),o);
			     	//System.out.println("File no "+s.filenum+" "+s.word+"-"+s.doc_id.get(i)+":"+t+","+info+","+c+","+b+","+r+","+e);
			     }
			}
			//System.out.println(s.word+" "+index.get(s.word));
			if(!pq.isEmpty()&&s.word!=pq.peek().word)
				break;

		}
	}
	
private static void parsePList(Item s, HashMap<String,Obj> lmap) {
		// TODO Auto-generated method stub
	//s.obj=new ArrayList<Obj>(); 
	for(int i=0;i<s.doc_id.size();i++)
     {
     	String t="",info="",c="",b="",r="",e="";
     	Obj o=new Obj();
     	String str=s.list.get(i);
     	
     	for(int j=0;j<str.length();)
     	{
     		char ch;
     		if(str.charAt(j)=='T')
     		{
     			j++;
     			ch=str.charAt(j);
     			while(j<str.length() && ch>=48 && ch<=57)
     			{
     				ch=str.charAt(j);
     				t=t+ch;
     				j++;
     				if(j<str.length())
     					ch=str.charAt(j);
     			}
     			o.title=Integer.parseInt(t);
     		}
     		if(j<str.length() && str.charAt(j)=='I')
     		{
     			j++;
     			ch=str.charAt(j);
     			while(j<str.length() && ch>=48 && ch<=57)
     			{
     				info=info+ch;
     				j++;
     				if(j<str.length())
     					ch=str.charAt(j);
     			}
     			o.info_box=Integer.parseInt(info);
     		}
     		if(j<str.length() && str.charAt(j)=='C')
     		{
     			j++;
     			ch=str.charAt(j);
     			while(j<str.length() && ch>=48 && ch<=57)
     			{
     				c=c+ch;
     				j++;
     				if(j<str.length())
     					ch=str.charAt(j);
     			}
     			o.categories=Integer.parseInt(c);
     		}
     		if(j<str.length() && str.charAt(j)=='B')
     		{
     			j++;
     			ch=str.charAt(j);
     			while(j<str.length() && ch>=48 && ch<=57)
     			{
     				b=b+ch;
     				j++;
     				if(j<str.length())
     					ch=str.charAt(j);
     			}
     			o.body=Integer.parseInt(b);
     		}
     		if(j<str.length() && str.charAt(j)=='R')
     		{
     			j++;
     			ch=str.charAt(j);
     			while(j<str.length() && ch>=48 && ch<=57)
     			{
     				r=r+ch;
     				j++;
     				if(j<str.length())
     					ch=str.charAt(j);
     			}
     			o.ref=Integer.parseInt(r);
     		}
     		if(j<str.length() && str.charAt(j)=='E')
     		{
     			j++;
     			ch=str.charAt(j);
     			while(j<str.length() && ch>=48 && ch<=57)
     			{
     				e=e+ch;
     				j++;
     				if(j<str.length())
     					ch=str.charAt(j);
     			}
     			o.ext_links=Integer.parseInt(e);
     		}
     	}
     //s.obj.add(i,o);
     	lmap.put(s.doc_id.get(i),o);
     	//System.out.println("File no "+s.filenum+" "+s.word+"-"+s.doc_id.get(i)+":"+t+","+info+","+c+","+b+","+r+","+e);
     }
	 index.put(s.word, lmap);
	}



private static void puttoqueue(int i) {
		// TODO Auto-generated method stub
		try 
		{
			//br[i] = new BufferedReader(new FileReader("Index/index"+i));
			String str="";
			if((str=br[i].readLine())!=null)
			{
				
				Item s=new Item();
				s.filenum=i;
				s.word=str.substring(0,str.indexOf('-'));
				
				int len=lenOfPostingList(str);
				int col=0,scol=0;
				//System.out.print(s.word+"-");
				for(int j=0;j<len;j++)
				{
					if(j==0)
					{
						col=str.indexOf(':',0);
						scol=str.indexOf(';',0);
						s.doc_id=new ArrayList<String>();
							s.list=new ArrayList<String>();
						s.doc_id.add(j, str.substring(str.indexOf('-')+1, col));
							s.list.add(j,str.substring(str.indexOf(':',0)+1,scol));
					}
					else
					{
						col=str.indexOf(':',col+1);
						s.doc_id.add(j, str.substring(scol+1, col));
						scol=str.indexOf(';',col);
						// System.out.println(scol+" "+col);
						s.list.add(j,str.substring(col+1,scol));
					}
					//System.out.print(s.doc_id+":"+s.list+";");	
				}
				//System.out.println(s.word+" "+s.doc_id+" "+s.list);
				pq.add(s);
			}
			else
			{
				//System.out.println("EOF of index"+i);
			}
		}
		catch (Exception e)
		{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}

	private static void readFirstLine() {
		// TODO Auto-generated method stub
		for(int i=0;i<=ReadXMLFile.index_count;i++)	
		{
			try 
			{
				br[i] = new BufferedReader(new FileReader("Index/index"+i));
				String str="";
				if((str=br[i].readLine())!=null)
				{
					
					Item s=new Item();
					s.filenum=i;
					s.word=str.substring(0,str.indexOf('-'));
					int len=lenOfPostingList(str);
					int col=0,scol=0;
					//System.out.print(s.word+"-");
					for(int j=0;j<len;j++)
					{
						if(j==0)
						{
							col=str.indexOf(':',0);
							scol=str.indexOf(';',0);
							s.doc_id=new ArrayList<String>();
							s.list=new ArrayList<String>();
							s.doc_id.add(j, str.substring(str.indexOf('-')+1, col));
							s.list.add(j,str.substring(str.indexOf(':',0)+1,scol));
						}
						else
						{
							col=str.indexOf(':',col+1);
							s.doc_id.add(j, str.substring(scol+1, col));
							scol=str.indexOf(';',col);
							// System.out.println(scol+" "+col);
							s.list.add(j,str.substring(col+1,scol));
						}
						//System.out.print(s.doc_id+" : "+s.list+" ; ");	
					}
					//System.out.println();
					pq.add(s);
//					System.out.println(str.substring(0,str.indexOf('-')));
//					System.out.println(str.substring(str.indexOf('-')+1, str.indexOf(':')));
//					System.out.println(str.substring(str.indexOf(':')+1));
				}
			}
			catch (Exception e)
			{
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
	}



	private static int lenOfPostingList(String str) {
		// TODO Auto-generated method stub
		int len=0;
		for(int i=0;i<str.length();i++)
		{
			if(str.charAt(i)==';')
				len++;
		}
		return len;
	}
}
