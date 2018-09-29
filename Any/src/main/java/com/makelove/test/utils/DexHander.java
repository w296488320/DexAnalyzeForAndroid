package com.makelove.test.utils;

import com.makelove.test.bean.ClassDataItem;
import com.makelove.test.bean.ClassDefItem;
import com.makelove.test.bean.CodeItem;
import com.makelove.test.bean.Dex;
import com.makelove.test.bean.EncodedField;
import com.makelove.test.bean.EncodedMethod;
import com.makelove.test.bean.FieldIdsItem;
import com.makelove.test.bean.HeaderType;
import com.makelove.test.bean.MethodIdsItem;
import com.makelove.test.bean.ProtoIdsItem;
import com.makelove.test.bean.StringIdsItem;
import com.makelove.test.bean.TypeIdsItem;
import com.makelove.test.bean.TypeList;

/**
 * Created by lyh on 2018/9/28.
 */

public class DexHander {
    private Dex mDex;




    private static int HeaderOffset = 0;

    private static int StringListCount = 0;
    private static int StringListOffset = 0;

    private static int typeListCount = 0;
    private static int typeListOffset = 0;

    private static int protoListCount = 0;
    private static int protoListOffset = 0;

    private static int fieldListCount = 0;
    private static int fieldListOffset = 0;

    private static int methodListCount = 0;
    private static int methodListOffset = 0;

    private static int classDefListCount = 0;
    private static int classDefListOffset = 0;

    private static int dataListCount = 0;

    //ClassData  解析时候用不上 需要 根据 ClassDef里面的
    //data_offset拿到 偏移 和大小 然后 分别赋值
    private static int dataListOffset = 0;
    //存放data的 偏移 比如 第一个 StringData 数据
    private static int mapListOffset = 0;
    
    
    
    /**
     * 对 Dex进行赋值 
     * @param src
     * @return
     */
    public Dex SetDex(byte[] src){
        mDex=new Dex(src);
        praseDexHeader(src,mDex);
        praseDexStringList(src,mDex);
        praseDexTypeList(src,mDex);
        praseDexProtoList(src,mDex);
        praseDexFieldList(src,mDex);
        praseDexMethodList(src,mDex);
        praseDexClassDefList(src,mDex);
        praseDexData(src,mDex);
        return mDex;
    }

    /**
     * @param src
     * @param dex
     */
    private void praseDexData(byte[] src, Dex dex) {


        dex.Data=Utils.copyByte(src,dataListOffset,dataListCount);


//        byte[] foundation=null;
//        //StringList的Data数据
//        for(int i=0;i<dex.StringOffList.length;i++){
//            int startOff = Utils.byte2int(dex.StringOffList[i].string_data_off);
//            //字节长度
//            byte[] bytes = Utils.readUnsignedLeb128(src, startOff);
//
//            int length = Utils.Leb128_Int(bytes);
//            dex.Data.StringListData[i].size=bytes;
//            //字符串总是比 长度多一个字节
//            dex.Data.StringListData[i].data=Utils.
//                    copyByte(src,startOff+bytes.length,length+1);
//        }
    }

    private void praseDexMethodList(byte[] src, Dex dex) {

        int idSize = MethodIdsItem.getSize();

        for(int i=0;i<methodListCount;i++) {
            MethodIdsItem methodIdsItem = new MethodIdsItem();
            //一组数据
            byte[] bytes = Utils.copyByte(src, methodListOffset + i * idSize, idSize);

            methodIdsItem.class_idx = Utils.copyByte(bytes, 0, 2);
            methodIdsItem.proto_idx = Utils.copyByte(bytes, 2, 2);
            methodIdsItem.name_idx = Utils.copyByte(bytes, 4, 4);

            dex.MethodOffList[i]=methodIdsItem;
        }

    }

    private ClassDefItem praseDexClass(byte[] srcByte,ClassDefItem item){

        item.class_idx = Utils.copyByte(srcByte, 0, 4);

        item.access_flags = Utils.copyByte(srcByte, 4, 4);


        item.superclass_idx  = Utils.copyByte(srcByte, 8, 4);

        item.iterfaces_off = Utils.copyByte(srcByte, 12, 4);

        item.source_file_idx = Utils.copyByte(srcByte, 16, 4);

        item.annotations_off = Utils.copyByte(srcByte, 20, 4);

        item.class_data_off = Utils.copyByte(srcByte, 24, 4);

        item.static_value_off = Utils.copyByte(srcByte, 28, 4);

        int off = Utils.byte2int(item.class_data_off);
        //说明有Data数据
        if(off!=0){
            ClassDefItem defItem = parseClassDataItem(srcByte, off, item);

            return defItem;
        }

        return item;
    }






    /**
     * 解析 ClassDefIds->ClassData->ClassDataItem
     * 对ClassDataItem 进行解析 可以得到 ClassData 具体
     * @param srcByte
     */
    private static ClassDefItem parseClassDataItem(byte[] srcByte, int offset,ClassDefItem classDefItem){
        //LogUtils.e("size 开始偏移地址:  "+offset);
        ClassDataItem item = new ClassDataItem();


        // 4个 leb128变量
        for(int i=0;i<4;i++){
            byte[] byteAry = Utils.readUnsignedLeb128(srcByte, offset);
            //LogUtils.e("偏移基地址"+offset);
            //LogUtils.e("ByteLength"+byteAry.length);   最前面一个  1
            //循环了4次 把四个size赋值以后
            // 剩下的 就是 4个  Encoded 的 数据
            offset += byteAry.length;

//            int size = 0;

//            if(byteAry.length == 1){
//                size = byteAry[0];
//            }else if(byteAry.length == 2){
//                size = Utils.byte2Short(byteAry);
//            }else if(byteAry.length == 4){
//                size = Utils.byte2int(byteAry);
//            }
            if(i == 0){
                item.static_fields_size = byteAry;
            }else if(i == 1){
                item.instance_fields_size = byteAry;
            }else if(i == 2){
                item.direct_methods_size = byteAry;
            }else if(i == 3){
                item.virtual_methods_size = byteAry;
            }
        }


        // static_fields
        //每一个 ClassData有多个 EncodedField  和 EncodeMethod
        //顺序排列
        EncodedField[] staticFieldAry = new EncodedField[item.static_fields_size.length];

        //LogUtils.e("Encoded 开始偏移地址:  "+offset);
        for(int i=0;i<item.static_fields_size.length;i++){
            /**
             *  public int filed_idx_diff;
             public int access_flags;
             */
            EncodedField staticField = new EncodedField();
            //  Encode里面都是 Leb128数据  先拿到 第一个 数据
            staticField.filed_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
            offset += staticField.filed_idx_diff.length;
            //分别赋值
            staticField.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
            offset += staticField.access_flags.length;

            staticFieldAry[i] = staticField;
        }

        // instance_fields
        EncodedField[] instanceFieldAry = new EncodedField[item.instance_fields_size.length];
        for(int i=0;i<item.instance_fields_size.length;i++){
            /**
             public int filed_idx_diff;
             public int access_flags;
             */
            EncodedField instanceField = new EncodedField();
            instanceField.filed_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
            offset += instanceField.filed_idx_diff.length;
            instanceField.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
            offset += instanceField.access_flags.length;
            instanceFieldAry[i] = instanceField;
        }

        // static_methods
        //和上面数据 是连续上的 不是分开的
        EncodedMethod[] staticMethodsAry = new EncodedMethod[item.direct_methods_size.length];
        for(int i=0;i<item.direct_methods_size.length;i++){
            /**
             *
             public byte[] method_idx_diff;
             public byte[] access_flags;
             public byte[] code_off;
             */
            EncodedMethod directMethod = new EncodedMethod();
            directMethod.method_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
            offset += directMethod.method_idx_diff.length;
            directMethod.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
            offset += directMethod.access_flags.length;



            directMethod.code_off = Utils.readUnsignedLeb128(srcByte, offset);
            //LogUtils.e("code_off 开始偏移地址:  "+directMethod.code_off.length);
            offset += directMethod.code_off.length;

            int codeItemOff = directMethod.getCodeItemOff();

            if(codeItemOff!=0){
                //将数据的偏移地址 放进去
                CodeItem codeItem = new CodeItem(codeItemOff);
                //将Item 赋值
                directMethod.CodeItem=codeItem;
            }
            staticMethodsAry[i] = directMethod;
        }

        // virtual_methods
        EncodedMethod[] instanceMethodsAry = new EncodedMethod[item.virtual_methods_size.length];
        for(int i=0;i<item.virtual_methods_size.length;i++){
            /**
             public byte[] method_idx_diff;
             public byte[] access_flags;
             public byte[] code_off;
             */
            EncodedMethod instanceMethod = new EncodedMethod();
            instanceMethod.method_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
            offset += instanceMethod.method_idx_diff.length;
            instanceMethod.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
            offset += instanceMethod.access_flags.length;
            instanceMethod.code_off = Utils.readUnsignedLeb128(srcByte, offset);
            offset += instanceMethod.code_off.length;



            int codeItemOff = instanceMethod.getCodeItemOff();

            if(codeItemOff!=0){
                //将数据的偏移地址 放进去
                CodeItem codeItem = new CodeItem(codeItemOff);
                //将Item 赋值
                instanceMethod.CodeItem=codeItem;
            }
            instanceMethodsAry[i] = instanceMethod;
        }

        item.static_fields = staticFieldAry;
        item.instance_fields = instanceFieldAry;
        item.direct_methods = staticMethodsAry;
        item.virtual_methods = instanceMethodsAry;

        classDefItem.ClassDataItem=item;

        return classDefItem;
    }




    private void praseDexClassDefList(byte[] src, Dex dex) {
        int size = ClassDefItem.getSize();

        for(int i=0;i<classDefListCount;i++) {
            ClassDefItem classDefItem = new ClassDefItem();
            //一组数据
            byte[] bytes = Utils.copyByte(src, classDefListOffset + i * size, size);

            dex.ClassDefOffList[i]=praseDexClass(bytes,classDefItem);
        }


    }

    private void praseDexFieldList(byte[] src, Dex dex) {
        int idSize = FieldIdsItem.getSize();

        for(int i=0;i<fieldListCount;i++) {
            FieldIdsItem fieldIdsItem = new FieldIdsItem();
            //一组数据
            byte[] bytes = Utils.copyByte(src, fieldListOffset + i * idSize, idSize);

            fieldIdsItem.class_idx = Utils.copyByte(bytes, 0, 2);
            fieldIdsItem.type_idx = Utils.copyByte(bytes, 2, 2);
            fieldIdsItem.name_idx = Utils.copyByte(bytes, 4, 4);

            dex.FieldOffList[i]=fieldIdsItem;
        }


    }


    private void praseDexProtoList(byte[] src, Dex dex) {
        //获取大小
        int idSize = ProtoIdsItem.getSize();

        for(int i=0;i<protoListCount;i++){
            ProtoIdsItem typeIdsItem = new ProtoIdsItem();
            //一组数据
            byte[] bytes = Utils.copyByte(src, protoListOffset + i * idSize, idSize);
            typeIdsItem.shorty_idx = Utils.copyByte(bytes, 0, 4);
            typeIdsItem.return_type_idx = Utils.copyByte(bytes, 4, 4);
            typeIdsItem.parameters_off = Utils.copyByte(bytes, 8, 4);


            int off = Utils.byte2int(typeIdsItem.parameters_off);

            if( off!= 0){
                //指针4字节
                byte[] sizeByte = Utils.copyByte(src, off, 4);

                //size 是 有几个 参数 的 size
                int size = Utils.byte2int(sizeByte);

                for(int o=0;o<size;o++){
                    typeIdsItem.TypeList=new TypeList();
                    // +4 +2*i 前面的 4是 size的 int 大小
                    //后面的 short的大小 short占2字节
                    //因为 指向的 是一个 Type类型 开始地址 包含了 具体 可看 图片
                    byte[] typeByte = Utils.copyByte(src, off+4+2*i, 2);

                    typeIdsItem.TypeList.typeIds=typeByte;
                }

            }

            dex.ProtoOffList[i]=typeIdsItem;

        }
    }



    private void praseDexTypeList(byte[] src, Dex dex) {
        //单个大小
        int idSize = TypeIdsItem.getSize();

        for(int i=0;i<typeListCount;i++){
            TypeIdsItem typeIdsItem = new TypeIdsItem();
            typeIdsItem.descriptor_idx
                    = Utils.copyByte(src,typeListOffset+i*idSize,idSize);

            dex.TypeOffList[i]=typeIdsItem;
        }
    }

    private void praseDexStringList(byte[] src, Dex dex) {
        //单个大小
        int idSize = StringIdsItem.getSize();

        for(int i=0;i<StringListCount;i++){
            StringIdsItem stringIdsItem = new StringIdsItem();
            stringIdsItem.string_data_off
                    = Utils.copyByte(src,StringListOffset+i*idSize,idSize);

            dex.StringOffList[i]=stringIdsItem;
        }

    }





    /**
     * 解析头部
     * @param byteSrc
     */
    private  void praseDexHeader(byte[] byteSrc,Dex dex){
        HeaderType headerType = dex.Header;
        //魔教
        byte[] magic = Utils.copyByte(byteSrc, 0, 8);
        headerType.magic = magic;

        //checksum 效验 dex是否损毁
        byte[] checksumByte = Utils.copyByte(byteSrc, 8, 4);


        //siganature SHA-1  20个字节
        byte[] siganature = Utils.copyByte(byteSrc, 12, 20);
        headerType.siganature = siganature;

        //file_size  文件总大小
        byte[] fileSizeByte = Utils.copyByte(byteSrc, 32, 4);
        headerType.file_size = fileSizeByte;


        //header_size  头部大小 一般为70和字节
        byte[] headerSizeByte = Utils.copyByte(byteSrc, 36, 4);
        headerType.header_size = headerSizeByte;

        //endian_tag  判断大小端
        byte[] endianTagByte = Utils.copyByte(byteSrc, 40, 4);
        headerType.endian_tag = endianTagByte;

        //link_size
        byte[] linkSizeByte = Utils.copyByte(byteSrc, 44, 4);
        headerType.link_size = linkSizeByte;

        //link_off
        byte[] linkOffByte = Utils.copyByte(byteSrc, 48, 4);
        headerType.link_off = linkOffByte;

        //map_off
        byte[] mapOffByte = Utils.copyByte(byteSrc, 52, 4);
        headerType.map_off = mapOffByte;




        //string_ids_size
        byte[] stringIdsSizeByte = Utils.copyByte(byteSrc, 56, 4);
        headerType.string_ids_size = stringIdsSizeByte;


        //string_ids_off
        byte[] stringIdsOffByte = Utils.copyByte(byteSrc, 60, 4);
        headerType.string_ids_off = stringIdsOffByte;

        // type_ids_size
        byte[] typeIdsSizeByte = Utils.copyByte(byteSrc, 64, 4);
        headerType.type_ids_size = typeIdsSizeByte;

        // type_ids_off
        byte[] typeIdsOffByte = Utils.copyByte(byteSrc, 68, 4);
        headerType.type_ids_off = typeIdsOffByte;

        // proto_ids_size
        byte[] protoIdsSizeByte = Utils.copyByte(byteSrc, 72, 4);
        headerType.proto_ids_size = protoIdsSizeByte;

        // proto_ids_off
        byte[] protoIdsOffByte = Utils.copyByte(byteSrc, 76, 4);
        headerType.proto_ids_off = protoIdsOffByte;

        // field_ids_size
        byte[] fieldIdsSizeByte = Utils.copyByte(byteSrc, 80, 4);
        headerType.field_ids_size = fieldIdsSizeByte;

        // field_ids_off
        byte[] fieldIdsOffByte = Utils.copyByte(byteSrc, 84, 4);
        headerType.field_ids_off = fieldIdsOffByte;

        // method_ids_size
        byte[] methodIdsSizeByte = Utils.copyByte(byteSrc, 88, 4);
        headerType.method_ids_size = methodIdsSizeByte;

        // method_ids_off
        byte[] methodIdsOffByte = Utils.copyByte(byteSrc, 92, 4);
        headerType.method_ids_off = methodIdsOffByte;

        // class_defs_size
        byte[] classDefsSizeByte = Utils.copyByte(byteSrc, 96, 4);
        headerType.class_defs_size = classDefsSizeByte;

        // class_defs_off
        byte[] classDefsOffByte = Utils.copyByte(byteSrc, 100, 4);
        headerType.class_defs_off = classDefsOffByte;

        // data_size
        byte[] dataSizeByte = Utils.copyByte(byteSrc, 104, 4);
        headerType.data_size = dataSizeByte;

        // data_off
        byte[] dataOffByte = Utils.copyByte(byteSrc, 108, 4);
        headerType.data_off = dataOffByte;



        StringListCount= Utils.byte2int(stringIdsSizeByte);
        StringListOffset= Utils.byte2int(stringIdsOffByte);

        typeListCount= Utils.byte2int(typeIdsSizeByte);
        typeListOffset= Utils.byte2int(typeIdsOffByte);


        protoListCount= Utils.byte2int(protoIdsSizeByte);
        protoListOffset= Utils.byte2int(protoIdsOffByte);

        fieldListCount= Utils.byte2int(fieldIdsSizeByte);
        fieldListOffset= Utils.byte2int(fieldIdsOffByte);

        methodListCount= Utils.byte2int(methodIdsSizeByte);
        methodListOffset= Utils.byte2int(methodIdsOffByte);

        classDefListCount= Utils.byte2int(classDefsSizeByte);
        classDefListOffset= Utils.byte2int(classDefsOffByte);





    }
}
