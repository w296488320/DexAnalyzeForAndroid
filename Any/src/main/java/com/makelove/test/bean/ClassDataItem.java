package com.makelove.test.bean;

import com.makelove.test.utils.Utils;

public class ClassDataItem {
	
	/**
	 *  uleb128 unsigned little-endian base 128
		struct class_data_item
		{
			uleb128 static_fields_size;
			uleb128 instance_fields_size;
			uleb128 direct_methods_size;
			uleb128 virtual_methods_size;
			encoded_field static_fields [ static_fields_size ];
			encoded_field instance_fields [ instance_fields_size ];
			encoded_method direct_methods [ direct_method_size ];
			encoded_method virtual_methods [ virtual_methods_size ];
		}
	 */

	//uleb128只用来编码32位的整型数
	public byte[] static_fields_size;
	public byte[] instance_fields_size;
	public byte[] direct_methods_size;
	public byte[] virtual_methods_size;

	//记录 上面 个个leb128数据的 长度
//	public int static_fields_size_length;
//	public int instance_fields_size_length;
//	public int direct_methods_size_length;
//	public int virtual_methods_size_length;




	
	public EncodedField[] static_fields;
	public EncodedField[] instance_fields;
	public EncodedMethod[] direct_methods;
	public EncodedMethod[] virtual_methods;

	/**
	 * 将direct_methods  和 virtual_methods
	 * 的Debug_info数据都是设置成0
	 * @param src
	 * @return
	 */
	public byte[] setDebugZero(byte[] src){
		byte[] foundation=null;
		for(int i=0;i<direct_methods.length;i++){
			CodeItem codeItem = direct_methods[i].getCodeItem();
			if(codeItem!=null){
				foundation = codeItem.setDebug_info_off_Zero(src);
			}
		}
		for(int i=0;i<virtual_methods.length;i++){
			CodeItem codeItem = virtual_methods[i].getCodeItem();
			if(codeItem!=null){
				foundation = codeItem.setDebug_info_off_Zero(src);
			}
		}
		return foundation;
	}




//	@Override
//	public byte[] getData() {
//		return Utils.byteMergerAll(static_fields_size,instance_fields_size,
//				direct_methods_size,virtual_methods_size,
//				getData_Static_Fields(),getData_Instance_Fields(),
//				getData_Direct_Methods(),getData_Virtual_Methods());
//	}



//	public byte[] getData_Static_Fields(){
//		byte[] tote=null;
//		for(EncodedField ef:static_fields){
//			tote= Utils.byteMergerAll(tote,ef.getData());
//		}
//		return tote;
//	}
//
//	public byte[] getData_Instance_Fields(){
//		byte[] tote=null;
//		for(EncodedField ef:instance_fields){
//			tote= Utils.byteMergerAll(tote,ef.getData());
//		}
//		return tote;
//	}
//
//	public byte[] getData_Direct_Methods(){
//		byte[] tote=null;
//		for(EncodedMethod ef:direct_methods){
//			tote= Utils.byteMergerAll(tote,ef.getData());
//		}
//		return tote;
//	}
//
//	public byte[] getData_Virtual_Methods(){
//		byte[] tote=null;
//		for(EncodedMethod ef:virtual_methods){
//			tote= Utils.byteMergerAll(tote,ef.getData());
//		}
//		return tote;
//	}



//	@Override
//	public String toString(){
//		return "static_fields_size:"+static_fields_size+",instance_fields_size:"
//				+instance_fields_size+",direct_methods_size:"+direct_methods_size+",virtual_methods_size:"+virtual_methods_size
//				+"\n"+getFieldsAndMethods();
//	}
//
//	private String getFieldsAndMethods(){
//		StringBuilder sb = new StringBuilder();
//		sb.append("static_fields:\n");
//		for(int i=0;i<static_fields.length;i++){
//			sb.append(static_fields[i]+"\n");
//		}
//		sb.append("instance_fields:\n");
//		for(int i=0;i<instance_fields.length;i++){
//			sb.append(instance_fields[i]+"\n");
//		}
//		sb.append("direct_methods:\n");
//		for(int i=0;i<direct_methods.length;i++){
//			sb.append(direct_methods[i]+"\n");
//		}
//		sb.append("virtual_methods:\n");
//		for(int i=0;i<virtual_methods.length;i++){
//			sb.append(virtual_methods[i]+"\n");
//		}
//		return sb.toString();
//	}

}
