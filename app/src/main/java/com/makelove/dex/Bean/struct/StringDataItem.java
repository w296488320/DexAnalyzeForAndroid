package com.makelove.dex.Bean.struct;

import java.util.ArrayList;
import java.util.List;

public class StringDataItem {
	
	/**
	 * struct string_data_item
		{
		uleb128 utf16_size;
		ubyte data;
		}
	 */
	
	/**
	 *  �����������ᵽ�� LEB128 �� little endian base 128 ) ��ʽ ���ǻ��� 1 �� Byte ��һ�ֲ������ȵ�
		���뷽ʽ ������һ�� Byte �����λΪ 1 �����ʾ����Ҫ��һ�� Byte ������ ��ֱ�����һ�� Byte �����
		λΪ 0 ��ÿ�� Byte ������ Bit ������ʾ����
	 */
	
	public List<Byte> utf16_size = new ArrayList<Byte>();
	public byte data;

}
