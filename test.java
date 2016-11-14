import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.Buffer;
import java.util.StringTokenizer;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try 
		{
			BufferedReader br=new BufferedReader(new FileReader("Index_final.txt"));
			  File file = new File("newindexBig");
			  if (!file.exists()) {
		 	       file.createNewFile();
		 	     }

			  FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw=new BufferedWriter(fw);
			String str="";long count1=0;
			long start=System.currentTimeMillis();
			while((str=br.readLine())!=null)
			{
				StringTokenizer st=new StringTokenizer(str,":");
				StringBuilder sb=new StringBuilder();
				int count=0;
				//System.out.println(str);
				while(st.hasMoreElements())
				{

					if(count==0)
					{
						sb.append(st.nextToken());
						// if(count1>193686)
						// 	System.out.print(sb.toString());
						sb.append('-');
						sb.append(st.nextToken());
						sb.append(":");
					}
					else
					{
						String s=st.nextToken();
						// if(count1>193686)
						// 	System.out.println(" "+s);
						if(s.equals("T")||s.equals("I")||s.equals("C")||s.equals("B")||s.equals("R")||s.equals("E"))
						{
							sb.append(s);
							sb.append(st.nextToken());
						}
						else
						{
							sb.append(";");
							sb.append(s);
							sb.append(":");
						}
						
					}
					count++;
				}
				count1++;
				sb.append('\n');
				// if(count1>=193686)
				// 	System.out.print(sb.toString());
				bw.write(sb.toString());
				sb.setLength(0);
				if(count1%100000==0)
					System.out.println(count1);
			}
			br.close();
			bw.close();
			long end =System.currentTimeMillis();
			System.out.println(count1);
			System.out.println((end-start)+"ms");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
