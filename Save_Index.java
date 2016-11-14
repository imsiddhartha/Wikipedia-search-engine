import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.*;


class Item2
{
  String doc_id;
  Obj obj;
  int rank; 
}
class Comp2 implements Comparator<Item2> 
{ 
    public int compare(Item2 x, Item2 y) 
    { 
       return y.rank - x.rank;
        
      //descending order
          //return compareQuantity - this.quantity;
      //ascending order
          //return this.quantity - compareQuantity;
    } 
}


public class Save_Index 
{
    static void save_index(String output)  throws Exception
      {
        try
        {
          //System.out.println(output);
          output="Index/"+output;
          //System.out.println(output);
          File file = new File(output);
        // System.out.print("Here");
      // if file doesnt exists, then create it
      if (!file.exists()) {
        file.createNewFile();
      }

      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
  //     public int title;
  // public int  body;
  // public int info_box;
  // public int categories;
  // public int ext_links;
  // public int ref;
        for (Entry<String, HashMap<String, Obj>> entry : ReadXMLFile.index.entrySet()) 
        {
          String word_name=entry.getKey();
          HashMap<String,Obj> lmap = new HashMap<String,Obj>(entry.getValue());
          bw.write(word_name+"-");
          for(Entry<String,Obj> loc_entry:lmap.entrySet())
          {

            String doc_name = loc_entry.getKey();
            Obj obj = loc_entry.getValue();
            String append = doc_name+":";
           // String ext_links="",ref="",cat="",info="",body="",title="";
            if(obj.title!=0)
            {
              append=append+"T"+obj.title;
              //title=""+obj.title;
            }

            if(obj.info_box!=0)
            {
              //info=""+obj.info_box;
            append=append+"I"+obj.info_box;
            }

            if(obj.categories != 0)
              {
                //cat=""+obj.categories;
                append=append+"C"+obj.categories;
              }
            
            if(obj.body!=0)
            {
              append=append+"B"+obj.body;
              // body=""+obj.body;
            }

            if(obj.ref != 0)
            {
               append=append+"R"+obj.ref;
              //append=append+"B"+body;
            }
            if(obj.ext_links!=0)
            {
              append=append+"E"+obj.ext_links;
              // ext_links=""+obj.ext_links;
            }
            append=append+";";
            //String append = doc_name+":"+title+","+info+","+cat+","+body+","+ref+","+ext_links+";";
            //String append = doc_name+":"+title+","+info+","+cat+","+body+","+ref+";";
            //System.out.println(word_name+"-"+append);
            bw.write(append);
          }
          bw.write("\n");
        }

      bw.close();
      ReadXMLFile.index.clear();
    
    
    }catch(Exception e){}
    }
    static void save_Dense_index(String output)  throws Exception
    {
      try
      {
        //System.out.println(output);
        output="Index/"+output;
        //  System.out.println(output);
        File file = new File(output);
      // System.out.print("Here");
    // if file doesnt exists, then create it
    if (!file.exists()) {
      file.createNewFile();
    }

    FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
    BufferedWriter bw = new BufferedWriter(fw);
      for (Entry<String, HashMap<String, Obj>> entry : CreateDenseIndex.index.entrySet()) 
      {
          String word_name=entry.getKey();
          HashMap<String,Obj> lmap = new HashMap<String,Obj>(entry.getValue());
          Comparator<Item2> comp2 = new Comp2();
          PriorityQueue<Item2> q =  new PriorityQueue<Item2>(lmap.size()+2,comp2);
          bw.write(word_name+"-");
          for(Entry<String,Obj> loc_entry:lmap.entrySet())
          {
            Item2 it=new Item2();
            it.doc_id=loc_entry.getKey();
            it.obj=loc_entry.getValue();
            it.rank=it.obj.title*1000+it.obj.info_box*25+it.obj.categories*20+it.obj.body*2+it.obj.ref*1+it.obj.ext_links*1;
            q.add(it);
          } 
          while(!q.isEmpty())
          {
            Item2 it1=q.poll();
            String doc_name=it1.doc_id;
            Obj obj=it1.obj;
            
            String append = doc_name+":";
            //String ext_links="",ref="",cat="",info="",body="",title="";
            if(obj.title!=0)
            {
              append=append+"T"+obj.title;
              //title=""+obj.title;
            }

            if(obj.info_box!=0)
            {
              //info=""+obj.info_box;
            append=append+"I"+obj.info_box;
            }

            if(obj.categories != 0)
              {
                //cat=""+obj.categories;
                append=append+"C"+obj.categories;
              }
            
            if(obj.body!=0)
            {
              append=append+"B"+obj.body;
              // body=""+obj.body;
            }

            if(obj.ref != 0)
            {
               append=append+"R"+obj.ref;
              //append=append+"B"+body;
            }
            if(obj.ext_links!=0)
            {
              append=append+"E"+obj.ext_links;
              // ext_links=""+obj.ext_links;
            }
            append=append+";";
            bw.write(append);
          }
          bw.write("\n");
      }

    bw.close();
    CreateDenseIndex.index.clear();
  }catch(Exception e){}
  }
}
