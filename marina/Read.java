package marina;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Read {
	public static void main(String[] args) throws ParseException, IOException {
		//ReadCapilar marinara = new ReadCapilar();
		//marinara.maximum(1234567);;
		ArrayList<Long> readtimes = new ArrayList<Long>();
		for(String u: readSmallTextFile()) {
			System.out.println(u);
			ReadCapilar marinara = new ReadCapilar();
			marinara.stockurl=u;
			marinara.getnews();
			System.out.println(marinara.timestamps.size()+" "+marinara.titles.size()+" "+marinara.urls.size());
			for(int sindex = 0;sindex<marinara.timestamps.size();sindex++) {
				System.out.println(marinara.timestamps.get(sindex)+" "+marinara.titles.get(sindex)+" "+marinara.urls.get(sindex));
				readtimes.add(marinara.timestamps.get(sindex));
			}
		}
		maximum(Collections.max(readtimes));
	}
	public static List<String> readSmallTextFile() throws IOException {
	    Path path = Paths.get("C:/placebo/lucia/newsurls.txt");
	    return Files.readAllLines(path,StandardCharsets.UTF_8);
	  }
	public static void maximum(long unixTime) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:/placebo/lucia/minimum.txt"));
        writer.write(Long.toString(unixTime));
        writer.close();
	}
}
