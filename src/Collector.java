//import javax.imageio.*;
import java.util.Scanner;
//import java.awt.Image;
import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class Collector {	
	public static void main(String[] args) {
		Scanner sc;
		try {
			//read names file, contains all characters names as they appear in the assets url list.
			sc = new Scanner(new File("Names.txt"));
		
			while(sc.hasNext())
			{
				String character = sc.nextLine();
				character = character.toLowerCase();
				
				for(int i = 1; i < 9; i ++) {
					String num = "";
					if (i > 1)
						//1st render does not contain number, the rest do.
						num = ""+i;
					
						//if the first render already exists, assume all files have been gathered and skip character.
					if (!(new File("renders/" + character + "/" + "character"+ ".png").exists())) {
						try {
							//url is generalized for each character and skin no., plug in relevant data to complete. 
							URL url = new URL("https://www.smashbros.com/assets_v2/img/fighter/" + character + "/main" + num + ".png");
							
							//most of this is pulled from stack overflow but works fine.
							InputStream in = new BufferedInputStream(url.openStream());
							ByteArrayOutputStream out = new ByteArrayOutputStream();
							byte[] buf = new byte[1024];
							int n = 0;
							while (-1!=(n=in.read(buf)))
							{
							   out.write(buf, 0, n);
							}
							out.close();
							in.close();
							byte[] response = out.toByteArray();
							
							//generate file to correct folder with skin no. in name.
							File f = new File("renders/" + character + "/" + character + num + ".png");
							f.getParentFile().mkdirs();
							FileOutputStream fos = new FileOutputStream(f);
							fos.write(response);
							fos.close();
							
						}catch (IOException e) {e.printStackTrace(); }
					} else {
						System.out.println(character + "'s files already present on this machine. Skipped.");
						//skip rest of collection for this char.
						i = 9;
					}
				}
				//output when current set of renders are done. 
				System.out.println(character + " complete!");
			}
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	} 
}