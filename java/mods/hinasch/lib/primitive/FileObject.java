package mods.hinasch.lib.primitive;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Basicを思い出す作りにしてるのでOpenして最後Closeする。
 *
 *
 */
public class FileObject {

	//未完
	protected Optional<Character> currentPointer;
	protected File file;
	protected FileReader fileReader;
	protected BufferedWriter bWriter;
	protected BufferedReader bReader;
	protected FileWriter fileWriter;
	protected JsonWriter jsonWriter;
	protected JsonReader jsonReader;
	protected Gson gson;
	protected FileOutputStream stream;
	protected FileChannel channel;
	protected Optional<String> currentStr = Optional.of(new String());
	protected String temp = new String();
	public FileObject(String filename){
		this.file = new File(filename);

	}

	public FileObject(File file){
		this.file = file;
	}
	public FileObject(File path,String filename){
		this.file = new File(path,filename);

	}

	public void openForOutput(){
			try {
				fileWriter = new FileWriter(file);

			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

	}

	@SuppressWarnings("resource")
	public void openForOutputStream(){
		try {
			this.channel = new FileOutputStream(file).getChannel();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

}
	@SuppressWarnings("resource")
	public void openForInputStream(){
		try {
			this.channel = new FileInputStream(file).getChannel();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

}
	public void openForJsonOutput(){
		try {
			fileWriter = new FileWriter(file);
			jsonWriter = new JsonWriter(new BufferedWriter(fileWriter));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	public FileChannel getChannel(){
		return this.channel;
	}
	public void openForJsonInput(){
		if(file.exists()){
			try {

				fileReader = new FileReader(file);
				bReader= new BufferedReader(fileReader);
				jsonReader = new JsonReader(bReader);

			} catch (FileNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}else{
			System.out.println("file not found:"+file.toString());
		}

	}
	public int writeBuffer(ByteBuffer buf,long size){
		try {
			return this.channel.write(buf,size);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return -1;
	}
	public int writeBuffer(ByteBuffer buf){
		try {
			return this.channel.write(buf);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return -1;
	}

	public int readBuffer(ByteBuffer toWrite){
		try {
			return this.channel.read(toWrite);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return -1;
	}
	/**
	 * テキストを書き込む。
	 * @param str
	 */
	public void write(String str){
		if(fileWriter!=null){
			try {
				fileWriter.write(str);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}else{
			System.out.println("can't write,open file first.");
		}
	}

	/**
	 * Jsonとしてオブジェクトの中身を書き出す。
	 * 別に書き込み用クラスを作るほうがよい。
	 * @param obj
	 * @param clazz
	 */
	public void writeJson(Object obj,Class clazz){

		this.gson = new Gson();
		System.out.println(gson.toJson(obj));
		gson.toJson(obj,clazz,jsonWriter);
		//gson.to
	}

	/**
	 * Jsonからオブジェクトへ戻す。オブジェクトクラスはあらかじめ用意しておく。
	 * ちなみにマップ型はすべてLinkedTreeMapになる（入れ子も同様）
	 * @param clazz
	 * @return
	 */
	public <T> T readJson(Class clazz){
		this.gson = new Gson();
		return gson.fromJson(jsonReader, clazz);
	}
	public void readNext(){
		if(fileReader!=null){
			try {

				int c = fileReader.read();
				if(c==-1){
					this.currentPointer = Optional.absent();
					return;
				}
				this.currentPointer = Optional.of((char)c);


			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}else{
			System.out.println("can't read,open file first.");
		}

	}

	public void close(){
		if(fileWriter!=null){
			try {
				if(this.jsonWriter!=null){
					jsonWriter.close();
				}
				fileWriter.close();

			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		if(fileReader!=null){
			try {
				fileReader.close();
				bReader.close();
				if(this.jsonReader!=null){
					this.jsonReader.close();
				}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}
		if(this.channel!=null){
			try {
				this.channel.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}



	public void openForInput(){
		if(file.exists()){
			try {

				fileReader = new FileReader(file);
				bReader= new BufferedReader(fileReader);

			} catch (FileNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}else{
			System.out.println("file not found:"+file.toString());
		}
	}

	public boolean exists(){
		return file.exists();
	}


	public Optional<String> read(){
		if(bReader!=null){
			try {
				String line = bReader.readLine();
				if(line==null){
					return Optional.absent();
				}
				return Optional.of(line);

			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return Optional.absent();
	}
}
