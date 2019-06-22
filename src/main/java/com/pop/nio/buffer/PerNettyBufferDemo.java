package com.pop.nio.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * @author Pop
 * @date 2019/6/22 12:45
 */
public class PerNettyBufferDemo {

    /*
    * Netty
    * 内置缓冲区的测试
    * */
    private static final byte[] CONTENT = new byte[1024];
    private static int loop = 1800000;
    public static void nettyDirectBuffer(){
        /**
         * 使用内存池分配器创建直接缓冲区
         */
        long startTime = System.currentTimeMillis();
        ByteBuf poolBuffer = null;
        for(int i =0;i<loop;i++){
            poolBuffer = PooledByteBufAllocator.DEFAULT.directBuffer(1024);
            poolBuffer.writeBytes(CONTENT);
            poolBuffer.release();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("内存池耗时 : "+(endTime - startTime)+" ms.");
    }

    public static void nettyUnHeapBuffer(){
        long startTime = System.currentTimeMillis();
        ByteBuf buffer = null;
        for(int i =0;i<loop;i++){
            /**
             * 通过非堆内存分配创建直接内存缓冲区
             */
            buffer = Unpooled.directBuffer(1024);
            buffer.writeBytes(CONTENT);
        }
        long endTime = System.currentTimeMillis();

        System.out.println("非内存池分配缓冲区耗时 "+(endTime-startTime)+" ms.");
    }

    public static void main(String[] args) {
        nettyDirectBuffer();
        nettyUnHeapBuffer();
    }

}
