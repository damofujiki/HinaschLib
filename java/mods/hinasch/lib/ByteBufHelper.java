package mods.hinasch.lib;
//package com.hinasch.lib;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.UnsupportedEncodingException;
//import java.nio.ByteBuffer;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.google.common.base.Function;
//import com.hinasch.lib.intf.IStreamWritable;
//
//public class ByteBufHelper {
//
//	protected static int ALLOCATE_DEFAULT  = 1024;
//	protected static int ALLOCATE_CHILD  = 32;
//	protected ByteBuffer buf;
//	protected ByteArrayOutputStream stream;
//	protected ObjectOutputStream objStream;
//	protected ByteArrayInputStream inputStream;
//	protected ObjectInputStream objInputStream;
//
//	public ByteBufHelper(){
//		this.buf = newBuffer();
//		this.stream = new ByteArrayOutputStream();
//		try {
//			this.objStream = new ObjectOutputStream(stream);
//		} catch (IOException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//	}
//	
//	public ByteBufHelper(byte[] bytes){
//		//this.buf = ByteBuffer.wrap(bytes);
//		this.inputStream = new ByteArrayInputStream(bytes);
//		try {
//			this.objInputStream = new ObjectInputStream(inputStream);
//		} catch (IOException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//	}
//	
//	public static ByteBufHelper newHelper(byte[] bytes){
//		return new ByteBufHelper(bytes);
//	}
//	public String readUTF() throws IOException{
//		return this.objInputStream.readUTF();
//	}
//	public ByteBufHelper writeUTF(String par1) throws IOException{
//		this.objStream.writeUTF(par1);
//		return this;
//	}
//	
//	public int length(){
//		if(this.stream!=null){
//			return this.stream.size();
//		}
//		return this.inputStream.available();
//	}
//	
//	public void close(){
//		try {
//			if(this.stream!=null){
//				this.objStream.close();
//				this.stream.close();
//			}else{
//
//				this.objInputStream.close();
//				this.inputStream.close();
//			}
//		} catch (IOException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//	}
//	public ByteBufHelper writeListToBuf(List<? extends IStreamWritable> list){
//		for(IStreamWritable writable:list){
//			ByteBufHelper child = ByteBufHelper.newHelper();
//			writable.writeToStream(child);
//			try {
//				byte[] bytes = child.toByteArray();
//				this.writeInt(bytes.length);
//				this.writeBytes(bytes);
//				System.out.println(bytes.length);
//			} catch (IOException e) {
//				// TODO 自動生成された catch ブロック
//				e.printStackTrace();
//			}
//			
//			
//		}
//		System.out.println(this.toByteArray().length);
//		this.close();
//		return this;
//	}
//	
//	public void writeInt(int par1) throws IOException{
//
//		this.objStream.writeInt(par1);
//
//	}
//	
//	public ByteBufHelper writeBytes(byte[] bytes) throws IOException{
//		for(byte b:bytes){
//			this.objStream.writeByte(b);
//		}
//		return this;
//	}
//	
//	public byte[] readBytes(int length) throws IOException {
//		byte[] bytes = new byte[length];
//		for(int i=0;i<length;i++){
//			bytes[i] = (byte) this.objInputStream.readByte();
//			//System.out.println(bytes[i]);
//		}
//		return bytes;
//	}
//	
//	public int readInt() throws IOException{
//		return this.objInputStream.readInt();
//	}
//	public <T> List<T> restoreListFromBuf(RestoreFunction<T> func){
//		List<T> list = new ArrayList();
//		try {
//			while(this.objInputStream.read()!=-1){
//				int length = this.readInt();			
//				System.out.println(length);
//				byte[] bytes = this.readBytes(length);
//				T elm = func.apply(ByteBufHelper.newHelper(bytes));
//				list.add(elm);
//			}
//			this.close();
//		} catch (IOException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		return list;
//	}
//	
//	public ByteBuffer getWrappedBuffer(){
//		return this.buf;
//	}
//	
//	/** 今のところ書き込み時に有効*/
//	public byte[] toByteArray(){
//		if(this.objStream!=null){
//
//			return this.stream.toByteArray();
//		}
//		return null;
//	}
//	public static ByteBufHelper newHelper(){
//		return new ByteBufHelper();
//	}
//	public static ByteBuffer newBuffer(){
//		return ByteBuffer.allocate(ALLOCATE_DEFAULT);
//	}
//	
//
//	public byte[] getBytes(int length){
//		return getBytes(this.buf,length);
//	}
//	public static byte[] getBytes(ByteBuffer buf,int length){
//		byte[] bytes = new byte[length];
//		for(int i=0;i<length;i++){
//			bytes[i] = buf.get();
//		}
//		return bytes;
//	}
//	public static void writeListToBuf(ByteArrayOutputStream buf,List<? extends IStreamWritable> list){
//
////		for(IStreamWritable writable:list){
////			ByteBuffer child = ByteBuffer.allocate(32);
////			writable.writeToStream(child);
////			byte[] childBytes = child.array();
////			int length = childBytes.length;
////			System.out.println(length);
////			buf.putInt(length);
////			buf.put(childBytes);
////		}
//
//	}
//
//	
//	public static void writeUTF(ByteBuffer buf,String str){
//		byte[] bytes;
//		try {
//			bytes = str.getBytes("UTF-8");
//			buf.putInt(bytes.length);
//			buf.put(bytes);
//		} catch (UnsupportedEncodingException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//
//	}
//	
//	public static String readUTF(ByteBuffer buf){
//		int length = buf.getInt();
//		byte[] bytes = getBytes(buf,length);
//		try {
//			String str = new String(bytes,"UTF-8");
//			return str;
//		} catch (UnsupportedEncodingException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//
//	public static void writeIntListToBuf(ByteBuffer buf,List<Integer> intlist){
//		for(int elm:intlist){
//			buf.putInt(elm);
//		}
//	}
//	
//	public static void writeObjListToBuf(ByteBuffer buf,List<Object> intlist){
//		for(Object elm:intlist){
//			writeUTF(buf,elm.toString());
//		}
//	}
//
//	public static List<Integer> restoreIntList(ByteBuffer buf){
//		List<Integer> list = new ArrayList();
//		while(buf.hasRemaining()){
//			list.add(buf.getInt());
//		}
//		return list;
//	}
//	
//	public static List<String> restoreStrList(ByteBuffer buf){
//		List<String> list = new ArrayList();
//		while(buf.hasRemaining()){
//			list.add(readUTF(buf));
//		}
//		return list;
//	}
//	
//	public static interface RestoreFunction<T> extends Function<ByteBufHelper,T>{
//		
//	}
//}
