package com.makelove.test.bean;


import com.makelove.test.utils.Utils;

public class EncodedField implements getData{
	
	/**
	 * struct encoded_field
		{
			uleb128 filed_idx_diff; // index into filed_ids for ID of this filed
			uleb128 access_flags; // access flags like public, static etc.
		}
	 */
	public byte[] filed_idx_diff;
	public byte[] access_flags;

	@Override
	public byte[] getData() {
		return Utils.byteMergerAll(access_flags,filed_idx_diff);
	}


//	@Override
//	public String toString(){
//		return "field_idx_diff:"+Utils.bytesToHexString(filed_idx_diff) +
//				",access_flags:"+ Utils.bytesToHexString(filed_idx_diff);

        //描述一个字段的详细信息
//        FieldIdsItem fieldIdsItem = ParseDexUtils.
//                fieldIdsList.get(Utils.byte2int(filed_idx_diff));
//
//        return "field_idx_diff:"+
//                ParseDexUtils.stringList.get(ParseDexUtils.typeIdsList.get(fieldIdsItem.class_idx).descriptor_idx) +
//
//
//
//				",access_flags:"+ Utils.bytesToHexString(filed_idx_diff);
//	}

}
