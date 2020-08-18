package mapReduce;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Filmovi2 {
	
	/*
	 * Posmatraju se podaci sa filmovima sajta https://datasets.imdbws.com/
	 * 
	 * Potrebno je naci film sa maximalnim brojem reditelja
	 * 
	 * Podaci o filmovima su dostupni u arhivi title.crew.tsv.gz
	 * 
	 */

	//Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
	//KEYIN ako citamo iz textualnog fajla liniju po liniju == LongWritable
	//VALUEIN je Text, jer iz ulaznog fajla citamo liniju po liniju
	//KEYOUT - sta je izlazni kljuc, ako ne znam sta je => Text
	//VALUEOUT - izlazna vrednost, ako ne znam sta je => Text
	public static class FilmMapper extends Mapper<LongWritable, Text, Text, Text>{

		
		int max;
		List<String> maxDirectorsFilms;
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			String line = value.toString();
			String[] data = line.split("\t");
			
			String title = data[0];
			String[] directors = data[1].split(",");
			String[] writers = data[2].split(",");
			
			int directorCnt = directors.length;
			
			if(directorCnt>max) {
				max = directorCnt;
				maxDirectorsFilms.clear();
			}
			
			//prosledjujem lokalne maximume
			if(directorCnt == max) {
				maxDirectorsFilms.add(title);
				
				//da ne bi doslo do pucanja zbog prekoracenja velicine memorije
				if(maxDirectorsFilms.size()==10000000) {
					for(String title1 : maxDirectorsFilms) {
						Text key1 = new Text("max");
						Text value1 = new Text(max + "\t" + title1);
						context.write(key1, value1);
						System.out.println("Poslao sam: " + key1 + " " + value1);
					}
					maxDirectorsFilms.clear();
				}
			}
		}
		
		//poziva se na pocetku mapiranja
		@Override
		protected void cleanup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			for(String title : maxDirectorsFilms) {
				Text key1 = new Text("max");
				Text value1 = new Text(max + "\t" + title);
				context.write(key1, value1);
				System.out.println("Poslao sam: " + key1 + " " + value1);
			}
			
		}
		
		//poziva se na kraju mapiranja
		@Override
		protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			max = 0;
			maxDirectorsFilms = new LinkedList<>();
		}
		
		
		
		
		
	}
	
	public static class FilmReducer extends Reducer<Text, Text, Text, Text>{

		//od razlicitih lokalnih maksimuma koje su nasli razliciti racunari treba naci globalni maximum
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			System.out.println("Usao sam u Reducer!");
			
			int max = 0;
			
			List<String> maxDirectorsFilms = new LinkedList<>(); 
			
			for(Text value: values) {
				String line = value.toString();
				String[] data = line.split("\t");
				
				int numDirectors = Integer.parseInt(data[0]);
				String title = data[1];
				
				//ako je nadjeni numDirectors > trenutnog max => zameni i isprazni listu
				if(numDirectors>max) {
					max = numDirectors;
					maxDirectorsFilms.clear();
				}
				
				//ako je numDirectors==max dodaj ime filma u listu
				if(numDirectors==max) {
					maxDirectorsFilms.add(title);
				}
			}
			
			//ispisi listu u context
			for(String title: maxDirectorsFilms) {
				Text key1 = new Text("max"); //svi sa istim keyem zavrsavaju na istom racunaru
				Text value1 = new Text(max + "\t" + title);
				context.write(key1, value1);
			}
			
		}		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance();
		//ime da bi mogli da pratimo posao u nekom sistemu
		job.setJobName("Filmovi sa max brojem reditelja");
		//sve sto treba da se distribuira na druge racunare se nalazi u ovoj klasi
		job.setJarByClass(Filmovi1.class);
		
		//ulaz u nas sistem je tekstualnog tipa, tj. kako nam cita objekte iz ulaznog toga => kao najobicniji text
		job.setInputFormatClass(TextInputFormat.class);
		//izlaz iz sistema je isto tekstualnog tipa, tj. kako upisuje obj. u izlazni tok => kao najobicniji text
		job.setOutputFormatClass(TextOutputFormat.class);
		
		//kog tipa ce biti kljuc
		job.setOutputKeyClass(Text.class);
		//kog tipa ce biti vrednost koju razmenjuju maper i reducer
		job.setOutputValueClass(Text.class);
		
		//ko radi mapiranje
		job.setMapperClass(FilmMapper.class);
		//ko radi redukciju
		job.setReducerClass(FilmReducer.class);
		
		//odakle inicijalno citamo podatke
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		//gde na kraju upisujemo podatke
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
		
		System.out.println("Test " + args[0]);
	}

}
