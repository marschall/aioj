package com.github.marschall.aioj.nio;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

public interface AioByteChannel {

  <A> void read(ByteBuffer dst,
                long fileOffset,
                A attachment,
                CompletionHandler<Integer,? super A> handler);

// for avoiding ByteBuffer#slice
//  <A> void read(ByteBuffer dst,
//                long fileOffset,
//                int bufferOffset,
//                int length,
//                A attachment,
//                CompletionHandler<Integer,? super A> handler);


  <A> void write(ByteBuffer src,
                 long fileOffset,
                 A attachment,
                 CompletionHandler<Integer,? super A> handler);

}
