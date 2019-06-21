package com.pop.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Pop
 * @date 2019/6/21 22:08
 */
public class MappedBuffer {

    static private final int start = 0;
    static private final int size = 1024;
    /**
     * 映射缓冲区，比传统意义上的io要快上许多
     * 因为他不会像是传统io一样将整个文件全部读进去，而是通过“截取”
     * 需要的部分，才会写入内容。
     *
     * 类似我们的start - size
     */
    public static void main(String[] args) throws Exception{

        RandomAccessFile raf = new RandomAccessFile("test.txt","rw");

        FileChannel fc = raf.getChannel();

        //选择映射模式，很明显，这里是读写，并且映射范围也指定出来
        MappedByteBuffer mmb = fc.map(FileChannel.MapMode.READ_WRITE,start,size);

        mmb.put(0,(byte)64);
        mmb.put(10,(byte)65);



        System.out.println(
                new String(new byte[]{mmb.get(3),mmb.get(2)})
        );
        mmb.get();
        System.out.println(mmb.position()+" "+mmb.limit()+" "+mmb.capacity());
        raf.close();
    }

}
