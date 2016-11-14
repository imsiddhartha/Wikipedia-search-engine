import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;


public class test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br=new BufferedReader(new FileReader("newindexBig"));
			File file=new File("newindex1");
			if(!file.exists())
				file.createNewFile();
			BufferedWriter bw=new BufferedWriter(new FileWriter(file));
			
			String s="";long count=0;
			while((s=br.readLine())!=null)
			{
				s=s+";";
				//System.out.println(s);
				bw.write(s+'\n');
				if(count%100000==0)
					System.out.println(count);
				count++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
