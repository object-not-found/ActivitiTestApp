package com.app.demo.JavaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.net.SyslogAppender;

import com.alibaba.fastjson.JSON;

public class AAAA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<BBBB> list = new ArrayList<>();

		BBBB b1 = new BBBB();
		b1.setId(123);
		List<CCCC> bc = new ArrayList<>();
		CCCC bc1 = new CCCC(1, 21, 4);
		CCCC bc2 = new CCCC(23, 12, 11);
		CCCC bc3 = new CCCC(3, 22, 1);
		CCCC bc4 = new CCCC(1, 23, 21);
		bc.add(bc1);
		bc.add(bc2);
		bc.add(bc3);
		bc.add(bc4);
		b1.setC(bc);
		list.add(b1);

		BBBB b2 = new BBBB();
		b2.setId(555);
		List<CCCC> b2c = new ArrayList<>();
		CCCC b2c1 = new CCCC(1, 12, 3);
		CCCC b2c2 = new CCCC(233, 12, 11);
		CCCC b2c3 = new CCCC(4, 22, 1);
		b2c.add(b2c1);
		b2c.add(b2c2);
		b2c.add(b2c3);
		b2.setC(b2c);
		list.add(b2);

		BBBB b3 = new BBBB();
		b3.setId(1233L);
		List<CCCC> bc33 = new ArrayList<>();
		CCCC bc3bc1 = new CCCC(1, 22, 1);
		CCCC bc3bc2 = new CCCC(233, 12, 11);
		CCCC bc3bc3 = new CCCC(15, 22, 1);
		bc33.add(bc3bc1);
		bc33.add(bc3bc2);
		bc33.add(bc3bc3);
		b3.setC(bc33);
		list.add(b3);
		
		System.out.println(JSON.toJSONString(list));
		
		Map<BBBB, Long> mm = new HashMap<>();
		//这个嵌套循环怎么精简
		for (BBBB b : list) {
			for (CCCC c : b.getC()) {
				if (c.getCid() == 1) {
					if (mm.containsKey(b)) {
						if (mm.get(b) < c.getCtime()) {
							mm.put(b, c.getCtime());
						}
					} else {
						mm.put(b, c.getCtime());
					}
				}
			}
		}

		list.clear();
		mm.entrySet().stream().sorted(Map.Entry.<BBBB, Long> comparingByValue().reversed())
				.forEachOrdered(x -> list.add(x.getKey()));

		for (BBBB b : list) {
			System.out.println(b.getId());
		}

		System.out.println(JSON.toJSONString(list));
	}

}

class BBBB {
	private long id;
	private List<CCCC> c;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<CCCC> getC() {
		return c;
	}

	public void setC(List<CCCC> c) {
		this.c = c;
	}

}

class CCCC {
	private long cid;
	private long ctime;
	private long cnum;

	public CCCC(long cid, long ctime, long cnum) {
		super();
		this.cid = cid;
		this.ctime = ctime;
		this.cnum = cnum;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	public long getCnum() {
		return cnum;
	}

	public void setCnum(long cnum) {
		this.cnum = cnum;
	}

}