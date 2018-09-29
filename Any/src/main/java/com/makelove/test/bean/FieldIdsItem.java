package com.makelove.test.bean;

import com.makelove.test.utils.Utils;

public class FieldIdsItem implements getData{
	
	/**
	 * struct filed_id_item
		{
		ushort class_idx;
		ushort type_idx;
		uint name_idx;
		}
	 */
	
	public byte[] class_idx;
	public byte[] type_idx;
	public byte[] name_idx;
	
	public static int getSize(){
		return 2 + 2 + 4;
	}

	@Override
	public byte[] getData() {
		return Utils.byteMergerAll(class_idx,type_idx,
				name_idx);
	}




//	@Override
//	public String toString(){
//		return "class_idx:"+class_idx+",type_idx:"+type_idx+",name_idx:"+name_idx;
//	}
	
}
