package mapReduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Filmovi {

	/*
	 * Posmatraju se podaci sa filmovima sajta https://datasets.imdbws.com/
	 * 
	 * Potrebno je za zadati interval vremena pronaci koliko je koje godine bilo
	 * filmova kojeg zanra.
	 * 
	 * Podaci o filmovima su dostupni u arhivi title.basics.tsv.gz
	 * 
	 */
	
	public static class FilmMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
		private static final IntWritable one = new IntWritable(1);
		
		int start=0;
		int end = 0;
		
		

		@Override
		protected void setup(Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			
			super.setup(context);
			start = context.getConfiguration().getInt("start", 0);
			end = context.getConfiguration().getInt("end", 0);
			
			//System.out.println("start: " + start + " end: " + end);
			
		}



		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			
			String line = value.toString();
			String[] data = line.split("\t");

			int year = Integer.parseInt(data[5]);
			if (start <= year && year <= end) {
				String[] categories = data[8].split(",");
				for (String category : categories) {
					Text key1 = new Text(year + "-" + category);
					context.write(key1, one);
				}
				System.out.println(data[0] + " " + year);
			}
		}
		
	}
	
	public static class FilmReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

		@Override
		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			
			int cnt = 0;
			for(IntWritable val : values) {
				cnt = cnt + val.get();
			}
			context.write(key, new IntWritable(cnt));
		}

	}
	
	

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration c = new Configuration();
		c.setInt("start", 2000);
		c.setInt("end", 2005);
		
		Job job = Job.getInstance(c, "filmovi5");
		job.setJarByClass(Filmovi5.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(FilmMapper.class);
		job.setReducerClass(FilmReducer.class);
		job.setCombinerClass(FilmReducer.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
		
		System.out.println("Test");

	}

}
