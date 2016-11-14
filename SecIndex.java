import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;


public class SecIndex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// BufferedReader br=new BufferedReader(new FileReader("newindexBig"));
			// File file=new File("SecIndex");
			//  BufferedReader br=new BufferedReader(new FileReader("SecIndex"));
			// File file=new File("TerrIndex");
			BufferedReader br=new BufferedReader(new FileReader("TitleInd"));
			File file=new File("SecTitInd");
			// BufferedReader br=new BufferedReader(new FileReader("SecTitInd"));
			// File file=new File("TerrTitInd");
			if(!file.exists())
			{
				file.createNewFile();
			}
			FileWriter fw=new FileWriter(file);
			BufferedWriter bw=new BufferedWriter(fw);
			
			String str="";
			long len=0;long offset=0;long count=0;
			while((str=br.readLine())!=null)
			{
				//String s=str.substring(0, str.indexOf('-'));			//for creating Secondary index delimeter is - else its space
				String s=str.substring(0, str.indexOf(' '));
				byte bytes[]=str.getBytes();
				s=s+" "+offset+'\n';
				len=bytes.length+1;
				offset=offset+len;
				 //if(count%50==0)
					bw.write(s);
				
				if(count%100000==0)
					System.out.println(count);
				//System.out.println(s);
				count++;
			}
			br.close();
			bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
