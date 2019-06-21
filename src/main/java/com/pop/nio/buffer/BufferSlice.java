package com.pop.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @author Pop
 * @date 2019/6/21 21:31
 */
public class BufferSlice {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);

        //切片缓冲区像是一个窗格，只给你看一部分的缓冲区位置
        for (int i = 0,len=buffer.capacity(); i <len ; i++) {
            buffer.put((byte)i);
        }

        //创建子缓冲区
        buffer.position(3);
        buffer.limit(7);//选定好区域，开始切片
        ByteBuffer slice = buffer.slice();//切片完成

        //改变子项目中的内容
        for (int i = 0,len=slice.capacity(); i <len ; i++) {
            byte b = slice.get(i);
            b*=10;
            slice.put(i,b);
        }

        //设置回去
        buffer.position(0);
        buffer.limit(buffer.capacity());

        while(buffer.remaining()>0){
            System.out.println(buffer.get());
        }
    }
}
