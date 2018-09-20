package com.makelove.dex.Bean.struct;

import java.util.ArrayList;
import java.util.List;

public class TypeList {
	
	/**
	 * struct type_list
		{
		uint size;
		ushort type_idx[size];
		}
	 */
	
	public int size;//�����ĸ���
	public List<Short> type_idx = new ArrayList<Short>();//����������
	
}
