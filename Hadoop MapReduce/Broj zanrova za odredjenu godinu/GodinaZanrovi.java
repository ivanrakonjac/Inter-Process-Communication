

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

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
import org.opencypher.relocated.ujson.IndexedValue.Str;

/**
 * Posmatraju se podaci sa filmovima sajta https://datasets.imdbws.com/
 * 
 * Potrebno je za zadati interval vremena pronaci koliko je koje godine bilo filmova kog zanra
 * 
 * @author Ika
 * 
 * Podaci o filmovima su dostupni u arhivi title.basics.tsv.gz
 *
 */

public class GodinaZanrovi {
	
	public static class FilmMapper extends Mapper<LongWritable, Text, Text, Text>{
		
		

		@Override
		protected void cleanup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			System.out.println("Zavrsavam <------------------");
		}

		@Override
		protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			System.out.println("Pocinjem ------------------> ");
		}

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			String line = value.toString();
			String[] data = line.split("\t");
			
			String originalTitle = data [3];
			String startYear = data [5];
			
		
			if (data[8].contains(",")) {
			
				String[] genres = data[8].split(",");
				
				for (String genre : genres) {
					Text key1 = new Text(startYear + "#" + genre);
					Text value1 = new Text ("1");
					context.write(key1, value1);
					System.out.println(key1 + ", " + value1);
					/*System.out.println(genre);*/
				}
				
			}else {
				Text key1 = new Text(startYear + "#" + data[8]);
				Text value1 = new Text ("1");
				context.write(key1, value1);
				System.out.println(key1 + ", " + value1);
				/*System.out.println(data[8]);*/
			}
			
			
		}
	}
	
	public static class  FilmReducer extends Reducer<Text, Text, Text, Text> {
		
		
		private HashMap<String, HashSet<String>> yearHasSetMap = new HashMap<String, HashSet<String>>();
		

		@Override
		protected void cleanup(Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			for (String i : yearHasSetMap.keySet()) {
			  System.out.println("key: " + i + " value: " + yearHasSetMap.get(i).toString());
			  
			  Text key = new Text(i);
			  Text value = new Text ("" + yearHasSetMap.get(i).size() );
			  context.write(key, value);
			}
			
			System.out.println("Zavrsavam reducer <------------------");
		}

		@Override
		protected void setup(Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
			System.out.println("Pocinjem reducer ------------------> ");
		}

		@Override
		protected void reduce(Text key, Iterable<Text> value, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			String line = key.toString();
			String[] data = line.split("#");
			
			String year = data[0];
			String genre = data[1];
			
			if(yearHasSetMap.containsKey(year)) {
				yearHasSetMap.get(year).add(genre);
				//System.out.println("Dodat " + genre + " u " + year);
			}else {
				yearHasSetMap.put(year, new HashSet<String>());
				//System.out.println("Dodata " + year);
				yearHasSetMap.get(year).add(genre);
				//System.out.println("Dodat " + genre + " u " + year);
			}
			
			
			System.out.println(data[0] + ", " + data[1] + ", " + value.toString());

		}
		
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance();
		job.setJarByClass(GodinaZanrovi.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.setMapperClass(FilmMapper.class);
		job.setReducerClass(FilmReducer.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
		
		System.out.println("Test");
	}

}
