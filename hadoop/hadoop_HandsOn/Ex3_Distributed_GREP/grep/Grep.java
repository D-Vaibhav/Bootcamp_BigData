package grep;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer;
import org.apache.hadoop.util.StringUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Grep extends Configured implements Tool {
	
	public static class GrepMapper extends
			Mapper<LongWritable, Text, Text, IntWritable> {
		private String searchString;
		private Text outputValue = new Text();
		private final static IntWritable ONE = new IntWritable(1);
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			System.out.println("-----Inside map()-----");
			String [] words = StringUtils.split(value.toString(),'\\', ' ');
			for(String word: words) {
				//TODO: Use the method from String class which check the whether the word containd searchString
				if(word._____(searchString)) {
					outputValue.set(word);
					context.write(outputValue, ONE);
				}
			}
		}
		
		//TODO: Complete the setup method call, 
		//TODO: Look at the API doc and tell how many times this method will be called
		@Override
		protected void setup(______) throws IOException,
				InterruptedException {
			System.out.println("-----Inside setup()-----");
			searchString = context.getConfiguration().get("searchString");
		}
		
		
	}

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), "GrepJob");
		Configuration conf = job.getConfiguration();
		conf.set("searchString", args[2]);
		job.setJarByClass(getClass());
		
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		out.getFileSystem(conf).delete(out, true);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		
		//TODO
		job.setMapperClass(_____);
		
		// TODO
		//Look at the API documentation for IntSumReducer class
		job.setReducerClass(IntSumReducer.class);
		
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		//TODO
		job.setOutputKeyClass(_____);
		//TODO		
		job.setOutputValueClass();
		
		return job.waitForCompletion(true)?0:1;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int result = 0;
		try {
			result = ToolRunner.run(new Configuration(), 
							new Grep(),
							args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(result);
	}
}

//TODO
// Run the application by giving following command
// 1. yarn jar grep.jar constitution.txt grep_output the
