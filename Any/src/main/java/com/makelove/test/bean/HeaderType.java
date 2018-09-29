package com.makelove.test.bean;


import com.makelove.test.utils.Utils;

public class HeaderType implements getData{
	
	/**
	 * struct header_item
		{
		ubyte[8] magic;
		unit checksum;
		ubyte[20] siganature;
		uint file_size;
		uint header_size;
		unit endian_tag;
		uint link_size;
		uint link_off;
		uint map_off;
		uint string_ids_size;
		uint string_ids_off;
		uint type_ids_size;
		uint type_ids_off;
		uint proto_ids_size;
		uint proto_ids_off;
		uint method_ids_size;
		uint method_ids_off;
		uint class_defs_size;
		uint class_defs_off;
		uint data_size;
		uint data_off;
		}
	 */
	public byte[] magic ;
	public byte[] checksum;
	public byte[] siganature ;
	public byte[] file_size;
	public byte[] header_size;
	public byte[] endian_tag;
	public byte[] link_size;
	public byte[] link_off;
	public byte[] map_off;
	public byte[] string_ids_size;
	public byte[] string_ids_off;
	public byte[] type_ids_size;
	public byte[] type_ids_off;
	public byte[] proto_ids_size;
	public byte[] proto_ids_off;
	public byte[] field_ids_size;
	public byte[] field_ids_off;
	public byte[] method_ids_size;
	public byte[] method_ids_off;
	public byte[] class_defs_size;
	public byte[] class_defs_off;
	public byte[] data_size;
	public byte[] data_off;

	@Override
	public byte[] getData() {
		return Utils.byteMergerAll(magic,checksum,
				siganature,file_size,header_size,
				endian_tag,link_size,link_off,
				map_off,string_ids_size,string_ids_off,
				type_ids_size,type_ids_off,proto_ids_size,
				proto_ids_off,field_ids_size,field_ids_off,
				method_ids_size,method_ids_off,class_defs_size,
				class_defs_off,data_size,data_off);
	}


//	@Override
//	public String toString(){
//		return "magic:"+Utils.bytesToHexString(magic)+"\n"
//				+ "checksum:"+checksum + "\n"
//				+ "siganature:"+ Utils.bytesToHexString(siganature) + "\n"
//				+ "file_size:"+file_size + "\n"
//				+ "header_size:"+header_size + "\n"
//				+ "endian_tag:"+endian_tag + "\n"
//				+ "link_size:"+link_size + "\n"
//				+ "link_off:"+Utils.bytesToHexString(Utils.int2Byte(link_off)) + "  "+link_off+"\n"
//				+ "map_off:"+Utils.bytesToHexString(Utils.int2Byte(map_off)) + "  "+map_off+"\n"
//				+ "string_ids_size:"+string_ids_size + "\n"
//				+ "string_ids_off:"+Utils.bytesToHexString(Utils.int2Byte(string_ids_off)) + "  "+string_ids_off+"\n"
//				+ "type_ids_size:"+type_ids_size + "\n"
//				+ "type_ids_off:"+Utils.bytesToHexString(Utils.int2Byte(type_ids_off)) + "  "+type_ids_off+"\n"
//				+ "proto_ids_size:"+proto_ids_size + "\n"
//				+ "proto_ids_off:"+Utils.bytesToHexString(Utils.int2Byte(proto_ids_off)) + "  "+proto_ids_off+"\n"
//				+ "field_ids_size:"+field_ids_size + "\n"
//				+ "field_ids_off:"+Utils.bytesToHexString(Utils.int2Byte(field_ids_off)) + "  "+field_ids_off+"\n"
//				+ "method_ids_size:"+method_ids_size + "\n"
//				+ "method_ids_off:"+Utils.bytesToHexString(Utils.int2Byte(method_ids_off)) + "  "+method_ids_off+"\n"
//				+ "class_defs_size:"+class_defs_size + "\n"
//				+ "class_defs_off:"+Utils.bytesToHexString(Utils.int2Byte(class_defs_off)) + "  "+class_defs_off+"\n"
//				+ "data_size:"+data_size + "\n"
//				+ "data_off:"+Utils.bytesToHexString(Utils.int2Byte(data_off))+ "  "+data_off;
//
//
//	}





}
