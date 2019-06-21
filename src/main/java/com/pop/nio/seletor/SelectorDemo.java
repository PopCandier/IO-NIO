package com.pop.nio.seletor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Pop
 * @date 2019/6/21 22:21
 */
public class SelectorDemo {

    /**
     * Selector 做NIO的三大模块之一
     *
     * channel
     *
     * buffer
     *
     * NIO基于Reactor(反应堆)模式的工作方式
     * 我的理解NIO的宣言是同步非阻塞，我的理解的非阻塞是
     * 每个io请求都能够得到处理，而是像传统BIO一样，一定要等待一个IO请求处理完后
     * 再去处理下一个
     *
     *
     * seletor通过注册事件key，与一个channel建立关联，并且会告诉当前自己的状态
     * 下一步是可以读还是写。
     *
     *
     * 使用NIO中非阻塞IO编写服务器处理程序，大体上可以分为下面三个步骤
     *
     * 1.向Selector对象注册感兴趣的事件
     * 2.向Selector 中获取感兴趣的事件
     * 3.根据不同的时间进行相应的处理
     */

    /**
     * 注册事件
     */
    static final int port = 8080;
    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private Selector getSelector() throws IOException {

        //创建Selector对象
         selector = Selector.open();

        //选择通道
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);//设置非阻塞


        //绑定通道到指定的端口
        ServerSocket socket = server.socket();
        /**
         * 表示此通道想要监听accept事件，也就是新的连接发生
         * 时所产生的事件
         */
        InetSocketAddress address = new InetSocketAddress(port);

        //绑定
        server.bind(address);
        socket.bind(address);


        //注册到selector中，这里是服务器通道注册

        server.register(selector, SelectionKey.OP_ACCEPT);
        return selector;

    }

    //以上为注册，现在开始监听端口
    public void listen(){

        System.out.println("listen on "+port);

        try{

            while(true){
                //阻塞，直到有一个事件发生
                selector.select();//保存高32位保存 read 低32位保存write，一旦有数据进出就会返回，停止阻塞
                //有事件触发，将会走一下步
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iter = keys.iterator();
                while(iter.hasNext()){

                    SelectionKey key = iter.next();
                    iter.remove();
                    process(key);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void process(SelectionKey key) throws Exception{

        //接受请求，为服务器定制的，因为目前服务器定制了请求事件
        if(key.isAcceptable()){

            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverChannel.accept();
            //取出客户端请求
            socketChannel.configureBlocking(false);
            //给这个客户端注册事件  可以读请求 服务器下次可以读
            socketChannel.register(selector,SelectionKey.OP_READ);
        }

        else if(key.isReadable()){
            //准备缓冲区
            SocketChannel channel = (SocketChannel) key.channel();
            int len = channel.read(buffer);
            if(len>0){
                //固定
                buffer.flip();
                String content = new String(buffer.array(),0,len);
                //服务器表示，我确实读到了，下一步我要准备写了,下一次循环将再次用到
                SelectionKey skey = channel.register(selector,SelectionKey.OP_WRITE);
                skey.attach(content);
            }else{
                channel.close();
            }
            buffer.clear();
        }
        else  if(key.isWritable()){

            SocketChannel channel = (SocketChannel) key.channel();
            String content = (String)key.attachment();
            ByteBuffer block = ByteBuffer.wrap(("输出内容: "+content).getBytes());
            if(block!=null){
                channel.write(block);
            }else {
                channel.close();
            }

        }
    }

}
