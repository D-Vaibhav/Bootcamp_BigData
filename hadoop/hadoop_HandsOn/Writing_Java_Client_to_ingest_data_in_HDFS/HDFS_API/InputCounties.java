package hdfs;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class InputCounties {


	public static void main(String[] args) throws IOException{
		Configuration conf = new Configuration();
		Path dir = new Path("counties");
		FileSystem fs = FileSystem.get(conf);
		
		//original code
		/*if(!fs.exists(dir)) {
			fs.mkdirs(dir);
		}*/
		
		//Amit: Above code is replaced as shown below
		if(!fs.exists(dir)) {
			fs.mkdirs(dir);
		} else {
			fs.delete(dir, true);
		}
		
		System.out.println("Created counties directory in HDFS");
		
		for(int i = 1; i <= 4; i++) {
			String filename = "counties_" + i + ".csv";
			Path localSrc = new Path("counties/" + filename);
			Path dest = new Path("counties/" + filename);
			fs.copyFromLocalFile(localSrc, dest); 
		}		
		
	}

}
