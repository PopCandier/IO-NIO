package com.pop.nio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Pop
 * @date 2019/6/21 21:47
 */
public class DirectBuffer {
    /**
     * 直接缓冲区是理论上最快的缓存区，他不需要拷贝到工作内存中
     * 而是直接操作系统级别的IO
     */

    public static void main(String[] args) throws Exception{

        FileInputStream fin = new FileInputStream("test.txt");
        FileChannel channelIn = fin.getChannel();

        //新的文件
        FileOutputStream fout = new FileOutputStream("testcopy.txt");
        FileChannel channelOut =  fout.getChannel();

        //使用 allocateDirect
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while(true){

            buffer.clear();//每次保证重头开始读

            int r = channelIn.read(buffer);
            if(r==-1){
                break;
            }
            //开始固定
            buffer.flip();

            channelOut.write(buffer);
        }
        fout.close();
        fin.close();
    }
}
