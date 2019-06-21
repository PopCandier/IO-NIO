package com.pop.nio.buffer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Pop
 * @date 2019/6/21 21:14
 */
public class BufferDemo {

    public static void main(String[] args) throws Exception {

        FileInputStream fin = new FileInputStream("test.txt");
        //得到通道
        FileChannel channel = fin.getChannel();
        //分配缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(15);
        output("初始时",byteBuffer);
        //读入缓冲区
        channel.read(byteBuffer);
        output("调用read",byteBuffer);

        //固定数据
        byteBuffer.flip();//会将position 归零
        output("固定数据",byteBuffer);

        //判断有没有可读数据
        while(byteBuffer.remaining()>0){
            byte b = byteBuffer.get();
            System.out.print(b+" ");
        }
        output("调用get()",byteBuffer);

        //clear
        byteBuffer.clear();
        output("调用clear()",byteBuffer);

        //关闭通道
        fin.close();

        /**
         * 另外需要注意的是，position 默认是0
         *
         * */
    }

    public static void output(String step, Buffer buffer){
        System.out.println(step+" : ");
        System.out.println(String.format("position : %d ,limit : %d ,capacity : %d",
                buffer.position(),buffer.limit(),buffer.capacity()));
        System.out.println();
    }
}
