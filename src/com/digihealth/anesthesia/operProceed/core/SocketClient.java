package com.digihealth.anesthesia.operProceed.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;


/**
 * 基于Netty框架实现Socket客户端，与采集服务完成数据同步 simple introduction
     * Title: SocketClient.java    
     * Description: 
     * @author chenyong       
     * @created 2016年7月15日 下午2:18:21
 */
public class SocketClient {
    private volatile EventLoopGroup workerGroup;

    private volatile Bootstrap bootstrap;

    private volatile static ChannelFuture future;
    
    public static Map<String, ChannelFuture> futureMap = new HashMap<String, ChannelFuture>();
    
    public static Map<String, Integer> connectMap = new HashMap<String, Integer>();

    //private volatile boolean connected = false;
    
    private volatile int connected = -1; //1/成功，0/正在重连，-1/失败
    
    //private volatile int normalClose = 0; //1/是，0/否   是否正常关闭连接

//    private final String remoteHost;
//
//    private final int remotePort;

    protected static final Logger logger = Logger.getLogger(SocketClient.class);

//    public boolean isConnected() {
//        return connected;
//    }
//
//    public void setConnected(boolean connected) {
//        this.connected = connected;
//    }
    
    public int getConnected() {
		return connected;
	}

	public void setConnected(int connected) {
		this.connected = connected;
	}

//    public SocketClient(String remoteHost, int remotePort) {
//        this.remoteHost = remoteHost;
//        this.remotePort = remotePort;
//    }

    

	private String getServerInfo(String remoteHost, int remotePort) {
        return String.format("RemoteHost=%s RemotePort=%d", remoteHost,
                remotePort);
    }

    public boolean init() {
        workerGroup = new NioEventLoopGroup(20);
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            public void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                ByteBuf delimiter = Unpooled.copiedBuffer(new byte[] { 0x0D });// {0x0D});//{0x1C,0x0D});
                pipeline.addLast(new DelimiterBasedFrameDecoder(1024 * 8, delimiter));
                pipeline.addLast("decoder", new StringDecoder( CharsetUtil.ISO_8859_1));
                pipeline.addLast("encoder", new StringEncoder( CharsetUtil.ISO_8859_1));
                pipeline.addLast("timeout", new IdleStateHandler(60, 60, 60));
                // pipeline.addLast("hearbeat", new HeartbeatClient());
                SocketClientHandler socketClientHandler = new SocketClientHandler();
                pipeline.addLast("handler", socketClientHandler);
                pipeline.addFirst(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelInactive(ChannelHandlerContext ctx)
                            throws Exception {
                    	// 进入channelInactive,将connected置为false 
                    	//connected = -1;
                        logger.info("init------channelInactive，不处理");
                        super.channelInactive(ctx);
                        
                        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
                        String clientIP = insocket.getAddress().getHostAddress();
                        int port = insocket.getPort();
                        String key = clientIP+":"+port;
                        Integer connectState = connectMap.get(key);
                        
                        if(Objects.equals(connectState, 1)){
                        	connectMap.remove(key);
                        }
                        
                        if(futureMap.containsKey(key)){
                        	doConnect(clientIP,port);
                        }
//                        ctx.channel().eventLoop().schedule(new Runnable() {
//                            public void run() {
//                                try {
//                                    logger.info("run------channelInactive");
//                                    //如果为正常关闭连接时，不执行doConnect方法重连
//                                    if(normalClose != 1)
//                                    	doConnect();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    logger.error(e);
//                                }
//                            }
//                        }, 10, TimeUnit.SECONDS);
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx,
                            Throwable cause) {
                        // 对读取超时异常进行判断
                        if (cause instanceof ReadTimeoutException) {
                            JSONObject json = new JSONObject();
                            json.put("msgType", MyConstants.DEFAULT_HEART_BEAT);
                            ctx.channel().writeAndFlush(json.toString() + MyConstants.END);
                        } else if (cause instanceof WriteTimeoutException) {
                            JSONObject json = new JSONObject();
                            json.put("msgType", MyConstants.DEFAULT_HEART_BEAT);
                            ctx.channel().writeAndFlush(json.toString() + MyConstants.END);
                        } else {
                            /*connected = false;
                            ctx.channel().eventLoop().schedule(new Runnable() {
                                public void run() {
                                    try {
                                        if (!connected) {
                                            logger.error("exceptionCaught------尝试Socket重连");
                                            doConnect();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        logger.error(e);
                                    }
                                }
                            }, 10, TimeUnit.SECONDS);*/
                        }

                    }
                });
            }
        });

//        doConnect();
        return true;
    }

    public void doConnect(final String remoteHost,final int remotePort) {
        
        logger.info("doConnect------尝试建立Tcp Client连接：" + getServerInfo(remoteHost,remotePort));

        future = bootstrap.connect(new InetSocketAddress(remoteHost, remotePort));

        String key = remoteHost+":"+remotePort;
    	futureMap.put(key, future);
        
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture f) throws Exception {
                if (f.isSuccess()) {
                	connected = 1;
                    logger.info("doConnect------建立Tcp Client连接成功: "
                            + future.channel().localAddress());
                } else {
                    logger.error("doConnect------建立Tcp Client连接失败： "
                            + getServerInfo(remoteHost,remotePort));
                    f.channel().eventLoop().schedule(new Runnable() {
                        // @Override
                        public void run() {
                            try {
                                logger.error("doConnect------重试Tcp Client连接: " + getServerInfo(remoteHost,remotePort));
                                //connected = 0;
                                Integer connectState = connectMap.get(remoteHost+":"+remotePort);
                                logger.error("doConnect------重试Tcp Client连接: " + getServerInfo(remoteHost,remotePort)+" connectState："+connectState);
                                if(!Objects.equals(connectState, 1)){
                                	doConnect(remoteHost,remotePort);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                logger.error(e);
                            }
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
    }
    
    public void closeFutureChannel(String key){
    	logger.info("closeFutureChannel------key:" + key); 
    	connectMap.put(key, 1);//1 表示正常关闭
		ChannelFuture future = SocketClient.futureMap.get(key);
		
		if(null != future){
			SocketClient.futureMap.remove(key);
			//bedMap.remove(bedId);
			try {
				future.channel().close().sync();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    
    

//    public void close() {
//    	
//    	try {
//    		normalClose = 1;
//			workerGroup.shutdownGracefully().sync();
//			//connected = -1;
//			normalClose = 0;
//			logger.info("close------Stopped Tcp Client Successed: "
//                    + getServerInfo());
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//			logger.info("close------Stopped Tcp Client : Failed："
//                    + Exceptions.getStackTraceAsString(e1));
//		}
//    }

    public boolean sendMsg(String msg,ChannelFuture future) {
        logger.info("sendMsg------Msg: " + msg);
        try {
//            if (connected == -1) {
//                logger.error("sendMsg------Socket消息发送失败，连接尚未建立 ");
//                return false;
//            }
        	if(null == future || null==future.channel()){
        		logger.error("sendMsg------消息发送失败，ChannelFuture异常! ");
        		return false;
        	}
        	
            String END = new String(new byte[] { 0x0D });
            msg = msg + END;
            future.channel().writeAndFlush(msg).sync();
            return true;
        } catch (Exception e) {
            logger.error("sendMsg------Socket消息发送失败： " + e + " Msg：" + msg);
            connected = -1;
//            doConnect();
//            try {
//                future.channel().writeAndFlush(msg).sync();
//				return true;
//            } catch (Exception ex) {
//                logger.error("sendMsg------重连后Socket消息发送失败： " + e + " Msg：" + msg);
//            }
        }
        
        return false;
    }
    
    //单独用：发送设备检查的命令
    public boolean sendDeviceMsg(String msg,ChannelFuture future){
    	logger.info("sendDeviceMsg------Msg: " + msg);
    	try {
    		String END = new String(new byte[] { 0x0D });
            msg = msg + END;
            future.channel().writeAndFlush(msg).await(500);
            return true;
		} catch (Exception e) {
			logger.error("sendDeviceMsg------Socket消息发送失败： " + e + " Msg：" + msg);
            connected = -1;
		}
    	return false;
    }
}
