package com.javabase.base.cache;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class People implements WriteableObject {

	private static final long serialVersionUID = 1L;

	private String name;

	private List<String> list;
	
	public People(){
		super();
	}
	
	public People(String name, List<String> list) {
		this.name = name;
		this.list = list;
	}

	@Override
	public void read(InputStream inputStream) throws IOException {
		DataInputStream dis = new DataInputStream(inputStream);
		name = dis.readUTF();
		int size = dis.readInt();
		list = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			list.add(dis.readLine());
		}
	}

	@Override
	public boolean write(OutputStream outputStream) throws IOException {
		DataOutputStream dos = new DataOutputStream(outputStream);
		dos.writeUTF(name);
		dos.writeInt(list.size());
		for (String obj : list) {
			dos.writeBytes(obj);
		}
		dos.flush();
		dos.close();
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

}
