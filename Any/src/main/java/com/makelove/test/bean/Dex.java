package com.makelove.test.bean;


public class Dex implements getData{

    private byte[] src;



    public Dex(byte[] src){
        this.src=src;
    }


    public HeaderType Header;

    public StringIdsItem[] StringOffList;

    public TypeIdsItem[] TypeOffList;

    public ProtoIdsItem[] ProtoOffList;

    public FieldIdsItem[] FieldOffList;

    public MethodIdsItem[] MethodOffList;

    public ClassDefItem[] ClassDefOffList;



    /**
     * 数据段是打乱的
     * 不规则的 只能根据 位置进行修改
     * 无法 按顺序生成
     */
    public byte[] Data;



    /**
     * 返回CodeItem
     * @return
     */
    public byte[] setDebugInfoZero(){
//        if(getCodeItemOff()!=0){
//            return CodeItem;
//        }
        for(int i=0;i<ClassDefOffList.length;i++){
            byte[] bytes = ClassDefOffList[i].getDataItem().setDebugZero(src);
            this.src=bytes;
        }
        return getData();
    }


    @Override
    public byte[] getData(){
        return this.src;
    }


}
