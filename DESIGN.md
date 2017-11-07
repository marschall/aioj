direct ByteBuffer
 * can be passed to system call without copy
 * .get is an instrinsic (custom abstraction has JNI overhead)
  * TODO benchmark JNI overhead
 * reads are slower than heap ByteBuffer because of range check
 * memory barrier same as Java

No
 * allocations behind the back (except for iocb and iocb array and events array)
 * no threads behind the back
 * object or buffer pools
 * reflection, Unsafe, only API
 * no copying

 * user controls buffer (has to pool them, if he wants to)
 * user controls objects (has to pool them, if he wants to)
 * user controls threads
 * copying to heap structures (if he wants to)

 * Linux 64bit only
  * x32 probably doesn't work
  * everything but x86 probably should work
 
To Test
 * read and write sliced buffer

Not supporting io_cancel for now.
Not supporting vectored io for now.