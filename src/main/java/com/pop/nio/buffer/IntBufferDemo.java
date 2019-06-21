package com.pop.nio.buffer;

import java.nio.IntBuffer;

/**
 * @author Pop
 * @date 2019/6/21 21:03
 */
public class IntBufferDemo {

    public static void main(String[] args) {
        /**
         * 缓冲区而言， 有三个值值得被记录
         * position limit c
         */

        IntBuffer buffer = IntBuffer.allocate(8);

        /**
         *
         * limit
         *  的理解需要两个方面，一个是
         *  缓冲区->通道 表示还有多少数据残留在缓冲区中未写出，需要写出
         *  通道->缓冲区 表示还有多少空间可以放入数据
         *
         */

        for (int i = 0,len=buffer.capacity(); i <len ; i++) {
            int j = 2*(i+1);
            buffer.put(j);//写满
        }
        //上一步，position 为 8 limit 8 capacity为8

        buffer.flip();//我理解成固定，将position固定成1，limit保存为上一次position的位置
        while(buffer.hasRemaining()){
            //读取
            int j = buffer.get();
            System.out.print(j+" ");

        }


    }

}
