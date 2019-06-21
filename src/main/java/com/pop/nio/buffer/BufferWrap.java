package com.pop.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @author Pop
 * @date 2019/6/21 21:29
 */
public class BufferWrap {

    public static void main(String[] args) {
        //手动分配缓冲区
        ByteBuffer buffer1 = ByteBuffer.allocate(10);

        byte array[] = new byte[10];//自己自定义数据
        ByteBuffer buffer = ByteBuffer.wrap(array);
    }
}
