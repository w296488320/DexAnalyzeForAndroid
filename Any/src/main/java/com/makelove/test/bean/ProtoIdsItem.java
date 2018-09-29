package com.makelove.test.bean;

import com.makelove.test.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProtoIdsItem implements getData{
	
	/**
	 * struct proto_id_item
		{
		uint shorty_idx;
		uint return_type_idx;
		uint parameters_off;
		}
	 */
	//StringList 的 idx
	public byte[] shorty_idx;
	public byte[] return_type_idx;
	//储存的是TypeID的地址 TypeID 指向string 的地址
	public byte[] parameters_off;


	//没指针 parameters_off 不等于0
	//TypeList 有数值
	public TypeList TypeList;
	
	public static int getSize(){
		return 4 + 4 + 4;
	}

	@Override
	public byte[] getData() {
		return Utils.byteMergerAll(shorty_idx,return_type_idx,parameters_off);
	}


//	@Override
//	public String toString(){
//		return "shorty_idx:"+shorty_idx
//				+",return_type_idx:"+return_type_idx+
//				",parameters_off: "+parameters_off+
//				",parametersList: "+
//				//判断 参数 列表 是否有数据
//				(parametersList.size()!=0?parametersList.toString():"")
//				//默认 为 0 等于 0打印 ""
//				//开始 位置
//				+",parameterCount: "+(parameterCount==0?"0":parametersList.size()+"");
//
//	}

}
